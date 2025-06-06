package com.orchestrator.payments.adapters.inbound.rest.v2.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class DecisionBtreeRequest(
    @field:NotBlank
    val name: String,
    val description: String? = null,
    @field:NotNull
    @field:Valid
    val flow: List<FlowRequest>
)
