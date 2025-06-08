package com.orchestrator.payments.adapters.inbound.rest.v3

import com.orchestrator.payments.adapters.inbound.rest.v3.request.DecisionRequest
import com.orchestrator.payments.adapters.inbound.rest.v3.request.ProcessRequest
import com.orchestrator.payments.application.port.DecisionPort
import com.orchestrator.payments.application.port.ProcessOrquestrationPort
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v3/orquestration")
class ProcessOrquestration(
    private val processService: ProcessOrquestrationPort,
    private val service: DecisionPort
) {
    @PostMapping()
    fun createDecisionBtree(
        @Valid @RequestBody decisionBtree: DecisionRequest
    ) = service.createDecision(decisionBtree)

    @GetMapping()
    fun getDecisionBtrees() = service.getDecisions()

    @GetMapping("/{merchantCode}")
    fun getDecisionBtreeByMerchantCode(
        @PathVariable merchantCode: String
    ) = service.getDecisionByMerchantCode(merchantCode = merchantCode)

    @PostMapping("/{merchantCode}/decode")
    fun processDecisionRule(
        @PathVariable merchantCode: String,
        @RequestBody transactionData: ProcessRequest
    ) =
        processService.execute(merchantCode = merchantCode, transactionData = transactionData)
}