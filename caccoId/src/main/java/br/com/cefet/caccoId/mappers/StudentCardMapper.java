package br.com.cefet.caccoId.mappers;

import br.com.cefet.caccoId.dtos.StudentCardDTO;
import br.com.cefet.caccoId.models.Student;
import br.com.cefet.caccoId.models.StudentCard;

import java.util.Base64;

public class StudentCardMapper {

    public static StudentCardDTO toDTO(StudentCard studentCard) {
        if (studentCard == null) return null;

        return StudentCardDTO.builder()
                .id(studentCard.getId())
                .name(studentCard.getName())
                .institution(studentCard.getInstitution())
                .program(studentCard.getProgram())
                .enrollmentNumber(studentCard.getEnrollmentNumber())
                .dateOfBirth(studentCard.getDateOfBirth())
                .educationLevel(studentCard.getEducationLevel())
                .validity(studentCard.getValidity())
                .emissionDateTime(studentCard.getEmissionDateTime())
                .validityToken(studentCard.getValidityToken())
                .isCurrentCard(studentCard.isCurrentCard())
                .studentId(studentCard.getStudent() != null ? studentCard.getStudent().getId() : null)
                .studentPhotoBase64(studentCard.getStudentPhoto() != null ?
                        Base64.getEncoder().encodeToString(studentCard.getStudentPhoto()) : null)
                .cpf(studentCard.getStudent() != null ? studentCard.getStudent().getCpf() : null)
                .rg(studentCard.getStudent() != null ? studentCard.getStudent().getRg() : null)
                .build();
    }

    public static StudentCard toEntity(StudentCardDTO dto) {
        if (dto == null) return null;

        StudentCard.StudentCardBuilder builder = StudentCard.builder()
                .id(dto.getId())
                .name(dto.getName())
                .institution(dto.getInstitution())
                .program(dto.getProgram())
                .enrollmentNumber(dto.getEnrollmentNumber())
                .dateOfBirth(dto.getDateOfBirth())
                .educationLevel(dto.getEducationLevel())
                .validity(dto.getValidity())
                .emissionDateTime(dto.getEmissionDateTime())
                .validityToken(dto.getValidityToken())
                .isCurrentCard(dto.isCurrentCard());

        // Se vier foto em base64, converte pra byte[]
        if (dto.getStudentPhotoBase64() != null) {
            builder.studentPhoto(Base64.getDecoder().decode(dto.getStudentPhotoBase64()));
        }

        // Associa o estudante apenas pelo ID (não carrega do banco aqui)
        if (dto.getStudentId() != null) {
            Student student = new Student();
            student.setId(dto.getStudentId());
            builder.student(student);
        }

        return builder.build();
    }
}
