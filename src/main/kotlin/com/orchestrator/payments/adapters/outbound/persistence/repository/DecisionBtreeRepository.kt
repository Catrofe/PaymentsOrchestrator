package com.orchestrator.payments.adapters.outbound.persistence.repository

import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionBtreeDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface DecisionBtreeRepository: MongoRepository<DecisionBtreeDocument, String> {
    fun findByMerchantCode(merchantCode: String): DecisionBtreeDocument?
}