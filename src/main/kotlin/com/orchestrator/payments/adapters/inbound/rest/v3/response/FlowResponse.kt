package com.orchestrator.payments.adapters.inbound.rest.v3.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.orchestrator.payments.domain.TypeFlowEnum

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FlowResponse(
    val typeFlow: TypeFlowEnum,
    val providerName: String? = null,
    val condition: ConditionResponse? = null,
    val trueFlow: List<FlowResponse>? = null,
    val falseFlow: List<FlowResponse>? = null,
)
