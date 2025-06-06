package com.orchestrator.payments.adapters.inbound.rest.v2.request

data class ProcessBtreeRequest(
    val amount: Double,
    val installments: Int,
    val brand: String,
)
