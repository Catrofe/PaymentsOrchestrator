package com.orchestrator.payments.adapters.inbound.rest.v1.request

import com.orchestrator.payments.domain.FieldEnum
import com.orchestrator.payments.domain.OperatorEnum
import jakarta.validation.constraints.NotBlank

data class ConditionRequest(
    @field:NotBlank
    val field: FieldEnum,
    @field:NotBlank
    val operator: OperatorEnum,
    @field:NotBlank
    val value: String
)
