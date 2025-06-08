package com.orchestrator.payments.application.adapter.evaluators

import com.orchestrator.payments.adapters.outbound.persistence.model.Condition
import com.orchestrator.payments.application.port.FieldConditionEvaluatorPort
import com.orchestrator.payments.domain.EvaluationContext
import com.orchestrator.payments.domain.FieldEnum
import com.orchestrator.payments.domain.OperatorEnum

class StringFieldEvaluator: FieldConditionEvaluatorPort {

    override fun execute(
        condition: Condition,
        context: EvaluationContext
    ): Boolean {
        val fieldValue = when (condition.field) {
            FieldEnum.BRAND -> context.brand
            else -> null
        } ?: return false

        val conditionValue = condition.value

        return when (condition.operator) {
            OperatorEnum.EQUALS -> fieldValue == conditionValue
            OperatorEnum.NOT_EQUALS -> fieldValue != conditionValue
            OperatorEnum.IN -> conditionValue.split(",").map { it.trim() }.contains(fieldValue)
            OperatorEnum.NOT_IN -> !conditionValue.split(",").map { it.trim() }.contains(fieldValue)
            else -> false
        }
    }
}