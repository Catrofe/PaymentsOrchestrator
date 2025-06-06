package com.orchestrator.payments.application.port

import com.orchestrator.payments.adapters.inbound.rest.v2.request.DecisionBtreeRequest
import com.orchestrator.payments.adapters.inbound.rest.v2.request.ProcessBtreeRequest
import com.orchestrator.payments.adapters.inbound.rest.v2.response.DecisionBtreeResponse

interface DecisionBtreePort {
    fun createDecisionBtree(decisionBtree: DecisionBtreeRequest): DecisionBtreeResponse
    fun getDecisionBtrees(): List<DecisionBtreeResponse>
    fun getDecisionBtreeByMerchantCode(merchantCode: String): DecisionBtreeResponse
    fun processDecisionBtree(merchantCode: String, decisionBtree: ProcessBtreeRequest): List<String>
}