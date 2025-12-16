# Exemplo de Escopo de Requisição com Spring WebFlux

Este projeto demonstra como gerenciar dados de escopo de requisição de forma segura em uma aplicação reativa com Spring WebFlux, garantindo que os dados de cada requisição sejam completamente isolados uns dos outros.

## O Problema

Em aplicações Spring MVC tradicionais (baseadas em Servlets), é comum usar o escopo de requisição (`@Scope("request")`) para criar beans que vivem apenas durante o ciclo de vida de uma requisição HTTP. Isso funciona porque o Spring MVC usa um modelo de "thread-por-requisição", e o escopo é gerenciado usando `ThreadLocal`.

No entanto, em aplicações reativas como o Spring WebFlux, o processamento de uma única requisição pode alternar entre diferentes threads. Isso torna o `ThreadLocal` inadequado e perigoso, pois o contexto da requisição pode ser perdido quando a execução muda de thread, levando a exceções como `IllegalStateException: No Scope registered for scope name 'request'`.

## A Solução Reativa

A solução implementada neste projeto abandona o escopo de requisição do Spring e adota a abordagem idiomática do Project Reactor: o **`Context` do Reactor**.

O `Context` do Reactor é uma estrutura de dados imutável que é propagada junto com a sequência reativa (o `Mono` ou `Flux`), permitindo que dados sejam acessados em qualquer ponto da cadeia de processamento, independentemente da thread em que o código está sendo executado.

### Fluxo de Execução

1.  **`ScopeController`**: Para cada requisição recebida, um novo objeto `Context` (nosso POJO - Plain Old Java Object) é criado. Este objeto servirá como um contêiner para os dados específicos daquela requisição.
2.  **`ContextManager`**: Este componente ajuda a injetar nosso objeto `Context` no `Context` do Reactor usando o método `.contextWrite()`. Isso "anexa" os dados da requisição à cadeia reativa.
3.  **Serviços (`AddressService`, `PersonDataService`)**: Em vez de receberem o `Context` como um parâmetro de método, os serviços agora usam `Mono.deferContextual(contextView -> ...)` para obter acesso ao `Context` do Reactor. O `ContextManager` é usado para extrair nosso objeto `Context` específico da `ContextView`.
4.  **Isolamento Garantido**: Como um novo objeto `Context` é criado para cada requisição e viaja dentro do `Context` do Reactor daquela requisição específica, os dados permanecem completamente isolados. Uma requisição não pode acessar ou interferir nos dados de outra.

Este projeto serve como um exemplo prático de como gerenciar estado por requisição de forma segura e eficaz no mundo da programação reativa com Spring.
