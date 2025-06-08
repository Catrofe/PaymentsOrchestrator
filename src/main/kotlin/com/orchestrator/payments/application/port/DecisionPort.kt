package com.orchestrator.payments.application.port

import com.orchestrator.payments.adapters.inbound.rest.v3.request.DecisionRequest
import com.orchestrator.payments.adapters.inbound.rest.v3.response.DecisionResponse

interface DecisionPort {
    fun createDecision(decision: DecisionRequest): DecisionResponse
    fun getDecisions(): List<DecisionResponse>
    fun getDecisionByMerchantCode(merchantCode: String): DecisionResponse?
}