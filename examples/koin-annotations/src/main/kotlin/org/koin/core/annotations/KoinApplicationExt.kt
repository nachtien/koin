package org.koin.core.annotations

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.binds
import org.koin.dsl.single
import org.reflections.Reflections
import kotlin.reflect.KClass

fun KoinApplication.scanAnnotations(scanPackage: String = "", modules: List<KClass<*>> = emptyList()) {
    val allPackages: List<String> = modules.mapNotNull { foundModule ->
        val clazz = foundModule.java
        val isAModule = clazz.getAnnotation(org.koin.core.annotations.Module::class.java) != null
        if (isAModule) {
            clazz.getAnnotation(org.koin.core.annotations.ComponentScan::class.java).value
        } else null
    }

    val reflections = Reflections(scanPackage + allPackages)
    scanForClasses(reflections, Module())
}

private fun KoinApplication.scanForClasses(
    reflections: Reflections,
    defaultModule: Module
) {
    reflections.getTypesAnnotatedWith(Single::class.java).forEach { clazz ->
        val single = clazz.annotations.first { it is Single } as Single
        val qualifier =
            (clazz.annotations.firstOrNull { it is Qualifier } as Qualifier?)?.let { StringQualifier(it.value) }
        declareSingle(clazz, qualifier, single.binds, defaultModule)
    }
    modules(defaultModule)
}

private fun declareSingle(
    clazz: Class<*>,
    qualifier: StringQualifier?,
    binds: Array<KClass<*>>,
    module: Module
) {
    val primaryType = clazz.kotlin
    val secondaryTypes = if (binds.isNotEmpty()) binds else clazz.interfaces.map { it.kotlin }.toTypedArray()
    val p = module.single(primaryType, qualifier)
    p.binds(secondaryTypes)
}