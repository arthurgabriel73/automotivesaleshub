# automotivesaleshub

## Introdução

Este projeto é uma API que gerencia o estoque e vendas de veículos. Ele permite o cadastro de veículos, controle de
estoque, vendas e pagamentos.

### Apresentação: https://youtu.be/3-hP1oaoekg

## Sumário

* [Tecnologias](#tecnologias)
* [Ferramentas Utilizadas](#ferramentas-utilizadas)
* [Documentação](#documentação)
    * [Domain Storytelling](#domain-storytelling)
    * [Event Storming](#event-storming)
    * [Cenários](#cenários)
* [Arquitetura Proposta](#arquitetura-proposta)
* [Execução do Projeto](#execução-do-projeto)
* [Acessando a API](#acessando-a-api)
* [Dados de API](#dados-de-api)
* [Cobertura de Testes](#cobertura-de-testes)

## Tecnologias

- Java 21
- Spring Boot
- API REST
- PostgreSQL
- Docker
- docker-compose

## Ferramentas Utilizadas

- IntelliJ IDEA
- Docker
- Gradle

## Documentação

### Domain Storytelling

#### O Domain Storytelling é uma técnica que ajuda a entender o domínio do problema e a modelar o sistema de forma colaborativa.

[Voltar ao topo](#automotivesaleshub)
![Domain Storytelling](src/docs/domain-storytelling-v0.png)

### Event Storming

#### O Event Storming é uma técnica de modelagem de domínio que ajuda a identificar eventos, comandos e agregados no sistema.

[Voltar ao topo](#automotivesaleshub)
![Event Storming](src/docs/event-storming-v0.jpg)

### Cenários

[Voltar ao topo](#automotivesaleshub)

#### Os cenários são descrições de como o sistema deve se comportar em diferentes situações. Eles ajudam a entender os requisitos e a validar o comportamento do sistema. Para isso utilizamos a linguagem Gherkin, que é uma linguagem de domínio específico para descrever cenários de teste. E nesta API, utilizamos o Cucumber para executar os testes automatizados baseados nesses cenários.

##### Exemplo de Cenário:

```gherkin
Feature: Payment Update
  As a external payment service partner
  I want to update a payment status after processing externally
  So that the order flow can continue

  Scenario: Successfully update an existing payment
    Given the system has a pending payment
    And the external payment service has a valid payment update
    When the external payment service submits the payment update
    Then the payment status should be updated successfully

  Scenario: Fail to update a payment with invalid data
    Given the system has a pending payment
    And the external payment service has an invalid form to update the payment status
    When the external payment service submits the payment update
    Then the system should reject the payment update with a bad request error

  Scenario: Fail to update a non-existent payment
    Given the system has a pending payment
    And the external payment service attempts to update a non-existent payment
    When the external payment service submits the payment update
    Then the system should reject the payment update with a not found error
```

Para rodar os testes, você pode utilizar o comando:

```bash
./gradlew clean test koverHtmlReport
```

Os demais cenários estão disponíveis no diretório `src/test/resources/features`.

## Arquitetura

[Voltar ao topo](#automotivesaleshub)
![Arquitetura](src/docs/arquitetura-v0.jpg)

## Execução do Projeto

[Voltar ao topo](#automotivesaleshub)

```bash
kubectl apply -f postgres-secret.yaml
kubectl apply -f postgres_hub_deployment.yaml
kubectl apply -f postgres_service_deployment.yaml
kubectl apply -f automotivesaleshub_deployment.yaml
kubectl apply -f automotivesalesservice_deployment.yaml
kubectl apply -f metrics.yaml
kubectl apply -f app_hpa.yaml
```
