package com.orchestrator.payments.adapters.outbound.persistence.repository

import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionRuleDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface DecisionRuleRepository: MongoRepository<DecisionRuleDocument, String> {
    fun findByMerchantCode(merchantCode: String): DecisionRuleDocument?
}