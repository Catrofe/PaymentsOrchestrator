package com.orchestrator.payments.adapters.inbound.rest.v2.request

import com.orchestrator.payments.domain.FieldEnum
import com.orchestrator.payments.domain.OperatorEnum
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ConditionRequest(
    @field:NotNull
    val field: FieldEnum,
    @field:NotNull
    val operator: OperatorEnum,
    @field:NotBlank
    val value: String
)