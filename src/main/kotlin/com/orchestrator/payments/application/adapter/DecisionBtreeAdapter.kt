package com.orchestrator.payments.application.adapter

import com.orchestrator.payments.adapters.inbound.rest.v2.request.DecisionBtreeRequest
import com.orchestrator.payments.adapters.inbound.rest.v2.request.ProcessBtreeRequest
import com.orchestrator.payments.adapters.inbound.rest.v2.response.ConditionResponse
import com.orchestrator.payments.adapters.inbound.rest.v2.response.DecisionBtreeResponse
import com.orchestrator.payments.adapters.inbound.rest.v2.response.FlowResponse
import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionBtreeDocument
import com.orchestrator.payments.adapters.outbound.persistence.model.FlowDocument
import com.orchestrator.payments.adapters.outbound.persistence.repository.DecisionBtreeRepository
import com.orchestrator.payments.application.port.DecisionBtreePort
import com.orchestrator.payments.application.port.ProcessBtreePort
import org.springframework.stereotype.Service

@Service
class DecisionBtreeAdapter(
    private val repository: DecisionBtreeRepository,
    private val processBtree: ProcessBtreePort
): DecisionBtreePort {
    override fun createDecisionBtree(decisionBtree: DecisionBtreeRequest): DecisionBtreeResponse =
        repository.save(DecisionBtreeDocument(decisionBtree)).toResponse()

    override fun getDecisionBtrees(): List<DecisionBtreeResponse> {
        return repository.findAll().map { document ->
            document.toResponse()
        }
    }

    override fun getDecisionBtreeByMerchantCode(merchantCode: String): DecisionBtreeResponse {
        return repository.findByMerchantCode(merchantCode)
            ?.toResponse()
            ?: throw NoSuchElementException("No decision btree found for merchant code: $merchantCode")
    }

    override fun processDecisionBtree(
        merchantCode: String,
        decisionBtree: ProcessBtreeRequest
    ): List<String> =
        repository.findByMerchantCode(merchantCode)
            ?.let { processBtree.execute(it, decisionBtree) }
            ?: throw NoSuchElementException("No decision btree found for merchant code: $merchantCode")

    private fun DecisionBtreeDocument.toResponse(): DecisionBtreeResponse {
        return DecisionBtreeResponse(
            merchantCode = this.merchantCode,
            name = this.name,
            description = this.description,
            flow = this.flow.map { it.toResponse() }
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
                    value = condition.value
                )
            },
            trueFlow = this.trueFlow?.map { it.toResponse() },
            falseFlow = this.falseFlow?.map { it.toResponse() }
        )
    }
}