package com.orchestrator.payments.domain

import com.orchestrator.payments.adapters.inbound.rest.v3.request.MetaDataRequest
import com.orchestrator.payments.adapters.inbound.rest.v3.request.ProcessRequest

data class EvaluationContext(
    val amount: Int? = null,
    val brand: String? = null,
    val installments: Int? = null,
    val metaData: List<MetaDataRequest>? = null
){
    constructor(processRequest: ProcessRequest): this(
        amount = processRequest.amount,
        brand = processRequest.brand,
        installments = processRequest.installments,
        metaData = processRequest.metaData
    )
}
