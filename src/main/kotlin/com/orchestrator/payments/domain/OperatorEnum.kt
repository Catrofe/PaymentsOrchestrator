package com.orchestrator.payments.domain

enum class OperatorEnum(val description: String) {
    EQUALS("Igual a"),
    NOT_EQUALS("Diferente de"),
    NOT_IN("Não está entre"),
    IN("Está entre"),
    GREATER_THAN("Maior que"),
    LESS_THAN("Menor que"),
    GREATER_THAN_OR_EQUAL_TO("Maior ou igual a"),
    LESS_THAN_OR_EQUAL_TO("Menor ou igual a"),
}