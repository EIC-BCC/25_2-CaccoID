package br.com.cefet.caccoId.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "correction_solicitation")
@Data
@Builder
public class ReviewDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solicitation_id", nullable = false)
    private Long solicitationId;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "field_name", length = 50)
    private String fieldName;

    @Column(name = "consumed", nullable = false)
    private Boolean consumed = false;

    @Column(name = "admin_note", length = 255)
    private String adminNote;

    @Column(name = "consumed_by_email", nullable = false)
    private Boolean consumedByEmail = false;

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate = LocalDateTime.now();

    // Add this method to return the field name
    public String getFieldName() {
        // Replace 'fieldName' with the actual field name variable
        return this.fieldName;
    }
    
}

