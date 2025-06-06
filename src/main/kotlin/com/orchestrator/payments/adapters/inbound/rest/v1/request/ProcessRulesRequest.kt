package com.orchestrator.payments.adapters.inbound.rest.v1.request

data class ProcessRulesRequest(
    val amount: Double,
    val installments: Int,
    val brand: String,
)
