package com.orchestrator.payments.application.port

import com.orchestrator.payments.adapters.inbound.rest.v1.request.DecisionRulesRequest
import com.orchestrator.payments.adapters.inbound.rest.v1.request.ProcessRulesRequest
import com.orchestrator.payments.adapters.inbound.rest.v1.response.DecisionRulesResponse

interface DecisionRulesPort{
    fun createDecisionRule(decisionRules: DecisionRulesRequest): DecisionRulesResponse
    fun getDecisionRules(): List<DecisionRulesResponse>
    fun getDecisionRuleByMerchantCode(merchantCode: String): DecisionRulesResponse
    fun processDecisionRule(merchantCode: String, decisionRules: ProcessRulesRequest): List<String>
}