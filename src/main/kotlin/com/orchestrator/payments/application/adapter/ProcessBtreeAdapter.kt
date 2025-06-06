package com.orchestrator.payments.application.adapter

import com.orchestrator.payments.adapters.inbound.rest.v2.request.ProcessBtreeRequest
import com.orchestrator.payments.adapters.inbound.rest.v2.response.FlowResponse
import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionBtreeDocument
import com.orchestrator.payments.adapters.outbound.persistence.model.FlowDocument
import com.orchestrator.payments.application.port.ProcessBtreePort
import com.orchestrator.payments.domain.FieldEnum
import com.orchestrator.payments.domain.OperatorEnum
import com.orchestrator.payments.domain.TypeFlowEnum
import org.springframework.stereotype.Service

@Service
class ProcessBtreeAdapter: ProcessBtreePort {

    override fun execute(
        decisionBtreeDocument: DecisionBtreeDocument,
        decisionBtree: ProcessBtreeRequest
    ): List<String> {
        val providers = mutableListOf<String>()

        decisionBtreeDocument.flow.forEach { flowDocument ->
            processFlow(flowDocument, decisionBtree, providers)
        }

        return providers
    }

    private fun processFlow(
        flow: FlowDocument,
        decisionData: ProcessBtreeRequest,
        providers: MutableList<String>
    ) {
        when (flow.typeFlow) {
            TypeFlowEnum.PROVIDER -> {
                flow.providerName?.let { providers.add(it) }
            }

            TypeFlowEnum.CONDITION -> {
                val condition = flow.condition ?: return

                val conditionResult = when (condition.field) {
                    FieldEnum.AMOUNT -> {
                        validateNumericCondition(
                            decisionData.amount.toString(),
                            condition.operator,
                            condition.value
                        )
                    }
                    FieldEnum.INSTALLMENT -> {
                        validateNumericCondition(
                            decisionData.installments.toString(),
                            condition.operator,
                            condition.value
                        )
                    }
                    FieldEnum.BRAND -> {
                        validateStringCondition(
                            decisionData.brand,
                            condition.operator,
                            condition.value
                        )
                    }
                }

                if (conditionResult) {
                    flow.trueFlow?.forEach { processFlow(it, decisionData, providers) }
                } else {
                    flow.falseFlow?.forEach { processFlow(it, decisionData, providers) }
                }
            }
        }
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
        }
    }

    private fun validateStringCondition(actualValue: String, operator: OperatorEnum, expectedValue: String): Boolean {
        return when (operator) {
            OperatorEnum.EQUALS -> actualValue == expectedValue
            OperatorEnum.NOT_EQUALS -> actualValue != expectedValue
            else -> false
        }
    }
}