package com.orchestrator.payments.adapters.inbound.rest.v3.request.validators

import com.orchestrator.payments.adapters.inbound.rest.v3.request.ConditionRequest
import com.orchestrator.payments.adapters.inbound.rest.v3.request.validators.annotation.ValidConditionRequest
import com.orchestrator.payments.domain.FieldEnum
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ConditionRequestValidator: ConstraintValidator<ValidConditionRequest, ConditionRequest> {
    override fun isValid(value: ConditionRequest?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return true

        return when {
            value.field == FieldEnum.METADATA && (value.key.isNullOrEmpty() || value.keyType == null) -> {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate(
                    "For METADATA field, 'key' must not be null or empty and 'keyType' must not be null."
                ).addConstraintViolation()
                false
            }

            else -> true
        }
    }
}