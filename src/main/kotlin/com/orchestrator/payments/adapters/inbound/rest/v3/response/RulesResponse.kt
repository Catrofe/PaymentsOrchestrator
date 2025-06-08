package com.orchestrator.payments.adapters.inbound.rest.v3.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RulesResponse(
    val id: String,
    val providers: List<String>,
    val conditions: List<ConditionResponse>,
)
