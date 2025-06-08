package com.orchestrator.payments.adapters.inbound.rest.v3.request.validators.annotation

import com.orchestrator.payments.adapters.inbound.rest.v3.request.validators.DecisionRequestValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DecisionRequestValidator::class])
annotation class  ValidDecisionRequest(
    val message: String = "Request inválido",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)