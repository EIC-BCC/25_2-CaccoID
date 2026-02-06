package br.com.cefet.caccoId.mappers;

import br.com.cefet.caccoId.models.Solicitation;
import br.com.cefet.caccoId.models.Student;
import org.apache.tika.Tika;

import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SolicitationDTOMapper {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");


    private static String toDataUrl(byte[] bytes) {
        if (bytes == null) return null;
        Tika tika = new Tika();
        String mimeType = tika.detect(bytes);
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return "data:" + mimeType + ";base64," + base64;
    }

    public static Map<String, Object> toFrontendDto(Solicitation solicitation) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", solicitation.getId());
        map.put("virtualOnly", solicitation.isVirtualOnly());
        map.put("status", solicitation.getStatus() != null ? solicitation.getStatus().getStatus() : null);
        map.put("requestDate", solicitation.getRequestDate() != null ? solicitation.getRequestDate().format(DATE_TIME_FORMAT) : null);
        map.put("corrected", solicitation.isCorrected());
        map.put("adminNote", solicitation.getAdminNote());
        map.put("needsCorrection", solicitation.isNeedsCorrection());
        map.put("paid", solicitation.isPaid());
        map.put("pickupLocation", solicitation.getPickupLocation());
        map.put("rejected", solicitation.getRejected());
        map.put("rejectedAt", solicitation.getRejectedAt() != null ? solicitation.getRejectedAt().format(DATE_TIME_FORMAT) : null);
        map.put("pendingEdit", solicitation.getPendingEdit());

        // Blobs como data URL
        map.put("enrollmentProof", toDataUrl(solicitation.getEnrollmentProof()));
        map.put("identityDocumentFront", toDataUrl(solicitation.getIdentityDocumentFront()));
        map.put("identityDocumentBack", toDataUrl(solicitation.getIdentityDocumentBack()));
        map.put("studentPhoto", toDataUrl(solicitation.getStudentPhoto()));
        map.put("paymentProof", toDataUrl(solicitation.getPaymentProof()));

        // Student
        Student s = solicitation.getStudent();
        if (s != null) {
            Map<String, Object> studentMap = new HashMap<>();
            studentMap.put("id", s.getId());
            studentMap.put("name", s.getName());
            studentMap.put("email", s.getEmail());
            studentMap.put("rg", s.getRg());
            studentMap.put("cpf", s.getCpf());
            studentMap.put("telephone", s.getTelephone());
            studentMap.put("dateOfBirth", s.getDateOfBirth() != null ? s.getDateOfBirth().format(DATE_FORMAT) : null);
            studentMap.put("enrollmentNumber", s.getEnrollmentNumber());
            studentMap.put("program", s.getProgram());
            studentMap.put("institution", s.getInstitution());
            studentMap.put("educationLevel", s.getEducationLevel());
            map.put("student", studentMap);
        }

        return map;
    }
}
