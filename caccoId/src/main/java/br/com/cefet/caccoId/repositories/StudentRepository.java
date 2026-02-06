package br.com.cefet.caccoId.repositories;

import br.com.cefet.caccoId.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);

    @Modifying
    @Query("DELETE FROM Student s WHERE s.id IN :ids")
    int deleteByIdIn(@Param("ids") List<Long> ids);
}
