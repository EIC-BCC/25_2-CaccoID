package br.com.cefet.caccoId.services;

import br.com.cefet.caccoId.repositories.SolicitationRepository;
import br.com.cefet.caccoId.repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CleanupService {
    private static final Logger log = LoggerFactory.getLogger(CleanupService.class);
    @Autowired
    private SolicitationRepository solicitationRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void deleteOldRejections() {
        log.info("Iniciando deleção de carteirinhas rejeitadas.");
        try {
            List<Long> studentIds = solicitationRepository.findStudentIdsFromOldRejectedSolicitations();

            int quantitySolicitations = solicitationRepository.deleteOldRejectedSolicitations();
            log.info("{} carteirinhas foram deletadas.", quantitySolicitations);

            if (!studentIds.isEmpty()) {
                int quantityUsers = studentRepository.deleteByIdIn(studentIds);
                log.info("{} usuários foram deletados.", quantityUsers);
            }

        } catch (Exception e) {
            log.error("Erro ao efetuar remoção de carteirinhas rejeitadas: {}", e.getMessage());
        }
    }
}
