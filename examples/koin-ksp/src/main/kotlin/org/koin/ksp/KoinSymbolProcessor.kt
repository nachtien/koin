package org.koin.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import org.koin.core.annotations.Single
import java.io.OutputStream


class KoinSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.error("Koin generation ...")
        val symbols = resolver.getSymbolsWithAnnotation(Single::class.qualifiedName!!)
        val classes = symbols.filterIsInstance<KSClassDeclaration>().toList()
        val deps = classes.map {
            it.containingFile!!
        }
        logger.error("Koin generate file ...")
        codeGenerator.createNewFile(
            dependencies = Dependencies(true, *deps.toTypedArray()),
            packageName = "org.koin.ksp",
            fileName = "GeneratedModule",
            extensionName = "kt"
        ).use { file ->
            file.appendText(
                "import org.koin.dsl.bind\n" +
                "import org.koin.dsl.module\n" +
                "import org.koin.dsl.single\n"
            )
            classes.forEach { clazz ->
                logger.error("Koin class $clazz")
                val packageName = clazz.packageName.asString()

                file.appendText("import $packageName.$clazz\n")
            }
            file.appendText("val defaultModule = module {\n")
            classes.forEach { clazz ->
                logger.error("gen class $clazz")
                val packageName = clazz.packageName.asString()
                file.appendText("single <$packageName.$clazz>()\n")
            }
            file.appendText("}")

            file.close()
        }
        return emptyList()
    }

    private fun generate(classDeclaration: KSClassDeclaration) {
        val containingFile = classDeclaration.containingFile!!
        val packageName = classDeclaration.packageName.asString()
        val className = classDeclaration.simpleName.asString()
        codeGenerator.createNewFile(
            dependencies = Dependencies(true, containingFile),
            packageName = packageName,
            fileName = "${className}Klip",
            extensionName = "kt"
        ).use { file ->
            if (packageName.isNotBlank()) {
                file.appendText("package $packageName\n\n")
            }
        }
    }

    private fun OutputStream.appendText(str: String) {
        this.write(str.toByteArray())
    }
}

class KoinSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return KoinSymbolProcessor(
            environment.codeGenerator,
            environment.logger
        )
    }
}