package br.com.cefet.caccoId.repositories;

import br.com.cefet.caccoId.models.Solicitation;
import br.com.cefet.caccoId.models.enums.SolicitationStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface SolicitationRepository extends JpaRepository<Solicitation, Long> {
    @Query(value = """
            SELECT s.*
            FROM solicitation s
            JOIN student st ON s.student_id = st.id
            WHERE st.user_id = :userId
            """, nativeQuery = true)
    Solicitation getSolicitationStatusByLoggedUser(Long userId);

    @Modifying
    @Query(value = """
        DELETE FROM solicitation
        WHERE rejected = true
          AND rejected_at IS NOT NULL
          AND rejected_at < NOW() - INTERVAL 7 DAY
        """, nativeQuery = true)
    int deleteOldRejectedSolicitations();

    @Query(value = """
    SELECT s.student_id
    FROM solicitation s
    WHERE s.rejected = true
      AND s.rejected_at IS NOT NULL
      AND s.rejected_at < NOW() - INTERVAL 7 DAY
    """, nativeQuery = true)
    List<Long> findStudentIdsFromOldRejectedSolicitations();

    List<Solicitation> findByStatus(SolicitationStatus status);
    boolean existsByStudentIdAndStatus(Long studentId, SolicitationStatus statusCode);
    List<Solicitation> findByStudentIdAndStatus(Long studentId, SolicitationStatus status);


}
