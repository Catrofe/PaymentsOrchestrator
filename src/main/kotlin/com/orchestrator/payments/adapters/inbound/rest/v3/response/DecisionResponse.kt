package com.orchestrator.payments.adapters.inbound.rest.v3.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.orchestrator.payments.domain.DecisionType

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DecisionResponse(
    val merchantCode: String,
    val name: String,
    val description: String? = null,
    val decisionType: DecisionType,
    val rules: List<RulesResponse>? = null,
    val flow: List<FlowResponse>? = null
)
