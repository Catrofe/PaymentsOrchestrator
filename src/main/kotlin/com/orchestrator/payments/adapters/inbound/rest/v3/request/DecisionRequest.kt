package com.orchestrator.payments.adapters.inbound.rest.v3.request

import com.orchestrator.payments.adapters.inbound.rest.v3.request.validators.annotation.ValidDecisionRequest
import com.orchestrator.payments.domain.DecisionType

@ValidDecisionRequest
data class DecisionRequest(
    val merchantCode: String? = null,
    val name: String,
    val description: String? = null,
    val decisionType: DecisionType,
    val rules: List<RulesRequest>? = null,
    val flow: List<FlowRequest>? = null
)
