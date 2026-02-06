package br.com.cefet.caccoId.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCardDTO {
    private Long id;
    private String name;
    private String institution;
    private String program;
    private String enrollmentNumber;
    private LocalDate dateOfBirth;
    private String educationLevel;
    private LocalDate validity;
    private LocalDateTime emissionDateTime;
    private String validityToken;
    private boolean isCurrentCard;
    private Long studentId;
    private String studentPhotoBase64;
    private String cpf;
    private String rg;
}
