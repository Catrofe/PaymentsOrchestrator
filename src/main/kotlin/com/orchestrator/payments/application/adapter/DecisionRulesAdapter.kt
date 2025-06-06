package com.orchestrator.payments.application.adapter

import com.orchestrator.payments.adapters.inbound.rest.v1.request.DecisionRulesRequest
import com.orchestrator.payments.adapters.inbound.rest.v1.request.ProcessRulesRequest
import com.orchestrator.payments.adapters.inbound.rest.v1.response.ConditionResponse
import com.orchestrator.payments.adapters.inbound.rest.v1.response.DecisionRulesResponse
import com.orchestrator.payments.adapters.inbound.rest.v1.response.RulesResponse
import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionRuleDocument
import com.orchestrator.payments.adapters.outbound.persistence.repository.DecisionRuleRepository
import com.orchestrator.payments.application.port.DecisionRulesPort
import com.orchestrator.payments.application.port.ProcessRulesPort
import org.springframework.stereotype.Service

@Service
class DecisionRulesAdapter(
    private val repository: DecisionRuleRepository,
    private val processRulesUsecase: ProcessRulesPort
): DecisionRulesPort {

    override fun createDecisionRule(decisionRules: DecisionRulesRequest): DecisionRulesResponse =
        repository.save(DecisionRuleDocument(decisionRules)
            ).let { convertEntityToResponse(it) }


    override fun getDecisionRules(): List<DecisionRulesResponse> {
        return repository.findAll().map { document ->
            convertEntityToResponse(document)
        }
    }

    override fun getDecisionRuleByMerchantCode(merchantCode: String): DecisionRulesResponse {
        return repository.findByMerchantCode(merchantCode)
            ?.let { convertEntityToResponse(it) }
            ?: throw NoSuchElementException("No decision rule found for merchant code: $merchantCode")
    }

    override fun processDecisionRule(
        merchantCode: String, decisionRules: ProcessRulesRequest
    ): List<String>
    = repository.findByMerchantCode(merchantCode)
        ?.let { processRulesUsecase.execute(decisionRules, it) }
        ?: throw NoSuchElementException("No decision rule found for merchant code: $merchantCode")

    private fun convertEntityToResponse(document: DecisionRuleDocument): DecisionRulesResponse {
        return DecisionRulesResponse(
            merchantCode = document.merchantCode,
            rules = document.rules.map { rule ->
                RulesResponse(
                    id = rule.id,
                    providers = rule.providers,
                    conditions = rule.conditions.map { condition ->
                        ConditionResponse(
                            field = condition.field,
                            operator = condition.operator,
                            value = condition.value
                        )
                    }
                )
            },
            name = document.name,
            description = document.description
        )
    }
}
