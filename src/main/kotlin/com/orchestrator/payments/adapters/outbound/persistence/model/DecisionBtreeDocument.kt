package com.orchestrator.payments.adapters.outbound.persistence.model

import com.orchestrator.payments.adapters.inbound.rest.v2.request.DecisionBtreeRequest
import com.orchestrator.payments.adapters.inbound.rest.v2.request.FlowRequest
import com.orchestrator.payments.domain.TypeFlowEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID


@Document(collection = "decision_btree")
data class DecisionBtreeDocument(
    @Id
    val merchantCode: String,
    val name: String,
    val description: String? = null,
    val flow: List<FlowDocument>,
) {
    constructor(decisionBtree: DecisionBtreeRequest) : this(
        merchantCode = UUID.randomUUID().toString(),
        name = decisionBtree.name,
        description = decisionBtree.description,
        flow = decisionBtree.flow.map { mapFlowRequestToFlowDocument(it) }
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

data class FlowDocument(
    val typeFlow: TypeFlowEnum,
    val providerName: String? = null,
    val condition: Condition? = null,
    val trueFlow: List<FlowDocument>? = null,
    val falseFlow: List<FlowDocument>? = null,
)
