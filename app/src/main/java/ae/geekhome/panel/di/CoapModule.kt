package ae.geekhome.panel.di

import ae.geekhome.panel.coap.CaliforniumCoapService
import ae.geekhome.panel.coap.CoapService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class CoapModule {

    @Binds
    abstract fun bindCoapService(
        impl: CaliforniumCoapService
    ): CoapService
}