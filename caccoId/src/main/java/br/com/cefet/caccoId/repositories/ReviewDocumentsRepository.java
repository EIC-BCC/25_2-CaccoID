package br.com.cefet.caccoId.repositories;

import br.com.cefet.caccoId.models.ReviewDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDocumentsRepository extends JpaRepository<ReviewDocuments, Long> {

    List<ReviewDocuments> findBySolicitationId(Long solicitationId);
    boolean existsBySolicitationIdAndFieldNameIgnoreCaseAndConsumedFalse(
            Long solicitationId,
            String fieldName
    );

}
