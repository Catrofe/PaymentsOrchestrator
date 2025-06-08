package com.orchestrator.payments.adapters.inbound.rest.v3.request.validators

import com.orchestrator.payments.adapters.inbound.rest.v3.request.FlowRequest
import com.orchestrator.payments.adapters.inbound.rest.v3.request.validators.annotation.ValidFlowRequest
import com.orchestrator.payments.domain.TypeFlowEnum
import jakarta.validation.ConstraintValidator

class FlowRequestValidator: ConstraintValidator<ValidFlowRequest, FlowRequest> {
    override fun isValid(value: FlowRequest?, context: jakarta.validation.ConstraintValidatorContext): Boolean {
        if (value == null) return true

        return when {
            value.typeFlow == TypeFlowEnum.CONDITION && value.condition == null -> {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate("Condition must be provided for CONDITION type flow")
                    .addPropertyNode("condition")
                    .addConstraintViolation()
                false
            }
            value.typeFlow == TypeFlowEnum.CONDITION && value.trueFlow == null -> {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate("True flow must be provided for CONDITION type flow")
                    .addPropertyNode("trueFlow")
                    .addConstraintViolation()
                false
            }
            value.typeFlow == TypeFlowEnum.CONDITION && value.falseFlow == null -> {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate("False flow must be provided for CONDITION type flow")
                    .addPropertyNode("falseFlow")
                    .addConstraintViolation()
                false
            }
            value.typeFlow == TypeFlowEnum.PROVIDER && value.providerName.isNullOrEmpty() -> {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate("Provider name must be provided for PROVIDER type flow")
                    .addPropertyNode("providerName")
                    .addConstraintViolation()
                false
            }
            else -> true
        }
    }
}