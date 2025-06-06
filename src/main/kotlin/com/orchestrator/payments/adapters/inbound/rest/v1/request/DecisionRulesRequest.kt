package com.orchestrator.payments.adapters.inbound.rest.v1.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank


data class DecisionRulesRequest(
    @field:NotBlank
    @field:Valid
    val rules: List<RulesRequest>,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val description: String? = null,
)
