package com.orchestrator.payments.adapters.outbound.persistence.model

import com.orchestrator.payments.adapters.inbound.rest.v1.request.DecisionRulesRequest
import com.orchestrator.payments.domain.FieldEnum
import com.orchestrator.payments.domain.MetaDataKeyType
import com.orchestrator.payments.domain.OperatorEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "decision_rules")
data class DecisionRuleDocument(
    @Id
    val merchantCode: String,
    val rules: List<Rules>,
    val name: String,
    val description: String? = null,
){
    constructor(decisionRules: DecisionRulesRequest) : this(
        merchantCode = UUID.randomUUID().toString(),
        rules = decisionRules.rules.map { rule ->
            Rules(
                id = UUID.randomUUID().toString(),
                providers = rule.providers,
                conditions = rule.conditions.map { condition ->
                    Condition(
                        field = condition.field,
                        operator = condition.operator,
                        value = condition.value
                    )
                }
            )
        },
        name = decisionRules.name,
        description = decisionRules.description
    )
}

data class Rules(
    val id: String,
    val providers: List<String>,
    val conditions: List<Condition>,
)

data class Condition(
    val field: FieldEnum,
    val operator: OperatorEnum,
    val value: String,
    val keyType: MetaDataKeyType? = null,
    val key: String? = null
)
