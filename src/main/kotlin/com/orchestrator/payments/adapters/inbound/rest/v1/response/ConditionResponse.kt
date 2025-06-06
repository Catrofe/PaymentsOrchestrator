package com.orchestrator.payments.adapters.inbound.rest.v1.response

import com.orchestrator.payments.domain.FieldEnum
import com.orchestrator.payments.domain.OperatorEnum

data class ConditionResponse(
    val field: FieldEnum,
    val operator: OperatorEnum,
    val value: String
)
