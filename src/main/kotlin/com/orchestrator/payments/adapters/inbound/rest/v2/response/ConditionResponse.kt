package com.orchestrator.payments.adapters.inbound.rest.v2.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.orchestrator.payments.domain.FieldEnum
import com.orchestrator.payments.domain.MetaDataKeyType
import com.orchestrator.payments.domain.OperatorEnum

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ConditionResponse(
    val field: FieldEnum,
    val operator: OperatorEnum,
    val value: String,
    val keyType: MetaDataKeyType?,
    val key: String?
)
