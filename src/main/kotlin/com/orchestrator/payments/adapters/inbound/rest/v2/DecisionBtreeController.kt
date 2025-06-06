package com.orchestrator.payments.adapters.inbound.rest.v2

import com.orchestrator.payments.adapters.inbound.rest.v2.request.DecisionBtreeRequest
import com.orchestrator.payments.adapters.inbound.rest.v2.request.ProcessBtreeRequest
import com.orchestrator.payments.application.port.DecisionBtreePort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/decision-btree")
class DecisionBtreeController(
    private val service: DecisionBtreePort
) {

    @PostMapping()
    fun createDecisionBtree(
        @RequestBody decisionBtree: DecisionBtreeRequest
    ) = service.createDecisionBtree(decisionBtree = decisionBtree)

    @GetMapping()
    fun getDecisionBtrees() = service.getDecisionBtrees()

    @GetMapping("/{merchantCode}")
    fun getDecisionBtreeByMerchantCode(
        @PathVariable merchantCode: String
    ) = service.getDecisionBtreeByMerchantCode(merchantCode = merchantCode)

    @PostMapping("/{merchantCode}/decode")
    fun processDecisionBtree(
        @PathVariable merchantCode: String,
        @RequestBody decisionBtree: ProcessBtreeRequest
    ) = service.processDecisionBtree(merchantCode = merchantCode, decisionBtree = decisionBtree)
}