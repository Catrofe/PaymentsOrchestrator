package com.orchestrator.payments.application.adapter

import com.orchestrator.payments.adapters.inbound.rest.v3.request.ProcessRequest
import com.orchestrator.payments.adapters.outbound.persistence.repository.DecisionRepository
import com.orchestrator.payments.application.port.DecisionProcessorPort
import com.orchestrator.payments.application.port.ProcessOrquestrationPort
import com.orchestrator.payments.domain.DecisionType
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProcessOrquestrationAdapter(
    private val repository: DecisionRepository
): ProcessOrquestrationPort {

    override fun execute(
        merchantCode: String,
        transactionData: ProcessRequest
    ) =
        repository.findByMerchantCode(merchantCode)?.let { decisionDocument ->
            val strategy = createStrategy(decisionDocument.decisionType)
            strategy.execute(decisionDocument, transactionData)
        } ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Decision not found for merchant code: $merchantCode"
        )

    private fun createStrategy(
        decisionType: DecisionType
    ): DecisionProcessorPort {
        return when (decisionType) {
            DecisionType.BTREE -> DecisionProcessorBtree()
            DecisionType.RULES -> DecisionProcessorRules()
        }
    }
}