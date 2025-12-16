package br.com.danilors.scopes.service;

import br.com.danilors.scopes.beans.ContextManager;
import br.com.danilors.scopes.dto.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonDataService {

    private static final Logger logger = LoggerFactory.getLogger(PersonDataService.class);

    private final ContextManager contextManager;

    public PersonDataService(ContextManager contextManager) {
        this.contextManager = contextManager;
    }

    public Mono<Person> getPersonData() {
        logger.info("Getting person data");
        return Mono.deferContextual(contextView -> {
            var context = contextManager.getContext(contextView);
            return Mono.just(new Person(
                    "John",
                    "john@email.com",
                    "888888888"
            )).flatMap(person -> {
                context.addPeson(person);
                logger.info("Person data added to context: {}", person);
                return Mono.just(person);
            });
        });
    }
}
