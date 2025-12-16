package br.com.danilors.scopes.controller;

import br.com.danilors.scopes.beans.Contexto;
import br.com.danilors.scopes.beans.ContextManager;
import br.com.danilors.scopes.dto.WrapperData;
import br.com.danilors.scopes.service.CallerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/scope")
public class ScopeController {
    private static final Logger logger = LoggerFactory.getLogger(ScopeController.class);
    private final CallerService callerService;
    private final ContextManager contextManager;

    public ScopeController(CallerService callerService, ContextManager contextManager) {
        this.callerService = callerService;
        this.contextManager = contextManager;
    }

    @GetMapping
    public Mono<WrapperData> getContext() {
        logger.info("Received request for context");
        return Mono.deferContextual(contextView -> {
            Contexto contexto = new Contexto();
            contexto.init();
            return callerService.call()
                    .then(Mono.deferContextual(innerContextView -> {
                        var finalContext = contextManager.getContext(innerContextView);
                        return Mono.just(finalContext.getWrapperData());
                    }))
                    .contextWrite(contextManager.setContext(contexto));
        }).doOnSuccess(data -> logger.info("Successfully retrieved context data: {}", data));
    }
}
