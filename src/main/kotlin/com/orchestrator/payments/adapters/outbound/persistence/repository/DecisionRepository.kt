package com.orchestrator.payments.adapters.outbound.persistence.repository

import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface DecisionRepository: MongoRepository<DecisionDocument, String> {
    fun findByMerchantCode(merchantCode: String): DecisionDocument?
}