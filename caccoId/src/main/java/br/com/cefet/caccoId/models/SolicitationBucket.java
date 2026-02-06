package br.com.cefet.caccoId.models;

import br.com.cefet.caccoId.models.enums.SolicitationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitation_bucket")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of = "id")

//É uma cópia da classe Solicitation, mas com os campos de arquivo alterados para String (URLs)
public class SolicitationBucket {

    @Id
    private Long id;

    private boolean virtualOnly;

    @Enumerated(EnumType.STRING)
    private SolicitationStatus status;

    private LocalDateTime requestDate;

    private boolean corrected;

    @Column(length = 255)
    private String adminNote;

    private boolean needsCorrection;

    private boolean paid;

    @Column(length = 200)
    private String pickupLocation;

    private Boolean rejected;

    private LocalDateTime rejectedAt;

    private Boolean pendingEdit;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    // === AQUI MUDOU ===
    @Column(name = "enrollment_proof", length = 300)
    private String enrollmentProof;
    @Column(name = "identity_document_front", length = 300)
    private String identityDocumentFront;
    @Column(name = "identity_document_back", length = 300)
    private String identityDocumentBack;
    @Column(name = "student_photo", length = 300)
    private String studentPhoto;
    @Column(name = "payment_proof", length = 300)
    private String paymentProof;
}
