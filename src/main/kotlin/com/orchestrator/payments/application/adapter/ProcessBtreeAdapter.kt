package com.orchestrator.payments.application.adapter

import com.orchestrator.payments.adapters.inbound.rest.v2.request.ProcessBtreeRequest
import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionBtreeDocument
import com.orchestrator.payments.adapters.outbound.persistence.model.FlowDocument
import com.orchestrator.payments.application.port.ProcessBtreePort
import com.orchestrator.payments.domain.FieldEnum
import com.orchestrator.payments.domain.MetaDataKeyType
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
        request: ProcessBtreeRequest,
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
                            request.amount,
                            condition.operator,
                            condition.value
                        )
                    }

                    FieldEnum.INSTALLMENT -> {
                        validateNumericCondition(
                            request.installments,
                            condition.operator,
                            condition.value
                        )
                    }

                    FieldEnum.BRAND -> {
                        validateStringCondition(
                            request.brand,
                            condition.operator,
                            condition.value
                        )
                    }

                    FieldEnum.METADATA -> {
                        validateMetaDataCondition(
                            request.metaData.associate { it.key to it.value },
                            condition.operator,
                            condition.value,
                            condition.keyType,
                            condition.key
                        )
                    }
                }

                if (conditionResult) {
                    flow.trueFlow?.forEach { processFlow(it, request, providers) }
                } else {
                    flow.falseFlow?.forEach { processFlow(it, request, providers) }
                }
            }
        }
    }

    private fun validateNumericCondition(actualValue: Int, operator: OperatorEnum, expectedValue: String): Boolean {
        val expected = expectedValue.toIntOrNull() ?: return false

        return when (operator) {
            OperatorEnum.EQUALS -> actualValue == expected
            OperatorEnum.NOT_EQUALS -> actualValue != expected
            OperatorEnum.GREATER_THAN -> actualValue > expected
            OperatorEnum.LESS_THAN -> actualValue < expected
            OperatorEnum.GREATER_THAN_OR_EQUAL_TO -> actualValue >= expected
            OperatorEnum.LESS_THAN_OR_EQUAL_TO -> actualValue <= expected
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

    private fun validateMetaDataCondition(
        metaData: Map<String, String>,
        operator: OperatorEnum,
        expectedValue: String,
        expectedKeyType: MetaDataKeyType? = null,
        expectedKey: String? = null
    ): Boolean {
        if (expectedKeyType == null || expectedKey == null) {
            return false
        }

        val value = metaData[expectedKey] ?: return false

        try {
            val actualValue = transformMetaDataValue(value, expectedKeyType)
            val expected = transformMetaDataValue(expectedValue, expectedKeyType)

            return when (operator) {
                OperatorEnum.EQUALS -> actualValue == expected
                OperatorEnum.NOT_EQUALS -> actualValue != expected
                else -> false
            }
        } catch (e: RuntimeException) {
            return false
        }
    }

    private fun transformMetaDataValue(
        value: String,
        keyType: MetaDataKeyType
    ): Any? {
        return when (keyType) {
            MetaDataKeyType.BOOLEAN -> value.toBoolean()
            MetaDataKeyType.INTEGER -> value.toIntOrNull()
            MetaDataKeyType.STRING -> value
        }
    }
}