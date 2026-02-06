package br.com.cefet.caccoId.services;

import br.com.cefet.caccoId.mappers.SolicitationDTOMapper;
import br.com.cefet.caccoId.models.Solicitation;
import br.com.cefet.caccoId.models.User;
import br.com.cefet.caccoId.models.enums.SolicitationStatus;
import br.com.cefet.caccoId.repositories.SolicitationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Null;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class SolicitationService {
    @Autowired
    private SolicitationRepository solicitationRepository;
    private static final Logger log = LoggerFactory.getLogger(SolicitationService.class);

    public Solicitation getSolicitation(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            log.info("Retornando dados da solicitação de: {}", user.getUsername());
            return solicitationRepository.getSolicitationStatusByLoggedUser(user.getId());
        } catch (Exception e) {
            log.error("Falha ao retornar dados da solicitação: {}", e.getMessage());
            return null;
        }
    }

    public Map<String, ?> formatSolicitation(Solicitation solicitation){
        var requestDate = solicitation.getRequestDate();

        var requestDateFormated = String.format("%02d/%02d/%d",
                requestDate.getDayOfMonth(),
                requestDate.getMonthValue(),
                requestDate.getYear());

        byte[] photoBytes = solicitation.getStudentPhoto();

        if (photoBytes == null || photoBytes.length == 0) {
            log.warn("A solicitação com ID {} não possui foto do estudante.", solicitation.getId());
        }

        Tika tika = new Tika();
        String mimeType = tika.detect(photoBytes);
        String base64 = Base64.getEncoder().encodeToString(photoBytes);
        return Map.of(
                "studentName", solicitation.getStudent().getName(),
                "requestDate", requestDateFormated,
                "status", solicitation.getStatus().getStatus(),
                "photo", "data:" + mimeType + ";base64," + base64,
                "rejected", solicitation.getRejected(),
                "pendingEdit", solicitation.getPendingEdit(),
                "studentId", solicitation.getStudent().getId());
    }


    @Transactional
    public Solicitation updateStatus(Short newStatus, Long solicitationId){

        SolicitationStatus[] statuses = SolicitationStatus.values();

        if (newStatus < 0 || newStatus >= statuses.length) {
            throw new IllegalArgumentException("Status inválido.");
        }
        Solicitation solicitation = null;

        if(solicitationId != null){
            solicitation = this.getSolicitationById(solicitationId); // Corrigido: busca pelo ID
        } else {
            solicitation = this.getSolicitation(); // Mantém busca pelo usuário logado se ID for nulo
        }
        SolicitationStatus updatedStatus = statuses[newStatus];


        if(!(this.finalStatusReached(solicitation.getStatus()))){
            solicitation.setStatus(updatedStatus);
            solicitationRepository.save(solicitation);
        }
        return solicitation;
    }

    public Solicitation setSolicitationInitialValues(Solicitation solicitation){
        solicitation.setStatus(SolicitationStatus.UNDER_REVIEW);
        solicitation.setPaid(false);
        solicitation.setNeedsCorrection(false);
        solicitation.setCorrected(false);
        solicitation.setAdminNote("");
        if(solicitation.isVirtualOnly()) {
            solicitation.setPickupLocation("");
        }
        ZonedDateTime zonedNow = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        solicitation.setRequestDate(zonedNow.toLocalDateTime());
        solicitation.setPendingEdit(false);
        solicitation.setRejected(false);
        return solicitation;
    }

    public boolean finalStatusReached(SolicitationStatus solicitationStatus){
        return solicitationStatus == SolicitationStatus.ISSUED;
    }

    @Transactional
    public void rejectById(Long id){
        Solicitation solicitation = solicitationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada."));

        solicitation.setRejected(true);
        solicitation.setRejectedAt(LocalDateTime.now());
        solicitation.setStatus(SolicitationStatus.EXCLUDED);
        solicitationRepository.save(solicitation);
        log.info("Solicitação com ID {} rejeitada com sucesso. Status definido como '{}'.", id, SolicitationStatus.EXCLUDED.getStatus());
    }

    @Transactional
    public void authorizeById(Long id){
        Solicitation solicitation = solicitationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada."));

        solicitation.setStatus(SolicitationStatus.AUTHORIZED);
        solicitationRepository.save(solicitation);
        log.info("Solicitação com ID {} autorizada com sucesso. Status definido como '{}'.", id, SolicitationStatus.AUTHORIZED.getStatus());
    }

    @Transactional
    public void revertAuthorizationById(List<Long> ids) {
        log.debug("Iniciando reversão de autorização para {} solicitações: {}.", ids.size(), ids);
        for (Long id : ids) {
            Solicitation solicitation = solicitationRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Solicitação " + id + " não encontrada."));

            solicitation.setRejected(false);
            solicitation.setRejectedAt(null);
            solicitation.setStatus(SolicitationStatus.UNDER_REVIEW);
            solicitationRepository.save(solicitation);
            log.info("Autorização revertida com sucesso para a solicitação ID {}.", id);
        }
        log.debug("Reversão de autorizações concluída.");
    }

    public List<Map<String, ?>> findSolicitationsByStatus(SolicitationStatus status){
        var solicitations = this.solicitationRepository.findByStatus(status);

        List<Map<String, ?>> formattedSolicitations = new ArrayList<>();

        for (Solicitation solicitation : solicitations) {
            var formattedSolicitation = SolicitationDTOMapper.toFrontendDto(solicitation);
            formattedSolicitations.add(formattedSolicitation);
        }

        return formattedSolicitations;
    }

    public Solicitation getSolicitationById(Long id) {
        return solicitationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada."));
    }
}
