package com.model.annotation

import kotlin.annotation.Retention
import javax.validation.Constraint
import kotlin.reflect.KClass
import javax.validation.Payload

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [XSecondsPastOrPresentImpl::class])
annotation class XSecondsPastOrPresent(
    val seconds: Int = 60,
    val message: String = "Message",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)