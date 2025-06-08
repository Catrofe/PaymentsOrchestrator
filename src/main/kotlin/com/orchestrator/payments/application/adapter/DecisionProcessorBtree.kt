package com.orchestrator.payments.application.adapter

import com.orchestrator.payments.adapters.inbound.rest.v3.request.ProcessRequest
import com.orchestrator.payments.adapters.outbound.persistence.model.Condition
import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionDocument
import com.orchestrator.payments.adapters.outbound.persistence.model.FlowDocument
import com.orchestrator.payments.application.port.DecisionProcessorPort
import com.orchestrator.payments.domain.EvaluationContext
import com.orchestrator.payments.domain.TypeFlowEnum

class DecisionProcessorBtree: DecisionProcessorPort {
    override fun execute(decision: DecisionDocument, request: ProcessRequest): List<String> {
        val context = EvaluationContext(request)
        val flow = decision.flow?.firstOrNull() ?: return emptyList()
        return processFlow(flow, context)
    }

    private fun processFlow(
        flow: FlowDocument,
        context: EvaluationContext
    ): List<String> {
        return when (flow.typeFlow) {
            TypeFlowEnum.PROVIDER -> flow.providerName?.let { listOf(it) } ?: emptyList()
            TypeFlowEnum.CONDITION -> {
                val condition = flow.condition ?: return emptyList()
                val nextFlows = if (validateCondition(condition, context)) flow.trueFlow else flow.falseFlow
                nextFlows?.flatMap { processFlow(it, context) } ?: emptyList()
            }
        }
    }

    private fun validateCondition(
        condition: Condition,
        context: EvaluationContext
    ): Boolean =
        condition.field.evaluate().execute(
            condition,
            context
        )

}