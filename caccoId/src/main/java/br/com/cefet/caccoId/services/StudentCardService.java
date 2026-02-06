package br.com.cefet.caccoId.services;

import br.com.cefet.caccoId.dtos.StudentCardDTO;
import br.com.cefet.caccoId.mappers.StudentCardMapper;
import br.com.cefet.caccoId.models.Student;
import br.com.cefet.caccoId.models.StudentCard;
import br.com.cefet.caccoId.repositories.StudentCardRepository;
import br.com.cefet.caccoId.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cefet.caccoId.services.SolicitationService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import br.com.cefet.caccoId.repositories.SolicitationRepository;
import br.com.cefet.caccoId.models.enums.SolicitationStatus;
import br.com.cefet.caccoId.models.Solicitation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentCardService {
    @Autowired
    private SolicitationService solicitationService;
    @Autowired
    private StudentCardRepository studentCardRepository;
    @Autowired
    private SolicitationRepository solicitationRepository;
    @Autowired
    private StudentRepository studentRepository;

    private static final Logger log = LoggerFactory.getLogger(StudentCardService.class);

    /**
     * Verifica carteirinhas expiradas e retorna a carteirinha ativa mais recente.
     * Retorna null se não houver carteirinha ativa.
     */
    public StudentCard checkAndGetActiveCard(Long studentId) {
        LocalDate today = LocalDate.now();
        StudentCard activeCard = null;

        List<StudentCard> cards = studentCardRepository.findAllByStudentId(studentId);
        for (StudentCard c : cards) {
            if (c.isCurrentCard() && c.getValidity().isBefore(today)) {
                c.setCurrentCard(false);
                studentCardRepository.save(c);
                log.info("Carteirinha ID {} expirada desativada.", c.getId());

                // Atualiza status da solicitação para "AUTORIZADA"
                Short statusCode = (short) SolicitationStatus.fromString("AUTORIZADA").getCode();
                solicitationService.updateStatus(statusCode, studentId);
            }
        }

        // Busca novamente a carteirinha ativa após a desativação das expiradas
        for (StudentCard c : studentCardRepository.findAllByStudentId(studentId)) {
            if (c.isCurrentCard()) {
                activeCard = c;
                break; // considera a primeira encontrada como a mais recente
            }
        }

        return activeCard;
    }

    public StudentCardDTO getStudentCardByToken(String token) {
        StudentCard card = studentCardRepository.findByValidityToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Carteirinha não encontrada"));
        return StudentCardMapper.toDTO(card);
    }

    public StudentCardDTO getStudentCardByStudentId(Long studentId) {
        StudentCard activeCard = checkAndGetActiveCard(studentId);

        if (activeCard == null) {
            throw new IllegalStateException("Nenhuma carteirinha ativa encontrada para este estudante");
        }

        return StudentCardMapper.toDTO(activeCard);
    }

    public StudentCardDTO createStudentCard(Long studentId) {
        // Verifica carteirinha ativa
        StudentCard activeCard = checkAndGetActiveCard(studentId);
        if (activeCard != null) {
            throw new IllegalStateException("Já existe uma carteirinha ativa para este estudante");
        }

        // Busca estudante
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado"));

        LocalDate today = LocalDate.now();

        // Busca solicitações autorizadas
        Short statusAuthorizedCode = (short) SolicitationStatus.fromString("AUTORIZADA").getCode();
        List<Solicitation> solicitations = solicitationRepository.findByStudentIdAndStatus(studentId,
                SolicitationStatus.fromCode(statusAuthorizedCode));

        if (solicitations.isEmpty()) {
            throw new IllegalStateException("Nenhuma solicitação autorizada encontrada para este estudante");
        }

// Pega a primeira solicitação autorizada
        Solicitation solicitation = solicitations.get(0);

// Obtem a foto do estudante
        byte[] studentPhotoBytes = solicitation.getStudentPhoto();

// Cria nova carteirinha
        StudentCard newCard = StudentCard.builder()
                .student(student)
                .name(student.getName())
                .institution(student.getInstitution())
                .program(student.getProgram())
                .enrollmentNumber(student.getEnrollmentNumber())
                .dateOfBirth(student.getDateOfBirth())
                .educationLevel(student.getEducationLevel())
                .validity(LocalDate.of(today.getYear() + 1, 3, 31))
                .emissionDateTime(LocalDateTime.now())
                .isCurrentCard(true)
                .studentPhoto(studentPhotoBytes)
                .validityToken(UUID.randomUUID().toString())
                .build();

        StudentCard saved = studentCardRepository.save(newCard);

// Atualiza status da solicitação para EMITIDA apenas se for diferente
        Short statusIssuedCode = (short) SolicitationStatus.ISSUED.getCode();
        if (solicitation.getStatus().getCode() != statusIssuedCode) {
            solicitationService.updateStatus(statusIssuedCode, solicitation.getId());
        }
        return StudentCardMapper.toDTO(saved);
    }
}
