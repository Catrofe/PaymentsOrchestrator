package com.orchestrator.payments.adapters.inbound.rest.v1.response

data class DecisionRulesResponse(
    val merchantCode: String,
    val rules: List<RulesResponse>,
    val name: String,
    val description: String? = null,
) {
}