package br.com.danilors.scopes.beans;

import org.springframework.stereotype.Component;
import reactor.util.context.ContextView;

@Component
public class ContextManager {

    private static final String CONTEXT_KEY = "request-context";

    public reactor.util.context.Context setContext(Contexto contexto) {
        return reactor.util.context.Context.of(CONTEXT_KEY, contexto);
    }

    public Contexto getContext(ContextView contextView) {
        return contextView.get(CONTEXT_KEY);
    }
}
