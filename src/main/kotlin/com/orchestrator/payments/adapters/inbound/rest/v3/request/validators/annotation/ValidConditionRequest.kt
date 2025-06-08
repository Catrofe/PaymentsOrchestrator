package com.orchestrator.payments.adapters.inbound.rest.v3.request.validators.annotation

import com.orchestrator.payments.adapters.inbound.rest.v3.request.validators.ConditionRequestValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ConditionRequestValidator::class])
annotation class ValidConditionRequest(
    val message: String = "Request inválido para METADATA",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
