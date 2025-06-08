package com.orchestrator.payments.adapters.inbound.rest.v3.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

data class RulesRequest(
    @field:NotBlank
    val providers: List<String>,
    @field:NotBlank
    @field:Valid
    val conditions: List<ConditionRequest>
)
