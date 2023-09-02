package ae.geekhome.panel.di

import ae.geekhome.panel.coap.CaliforniumCoAPServer
import ae.geekhome.panel.coap.CoAPServer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class CoAPModule {

    @Binds
    abstract fun bindCoAPServer(
        impl: CaliforniumCoAPServer
    ): CoAPServer
}