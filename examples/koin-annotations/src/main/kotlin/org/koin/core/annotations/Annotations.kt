package org.koin.core.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS,AnnotationTarget.FUNCTION)
annotation class Single(val binds: Array<KClass<*>> = [], val createdAtStart: Boolean = false)
@Target(AnnotationTarget.CLASS,AnnotationTarget.FUNCTION)
annotation class Factory(val binds: Array<KClass<*>> = [])
@Target(AnnotationTarget.CLASS,AnnotationTarget.FUNCTION)
annotation class Qualifier(val value: String)
@Target(AnnotationTarget.CLASS)
annotation class Scope(val value: String = "")
@Target(AnnotationTarget.CLASS,AnnotationTarget.FUNCTION)
annotation class Scoped(val value: KClass<*>, val binds: Array<KClass<*>> = [])


@Target(AnnotationTarget.CLASS)
annotation class Module()
@Target(AnnotationTarget.CLASS)
annotation class ComponentScan(val value: String)