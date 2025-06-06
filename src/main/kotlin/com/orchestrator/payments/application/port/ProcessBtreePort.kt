package com.orchestrator.payments.application.port

import com.orchestrator.payments.adapters.inbound.rest.v2.request.ProcessBtreeRequest
import com.orchestrator.payments.adapters.outbound.persistence.model.DecisionBtreeDocument

interface ProcessBtreePort {
    fun execute(decisionBtreeDocument: DecisionBtreeDocument, decisionBtree: ProcessBtreeRequest): List<String>
}