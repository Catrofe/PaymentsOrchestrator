package com.orchestrator.payments.adapters.inbound.rest.v2.request

import com.orchestrator.payments.domain.TypeFlowEnum
import jakarta.validation.constraints.NotNull

data class FlowRequest(
    @field:NotNull
    val typeFlow: TypeFlowEnum,
    val providerName: String? = null,
    val condition: ConditionRequest? = null,
    val trueFlow: List<FlowRequest>? = null,
    val falseFlow: List<FlowRequest>? = null,
)
