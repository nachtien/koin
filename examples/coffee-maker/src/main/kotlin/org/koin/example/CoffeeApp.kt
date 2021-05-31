package org.koin.example

import defaultModule
import org.koin.api.MyApiModule
import org.koin.core.annotations.scanAnnotations
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.time.measureDuration
import org.koin.ui.UIModule

class CoffeeApp : KoinComponent {
    val maker: CoffeeMaker by inject()
}

fun main() {
    startKoin {
        printLogger(Level.DEBUG)
//        modules(listOf(coffeeAppModule))
        scanAnnotations(
            scanPackage = "org.koin.example"
        )
        modules(defaultModule)
//        scanAnnotations(
//            modules = listOf(MyApiModule::class,UIModule::class)
//        )
    }

    val coffeeShop = CoffeeApp()
    measureDuration("Got Coffee") {
        coffeeShop.maker.brew()
    }
}

fun measureDuration(msg: String, code: () -> Unit): Double {
    val duration = measureDuration(code)
    println("$msg in $duration ms")
    return duration
}