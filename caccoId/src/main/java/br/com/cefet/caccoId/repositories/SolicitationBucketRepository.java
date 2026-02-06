package br.com.cefet.caccoId.repositories;

import br.com.cefet.caccoId.models.SolicitationBucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitationBucketRepository extends JpaRepository<SolicitationBucket, Long> {
}
