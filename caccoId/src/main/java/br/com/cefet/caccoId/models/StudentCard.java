package br.com.cefet.caccoId.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "student_card")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of = "id")
public class StudentCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "institution", length = 70, nullable = false)
    private String institution;

    @Column(name = "program", length = 40, nullable = false)
    private String program;

    @Column(name = "enrollment_number", length = 20, nullable = false)
    private String enrollmentNumber;

    private LocalDate dateOfBirth;

    @Column(name = "education_level", length = 20, nullable = false)
    private String educationLevel;

    private LocalDate validity;

    private LocalDateTime emissionDateTime;

    private boolean isCurrentCard;

    @Column(name = "validity_token", length = 255, nullable = false)
    private String validityToken;

    @Lob
    @Column(name = "student_photo", columnDefinition = "MEDIUMBLOB")
    private byte[] studentPhoto;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @PrePersist
    public void prePersist() {
        if (emissionDateTime == null) {
            emissionDateTime = LocalDateTime.now();
        }
    }
}