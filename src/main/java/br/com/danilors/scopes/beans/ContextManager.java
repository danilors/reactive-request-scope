package br.com.danilors.scopes.beans;

import org.springframework.stereotype.Component;
import reactor.util.context.ContextView;

@Component
public class ContextManager {

    private static final String CONTEXT_KEY = "request-context";

    public reactor.util.context.Context setContext(br.com.danilors.scopes.beans.Context context) {
        return reactor.util.context.Context.of(CONTEXT_KEY, context);
    }

    public br.com.danilors.scopes.beans.Context getContext(ContextView contextView) {
        return contextView.get(CONTEXT_KEY);
    }
}
