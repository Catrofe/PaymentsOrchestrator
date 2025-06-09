# Payment Orchestrator

## Sobre o Projeto

O Payment Orchestrator é uma aplicação desenvolvida em Kotlin com Spring Boot que gerencia e orquestra fluxos de pagamento através de regras de decisão configuráveis. O sistema permite definir condições e fluxos de processamento de pagamentos de forma dinâmica, direcionando transações para diferentes provedores com base em regras de negócio.

## Funcionalidades Principais

- **Orquestração de Pagamentos**: Direciona transações para os provedores de pagamento mais adequados com base em regras configuráveis.
- **Regras de Decisão**: Suporta dois tipos de fluxo de decisão:
    - Regras simples (`RULES`)
    - Árvores de decisão (`BTREE`)
- **Avaliação de Condições**: Permite criar condições baseadas em diferentes campos da transação:
    - Valor (`AMOUNT`)
    - Bandeira do cartão (`BRAND`)
    - Número de parcelas (`INSTALLMENT`)
    - Metadados personalizados (`METADATA`)
- **Operadores Lógicos**: Suporta diversos operadores para avaliação de condições:
    - Igual a (`EQUALS`)
    - Diferente de (`NOT_EQUALS`)
    - Está entre (`IN`)
    - Não está entre (`NOT_IN`)
    - Maior que (`GREATER_THAN`)
    - Menor que (`LESS_THAN`)
    - Maior ou igual a (`GREATER_THAN_OR_EQUAL_TO`)
    - Menor ou igual a (`LESS_THAN_OR_EQUAL_TO`)
- **Gerenciamento de Regras**: API para criação e consulta de regras de decisão para diferentes comerciantes.

## Tecnologias Utilizadas

- Kotlin
- Spring Boot
- Gradle
- MongoDB (inferido pela estrutura do código)
- Docker

## Estrutura do Projeto

O projeto segue uma arquitetura hexagonal (Ports and Adapters):

- **Domain**: Contém as entidades e enums do domínio
- **Application**: Implementa a lógica de negócio através de portas e adaptadores
- **Adapters**: Gerencia a comunicação com o mundo externo (APIs REST, banco de dados)

## Como Executar

### Usando Docker

```bash
docker-compose up -d
```

### Localmente

```bash
./gradlew bootRun
```