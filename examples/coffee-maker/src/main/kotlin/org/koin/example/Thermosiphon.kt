package org.koin.example

import org.koin.core.annotations.Single

@Single
class Thermosiphon(private val heater: Heater) : Pump {
    override fun pump() {
        if (heater.isHot()) {
            println("=> => pumping => =>")
        }
    }
}