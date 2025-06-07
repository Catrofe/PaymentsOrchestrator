package com.orchestrator.payments.adapters.inbound.rest.v2.request

data class ProcessBtreeRequest(
    val amount: Int,
    val installments: Int,
    val brand: String,
    val metaData: List<MetaDataRequest> = emptyList()
)
