package com.orchestrator.payments.application.port

import com.orchestrator.payments.adapters.inbound.rest.v3.request.ProcessRequest


interface ProcessOrquestrationPort {
    fun execute(
        merchantCode: String,
        transactionData: ProcessRequest
    ): List<String>
}