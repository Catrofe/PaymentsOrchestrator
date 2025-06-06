package com.orchestrator.payments.adapters.inbound.rest.v2.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DecisionBtreeResponse(
    val merchantCode: String,
    val name: String,
    val description: String? = null,
    val flow: List<FlowResponse>
)
