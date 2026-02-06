package br.com.cefet.caccoId.services;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import br.com.cefet.caccoId.repositories.ReviewDocumentsRepository;
import br.com.cefet.caccoId.repositories.SolicitationRepository;
import jakarta.persistence.EntityNotFoundException;
import br.com.cefet.caccoId.enums.ReviewableField;
import br.com.cefet.caccoId.models.ReviewDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class ReviewDocumentsService {
    @Autowired
    private  ReviewDocumentsRepository reviewDocumentsRepository;
    @Autowired
    private  SolicitationRepository solicitationRepository;

    @Transactional
public void markForReview(Long solicitationId, List<String> fieldNames) {
    if (fieldNames == null || fieldNames.isEmpty()) {
        throw new IllegalArgumentException("A lista de campos não pode estar vazia.");
    }


    var solicitation = solicitationRepository.findById(solicitationId)
            .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada."));
    Long studentId = solicitationId;
    ReviewableField[] validFields = ReviewableField.values();
    for (String fieldName : fieldNames) {
        boolean isValidField = false;
        for (ReviewableField validField : validFields) {
            if (validField.name().equalsIgnoreCase(fieldName)) {
                isValidField = true;
                break;
            }
        }
        if (!isValidField) {
            throw new IllegalArgumentException("Campo inválido para revisão: " + fieldName);
        }
        boolean jaExiste = reviewDocumentsRepository
                .existsBySolicitationIdAndFieldNameIgnoreCaseAndConsumedFalse(solicitationId, fieldName);
        if (jaExiste) {
            throw new IllegalArgumentException("Já existe uma solicitação de revisão pendente para o campo: " + fieldName);
        }
        ReviewDocuments document = ReviewDocuments.builder()
                .solicitationId(solicitationId)
                .studentId(studentId)
                .fieldName(fieldName)
                .consumed(false)
                .consumedByEmail(false)
                .requestDate(LocalDateTime.now())
                .build();

        reviewDocumentsRepository.save(document);
    }
}
}
