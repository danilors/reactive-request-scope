package br.com.danilors.scopes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CallerService {

    private static final Logger logger = LoggerFactory.getLogger(CallerService.class);

    private final AddressService addressService;
    private final PersonDataService personDataService;

    public CallerService(AddressService addressService, PersonDataService personDataService) {
        this.addressService = addressService;
        this.personDataService = personDataService;
    }

    public Mono<Void> call() {
        logger.info("Calling address and person services");
        return Mono.zip(
                addressService.getAddress(),
                personDataService.getPersonData()
        ).doOnSuccess(data -> logger.info("Successfully called address and person services"))
        .then();
    }
}
