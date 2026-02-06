package br.com.cefet.caccoId.services;

import br.com.cefet.caccoId.models.Solicitation;
import br.com.cefet.caccoId.repositories.SolicitationRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitationMigrationService {

    private final SolicitationRepository solicitationRepository;
    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    private String detectExtension(byte[] data) {

        if (data == null || data.length < 4) {
            return "";
        }

        int b0 = data[0] & 0xFF;
        int b1 = data[1] & 0xFF;
        int b2 = data[2] & 0xFF;
        int b3 = data[3] & 0xFF;

        // JPG (FF D8 FF)
        if (b0 == 0xFF && b1 == 0xD8) {
            return ".jpg";
        }

        // PNG (89 50 4E 47)
        if (b0 == 0x89 && b1 == 0x50 && b2 == 0x4E && b3 == 0x47) {
            return ".png";
        }

        // PDF (25 50 44 46)
        if (b0 == 0x25 && b1 == 0x50 && b2 == 0x44 && b3 == 0x46) {
            return ".pdf";
        }

        return ""; // fallback
    }

    public void migrateAll() {
        List<Solicitation> solicitations = solicitationRepository.findAll();

        log.info("=== Iniciando migração de {} solicitações para MinIO ===", solicitations.size());

        for (Solicitation s : solicitations) {
            migrateSingle(s);
        }

        log.info("=== Migração concluída com sucesso ===");
    }

    private void migrateSingle(Solicitation s) {
        Long id = s.getId();
        String base = id + "/";

        log.info("Migrando solicitação {}", id);

        String docs = base + "docs/";
        String proofs = base + "proofs/";

        uploadWithDetection(proofs, "enrollment_proof", s.getEnrollmentProof());
        uploadWithDetection(docs, "identity_document_front", s.getIdentityDocumentFront());
        uploadWithDetection(docs, "identity_document_back", s.getIdentityDocumentBack());
        uploadWithDetection(docs, "student_photo", s.getStudentPhoto());
        uploadWithDetection(proofs, "payment_proof", s.getPaymentProof());

        log.info("✔ Solicitação {} migrada", id);
    }

    private void uploadWithDetection(String folder, String name, byte[] data) {
        if (data == null || data.length == 0) {
            log.warn("Arquivo {} está vazio ou nulo. Pulando...", name);
            return;
        }

        String ext = detectExtension(data);
        String path = folder + name + ext;

        upload(path, data);
    }

    private void upload(String path, byte[] data) {
        try {
            if (data == null || data.length == 0) {
                log.warn("Arquivo {} está vazio ou nulo. Pulando...", path);
                return;
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(path)
                            .contentType("application/octet-stream")
                            .stream(new ByteArrayInputStream(data), data.length, -1)
                            .build()
            );

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar para MinIO: " + path, e);
        }
    }
}
