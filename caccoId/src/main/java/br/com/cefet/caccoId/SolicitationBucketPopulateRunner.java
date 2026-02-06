package br.com.cefet.caccoId;

import br.com.cefet.caccoId.services.SolicitationBucketPopulateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitationBucketPopulateRunner implements CommandLineRunner {

    private final SolicitationBucketPopulateService service;

    @Override
    public void run(String... args) throws Exception {
        service.populate();
    }
}
