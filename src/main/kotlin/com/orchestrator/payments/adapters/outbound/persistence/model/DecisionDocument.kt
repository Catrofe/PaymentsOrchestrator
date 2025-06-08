package com.orchestrator.payments.adapters.outbound.persistence.model

import com.orchestrator.payments.adapters.inbound.rest.v3.request.DecisionRequest
import com.orchestrator.payments.adapters.inbound.rest.v3.request.FlowRequest
import com.orchestrator.payments.domain.DecisionType
import com.orchestrator.payments.domain.FieldEnum
import com.orchestrator.payments.domain.MetaDataKeyType
import com.orchestrator.payments.domain.OperatorEnum
import com.orchestrator.payments.domain.TypeFlowEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "decision_document")
data class DecisionDocument(
    @Id
    val merchantCode: String,
    @Indexed(unique = true)
    val name: String,
    val description: String? = null,
    val decisionType: DecisionType,
    val flow: List<FlowDocument>? = null,
    val rules: List<Rules>? = null
){
    constructor(decisionRequest: DecisionRequest) : this(
        merchantCode = decisionRequest.merchantCode ?: UUID.randomUUID().toString(),
        name = decisionRequest.name,
        description = decisionRequest.description,
        decisionType = decisionRequest.decisionType,
        flow = decisionRequest.flow?.map { mapFlowRequestToFlowDocument(it) },
        rules = decisionRequest.rules?.map { rule ->
            Rules(
                id = UUID.randomUUID().toString(),
                providers = rule.providers,
                conditions = rule.conditions.map { condition ->
                    Condition(
                        field = condition.field,
                        operator = condition.operator,
                        value = condition.value,
                        keyType = condition.keyType,
                        key = condition.key
                    )
                }
            )
        }
    )

    companion object {
        private fun mapFlowRequestToFlowDocument(flowRequest: FlowRequest): FlowDocument {
            return FlowDocument(
                typeFlow = flowRequest.typeFlow,
                providerName = flowRequest.providerName,
                condition = flowRequest.condition?.let { condition ->
                    Condition(
                        field = condition.field,
                        operator = condition.operator,
                        value = condition.value,
                        keyType = condition.keyType,
                        key = condition.key
                    )
                },
                trueFlow = flowRequest.trueFlow?.map { mapFlowRequestToFlowDocument(it) },
                falseFlow = flowRequest.falseFlow?.map { mapFlowRequestToFlowDocument(it) }
            )
        }
    }
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

data class FlowDocument(
    val typeFlow: TypeFlowEnum,
    val providerName: String? = null,
    val condition: Condition? = null,
    val trueFlow: List<FlowDocument>? = null,
    val falseFlow: List<FlowDocument>? = null,
)
