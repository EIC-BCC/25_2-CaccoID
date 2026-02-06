package br.com.cefet.caccoId.models;

import br.com.cefet.caccoId.models.enums.SolicitationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitation")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of = "id")
public class Solicitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean virtualOnly;

    private SolicitationStatus status;

    private LocalDateTime requestDate;

    private boolean corrected;

    private String adminNote;

    private boolean needsCorrection;

    private boolean paid;
    
    private String pickupLocation;

    private Boolean rejected;

    private LocalDateTime rejectedAt;

    private Boolean pendingEdit;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] enrollmentProof;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] identityDocumentFront;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] identityDocumentBack;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] studentPhoto;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] paymentProof;
}
