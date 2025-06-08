package com.orchestrator.payments.application.port

import com.orchestrator.payments.adapters.outbound.persistence.model.Condition
import com.orchestrator.payments.domain.EvaluationContext

interface FieldConditionEvaluatorPort {
    fun execute(
        condition: Condition,
        context: EvaluationContext
    ): Boolean
}