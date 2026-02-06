package br.com.cefet.caccoId.repositories;

import br.com.cefet.caccoId.models.StudentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentCardRepository extends JpaRepository<StudentCard, Long> {

    Optional<StudentCard> findByStudentId(@Param("studentId") Long studentId);

    List<StudentCard> findAllByStudentId(Long studentId); // <<< adicionado

    @Modifying
    @Transactional
    @Query("DELETE FROM StudentCard sc WHERE sc.student.id IN :studentIds")
    void deleteByStudentIds(@Param("studentIds") List<Long> studentIds);

    @Query("SELECT sc.student.id FROM StudentCard sc WHERE sc.validity < :currentDateTime")
    List<Long> findStudentIdsWithExpiredCards(@Param("currentDateTime") LocalDateTime currentDateTime);

    Optional<StudentCard> findByValidityToken(String validityToken);
}

