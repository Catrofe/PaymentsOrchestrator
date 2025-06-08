package com.orchestrator.payments.application.adapter

import com.orchestrator.payments.adapters.inbound.rest.v3.request.DecisionRequest
import com.orchestrator.payments.adapters.inbound.rest.v3.response.ConditionResponse
import com.orchestrator.payments.adapters.inbound.rest.v3.response.DecisionResponse
import com.orchestrator.payments.adapters.inbound.rest.v3.response.FlowResponse
import com.orchestrator.payments.adapters.inbound.rest.v3.response.RulesResponse
import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionDocument
import com.orchestrator.payments.adapters.outbound.persistence.model.FlowDocument
import com.orchestrator.payments.adapters.outbound.persistence.model.Rules
import com.orchestrator.payments.adapters.outbound.persistence.repository.DecisionRepository
import com.orchestrator.payments.application.port.DecisionPort
import org.springframework.stereotype.Service

@Service
class DecisionAdapter(
    private val repository: DecisionRepository
): DecisionPort {
    override fun createDecision(decision: DecisionRequest): DecisionResponse =
        repository.save(DecisionDocument(decision)).toResponse()

    override fun getDecisions(): List<DecisionResponse> =
        repository.findAll().map { it.toResponse() }

    override fun getDecisionByMerchantCode(merchantCode: String): DecisionResponse? =
        repository.findByMerchantCode(merchantCode)?.toResponse()

    private fun DecisionDocument.toResponse(): DecisionResponse =
        DecisionResponse(
            merchantCode = this.merchantCode,
            name = this.name,
            description = this.description,
            decisionType = this.decisionType,
            rules = this.rules?.map { it.toResponse() },
            flow = this.flow?.map { it.toResponse() }
        )

    private fun Rules.toResponse(): RulesResponse {
        return RulesResponse(
            id = this.id,
            providers = this.providers,
            conditions = this.conditions.map { condition ->
                ConditionResponse(
                    field = condition.field,
                    operator = condition.operator,
                    value = condition.value,
                    keyType = condition.keyType,
                    key = condition.key
                )
            }
        )
    }

    private fun FlowDocument.toResponse(): FlowResponse {
        return FlowResponse(
            typeFlow = this.typeFlow,
            providerName = this.providerName,
            condition = this.condition?.let { condition ->
                ConditionResponse(
                    field = condition.field,
                    operator = condition.operator,
                    value = condition.value,
                    keyType = condition.keyType,
                    key = condition.key
                )
            },
            trueFlow = this.trueFlow?.map { it.toResponse() },
            falseFlow = this.falseFlow?.map { it.toResponse() }
        )
    }

}