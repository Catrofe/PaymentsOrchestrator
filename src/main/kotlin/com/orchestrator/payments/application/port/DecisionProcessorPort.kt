package com.orchestrator.payments.application.port

import com.orchestrator.payments.adapters.inbound.rest.v3.request.ProcessRequest
import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionDocument
import com.orchestrator.payments.application.adapter.evaluators.IntegerFieldEvaluator
import com.orchestrator.payments.application.adapter.evaluators.MetadataFieldEvaluator
import com.orchestrator.payments.application.adapter.evaluators.StringFieldEvaluator
import com.orchestrator.payments.domain.FieldEnum

interface DecisionProcessorPort {
    fun execute(decision: DecisionDocument, request: ProcessRequest): List<String>

    fun FieldEnum.evaluate(): FieldConditionEvaluatorPort {
        return when (this) {
            FieldEnum.AMOUNT -> IntegerFieldEvaluator()
            FieldEnum.INSTALLMENT -> IntegerFieldEvaluator()
            FieldEnum.BRAND -> StringFieldEvaluator()
            FieldEnum.METADATA -> MetadataFieldEvaluator()
        }
    }
}