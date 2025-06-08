package com.orchestrator.payments.application.adapter

import com.orchestrator.payments.adapters.inbound.rest.v3.request.ProcessRequest
import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionDocument
import com.orchestrator.payments.adapters.outbound.persistence.model.Rules
import com.orchestrator.payments.application.port.DecisionProcessorPort
import com.orchestrator.payments.domain.EvaluationContext

class DecisionProcessorRules: DecisionProcessorPort {
    override fun execute(
        decision: DecisionDocument,
        request: ProcessRequest
    ): List<String> {
        val context = EvaluationContext(request)
        val rules = decision.rules ?: return emptyList()
        return processRules(rules, context)
    }

    private fun processRules(
        rules: List<Rules>,
        context: EvaluationContext
    ): List<String> {
        return rules.filter { rule ->
            rule.conditions.all { condition ->
                condition.field.evaluate().execute(condition, context)
            }
        }.flatMap { it.providers}
    }
}