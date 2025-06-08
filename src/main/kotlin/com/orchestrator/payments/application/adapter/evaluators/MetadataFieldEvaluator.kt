package com.orchestrator.payments.application.adapter.evaluators

import com.orchestrator.payments.adapters.outbound.persistence.model.Condition
import com.orchestrator.payments.application.port.FieldConditionEvaluatorPort
import com.orchestrator.payments.domain.EvaluationContext
import com.orchestrator.payments.domain.MetaDataKeyType
import com.orchestrator.payments.domain.OperatorEnum
import kotlin.collections.get

class MetadataFieldEvaluator: FieldConditionEvaluatorPort {

    override fun execute(
        condition: Condition,
        context: EvaluationContext
    ): Boolean {
        if (context.metaData.isNullOrEmpty()) return condition.operator == OperatorEnum.NOT_EQUALS

        val metaData = context.metaData.associate { it.key to it.value }
        val value = metaData[condition.key] ?: return false

        val fieldValue = transformMetaDataValue(value, condition.keyType!!)
        val expectedValue = transformMetaDataValue(condition.value, condition.keyType)

        return when (condition.operator) {
            OperatorEnum.EQUALS -> fieldValue == expectedValue
            OperatorEnum.NOT_EQUALS -> fieldValue != expectedValue
            else -> false
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