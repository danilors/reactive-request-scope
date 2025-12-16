package br.com.danilors.scopes.service;

import br.com.danilors.scopes.beans.ContextManager;
import br.com.danilors.scopes.dto.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AddressService {
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final ContextManager contextManager;

    public AddressService(ContextManager contextManager) {
        this.contextManager = contextManager;
    }


    public Mono<Address> getAddress() {
        logger.info("Getting address");
        return Mono.deferContextual(contextView -> {
            var context = contextManager.getContext(contextView);
            return Mono.just(new Address("one st", 1)).flatMap(address -> {
                context.addAddress(address);
                logger.info("Address added to context: {}", address);
                return Mono.just(address);
            });
        });
    }
}
