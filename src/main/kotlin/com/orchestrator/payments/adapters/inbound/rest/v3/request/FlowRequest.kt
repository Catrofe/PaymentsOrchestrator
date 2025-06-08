package com.orchestrator.payments.adapters.inbound.rest.v3.request

import com.orchestrator.payments.adapters.inbound.rest.v3.request.validators.annotation.ValidFlowRequest
import com.orchestrator.payments.domain.TypeFlowEnum
import jakarta.validation.constraints.NotNull

@ValidFlowRequest
data class FlowRequest(
    @field:NotNull
    val typeFlow: TypeFlowEnum,
    val providerName: String? = null,
    val condition: ConditionRequest? = null,
    val trueFlow: List<FlowRequest>? = null,
    val falseFlow: List<FlowRequest>? = null,
)
