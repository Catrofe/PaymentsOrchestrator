package com.orchestrator.payments.adapters.inbound.rest.v1

import com.orchestrator.payments.adapters.inbound.rest.v1.request.DecisionRulesRequest
import com.orchestrator.payments.adapters.inbound.rest.v1.request.ProcessRulesRequest
import com.orchestrator.payments.application.port.DecisionRulesPort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/decision-rules")
class DecisionRulesController(
    private val decisionRulesService: DecisionRulesPort
) {

    @PostMapping()
    fun createDecisionRule(
        @RequestBody decisionRules: DecisionRulesRequest
    ) =
        decisionRulesService.createDecisionRule(decisionRules = decisionRules)


    @GetMapping()
    fun getDecisionRules() = decisionRulesService.getDecisionRules()

    @GetMapping("/{merchantCode}")
    fun getDecisionRuleByMerchantCode(@PathVariable merchantCode: String) =
        decisionRulesService.getDecisionRuleByMerchantCode(merchantCode = merchantCode)

    @PostMapping("/{merchantCode}/decode")
    fun processDecisionRule(
        @PathVariable merchantCode: String,
        @RequestBody decisionRules: ProcessRulesRequest
    ) =
        decisionRulesService.processDecisionRule(merchantCode = merchantCode, decisionRules = decisionRules)
}
