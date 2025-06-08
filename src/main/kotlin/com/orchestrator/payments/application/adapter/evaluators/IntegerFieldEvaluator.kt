package com.orchestrator.payments.application.adapter.evaluators

import com.orchestrator.payments.adapters.outbound.persistence.model.Condition
import com.orchestrator.payments.application.port.FieldConditionEvaluatorPort
import com.orchestrator.payments.domain.EvaluationContext
import com.orchestrator.payments.domain.FieldEnum
import com.orchestrator.payments.domain.OperatorEnum

class IntegerFieldEvaluator: FieldConditionEvaluatorPort {

    override fun execute(
        condition: Condition,
        context: EvaluationContext
    ): Boolean {
        val fieldValue = when (condition.field) {
            FieldEnum.AMOUNT -> context.amount
            FieldEnum.INSTALLMENT -> context.installments
            else -> null
        } ?: return false
        val conditionValue = condition.value.toIntOrNull() ?: return false

        return when (condition.operator) {
            OperatorEnum.EQUALS -> fieldValue == conditionValue
            OperatorEnum.NOT_EQUALS -> fieldValue != conditionValue
            OperatorEnum.GREATER_THAN ->  fieldValue > conditionValue
            OperatorEnum.GREATER_THAN_OR_EQUAL_TO ->  fieldValue >= conditionValue
            OperatorEnum.LESS_THAN -> fieldValue < conditionValue
            OperatorEnum.LESS_THAN_OR_EQUAL_TO -> fieldValue <= conditionValue
            else -> false
        }
    }
}