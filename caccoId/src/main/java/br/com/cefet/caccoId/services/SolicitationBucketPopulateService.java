package br.com.cefet.caccoId.services;

import br.com.cefet.caccoId.models.Solicitation;
import br.com.cefet.caccoId.models.SolicitationBucket;
import br.com.cefet.caccoId.repositories.SolicitationRepository;
import br.com.cefet.caccoId.repositories.SolicitationBucketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitationBucketPopulateService {

    private final SolicitationRepository solicitationRepository;
    private final SolicitationBucketRepository solicitationBucketRepository;

    public void populate() {

        List<Solicitation> solicitations = solicitationRepository.findAll();
        log.info("=== Populando solicitation_bucket com {} registros ===", solicitations.size());

        for (Solicitation s : solicitations) {
            populateSingle(s);
        }

        log.info("=== TABELA solicitation_bucket populada com sucesso ===");
    }

    private void populateSingle(Solicitation s) {

        Long id = s.getId();
        String base = id + "/";

        // construir o registro novo
        SolicitationBucket b = SolicitationBucket.builder()
                .id(id)
                .virtualOnly(s.isVirtualOnly())
                .status(s.getStatus())
                .requestDate(s.getRequestDate())
                .corrected(s.isCorrected())
                .adminNote(s.getAdminNote())
                .needsCorrection(s.isNeedsCorrection())
                .paid(s.isPaid())
                .pickupLocation(s.getPickupLocation())
                .rejected(s.getRejected())
                .rejectedAt(s.getRejectedAt())
                .pendingEdit(s.getPendingEdit())
                .student(s.getStudent())

                // paths no minio (já existem!)
                .enrollmentProof(hasBlob(s.getEnrollmentProof()) ? base + "proofs/enrollment_proof" : null)
                .identityDocumentFront(hasBlob(s.getIdentityDocumentFront()) ? base + "docs/identity_document_front" : null)
                .identityDocumentBack(hasBlob(s.getIdentityDocumentBack()) ? base + "docs/identity_document_back" : null)
                .studentPhoto(hasBlob(s.getStudentPhoto()) ? base + "docs/student_photo" : null)
                .paymentProof(hasBlob(s.getPaymentProof()) ? base + "proofs/payment_proof" : null)

                .build();

        solicitationBucketRepository.save(b);

        log.info("✔ Registro {} migrado para solicitation_bucket", id);
    }

    private boolean hasBlob(byte[] data) {
        return data != null && data.length > 0;
    }
}
