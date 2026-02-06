package br.com.cefet.caccoId.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
@Builder
@EqualsAndHashCode(of = "id")
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Column(name = "rg", length = 20, nullable = false)
    private String rg;

    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @Column(name = "telephone", length = 20, nullable = false)
    private String telephone;

    private LocalDate dateOfBirth;

    @Column(name = "enrollment_number", length = 20, nullable = false)
    private String enrollmentNumber;

    @Column(name = "program", length = 40, nullable = false)
    private String program;

    @Column(name = "institution", length = 70, nullable = false)
    private String institution;

    @Column(name = "education_level", length = 20, nullable = false)
    private String educationLevel;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
