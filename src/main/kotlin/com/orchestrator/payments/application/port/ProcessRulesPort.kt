package com.orchestrator.payments.application.port

import com.orchestrator.payments.adapters.inbound.rest.v1.request.ProcessRulesRequest
import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionRuleDocument

interface ProcessRulesPort {

    fun execute(
        transactionData: ProcessRulesRequest,
        decisionRules: DecisionRuleDocument
    ): List<String>
}