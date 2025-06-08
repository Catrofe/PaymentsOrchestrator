package com.orchestrator.payments.adapters.inbound.rest.v3.request

data class ProcessRequest (
    val amount: Int,
    val installments: Int,
    val brand: String,
    val metaData: List<MetaDataRequest>? = null
)