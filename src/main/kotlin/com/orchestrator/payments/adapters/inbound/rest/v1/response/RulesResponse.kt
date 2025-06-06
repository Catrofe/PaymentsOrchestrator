package com.orchestrator.payments.adapters.inbound.rest.v1.response

data class RulesResponse(
    val id: String,
    val providers: List<String>,
    val conditions: List<ConditionResponse>,
)
