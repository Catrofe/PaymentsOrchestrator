package com.orchestrator.payments.adapters.inbound.rest.v3.request.validators

import com.orchestrator.payments.adapters.inbound.rest.v3.request.DecisionRequest
import com.orchestrator.payments.adapters.inbound.rest.v3.request.validators.annotation.ValidDecisionRequest
import com.orchestrator.payments.domain.DecisionType
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class DecisionRequestValidator: ConstraintValidator<ValidDecisionRequest, DecisionRequest>{

    override fun isValid(
        value: DecisionRequest?,
        context: ConstraintValidatorContext?
    ): Boolean {
        if (value == null) {
            context?.disableDefaultConstraintViolation()
            context?.buildConstraintViolationWithTemplate("DecisionRequest cannot be null")
                ?.addConstraintViolation()
            return false
        }

        return when {
            value.flow.isNullOrEmpty() && value.rules.isNullOrEmpty() -> {
                context?.disableDefaultConstraintViolation()
                context?.buildConstraintViolationWithTemplate("Either rules or flow must be provided")
                false
            }

            value.decisionType == DecisionType.RULES && value.rules.isNullOrEmpty() -> {
                context?.disableDefaultConstraintViolation()
                context?.buildConstraintViolationWithTemplate("Rules must be provided for RULES decision type")
                    ?.addPropertyNode("rules")
                    ?.addConstraintViolation()
                false
            }
            value.decisionType == DecisionType.BTREE && value.flow.isNullOrEmpty() -> {
                context?.disableDefaultConstraintViolation()
                context?.buildConstraintViolationWithTemplate("Flow must be provided for BTREE decision type")
                    ?.addPropertyNode("flow")
                    ?.addConstraintViolation()
                false
            }
            else -> true
        }
    }

}