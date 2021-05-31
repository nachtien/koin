package org.koin.ui

import org.koin.core.annotations.ComponentScan
import org.koin.core.annotations.Module
import org.koin.core.annotations.Single

@Single
class MyUISingle

@Module
@ComponentScan("org.koin.ui")
class UIModule