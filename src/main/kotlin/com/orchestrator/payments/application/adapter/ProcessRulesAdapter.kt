package com.orchestrator.payments.application.adapter

import com.orchestrator.payments.adapters.inbound.rest.v1.request.ProcessRulesRequest
import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionRuleDocument
import com.orchestrator.payments.application.port.ProcessRulesPort
import com.orchestrator.payments.domain.FieldEnum
import com.orchestrator.payments.domain.OperatorEnum
import org.springframework.stereotype.Service

@Service
class ProcessRulesAdapter: ProcessRulesPort {

    override fun execute(
        transactionData: ProcessRulesRequest,
        decisionRules: DecisionRuleDocument
    ): List<String> {
        for (rule in decisionRules.rules) {
            val allConditionsMet = rule.conditions.all { condition ->
                when (condition.field) {
                    FieldEnum.AMOUNT -> {
                        validateNumericCondition(transactionData.amount.toString(), condition.operator, condition.value)
                    }

                    FieldEnum.INSTALLMENT -> {
                        validateNumericCondition(
                            transactionData.installments.toString(),
                            condition.operator,
                            condition.value
                        )
                    }

                    FieldEnum.BRAND -> {
                        validateStringCondition(transactionData.brand, condition.operator, condition.value)
                    }

                    FieldEnum.METADATA -> {
                    false
                    }
                }
            }

            if (allConditionsMet) {
                return rule.providers
            }
        }

        return emptyList()
    }

    private fun validateNumericCondition(actualValue: String, operator: OperatorEnum, expectedValue: String): Boolean {
        val actual = actualValue.toDoubleOrNull() ?: return false
        val expected = expectedValue.toDoubleOrNull() ?: return false

        return when (operator) {
            OperatorEnum.EQUALS -> actual == expected
            OperatorEnum.NOT_EQUALS -> actual != expected
            OperatorEnum.GREATER_THAN -> actual > expected
            OperatorEnum.LESS_THAN -> actual < expected
            OperatorEnum.GREATER_THAN_OR_EQUAL_TO -> actual >= expected
            OperatorEnum.LESS_THAN_OR_EQUAL_TO -> actual <= expected
            OperatorEnum.NOT_IN -> false
            OperatorEnum.IN -> false
        }
    }

    private fun validateStringCondition(actualValue: String, operator: OperatorEnum, expectedValue: String): Boolean {
        return when (operator) {
            OperatorEnum.EQUALS -> actualValue == expectedValue
            OperatorEnum.NOT_EQUALS -> actualValue != expectedValue
            OperatorEnum.NOT_IN -> !expectedValue.replace(" ", "").split(",").contains(actualValue)
            OperatorEnum.IN -> expectedValue.replace(" ", "").split(",").contains(actualValue)
            else -> false
        }
    }
}