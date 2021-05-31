package org.koin.example

import org.koin.core.annotations.ComponentScan
import org.koin.core.annotations.Module
import org.koin.core.annotations.Single

//val coffeeAppModule = module {
//    single { CoffeeMaker(get(), get()) }
//    single<Pump> { Thermosiphon(get()) }
//    single<Heater> { ElectricHeater() }
//}

//@Module
//@ComponentScan("org.koin")
//class MyModule {
//
//    @Single
//    fun coffeeMaker(pump: Pump, heater: Heater) = CoffeeMaker(pump, heater)
//
//}

// val coffeeAppModule = module {
//     single<CoffeeMaker>()
//     single<Thermosiphon>() bind Pump::class
//     single<ElectricHeater>() bind Heater::class
// }