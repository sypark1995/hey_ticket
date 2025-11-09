package com.sypark.data

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)

annotation class Dispatcher(val appDispatchers: AppDispatchers)

enum class AppDispatchers {
    IO
}