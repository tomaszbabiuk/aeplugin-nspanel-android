package ae.geekhome.panel.di

import ae.geekhome.panel.coap.CoapService
import ae.geekhome.panel.coap.impl.CaliforniumCoapService
import ae.geekhome.panel.navigation.MyRouteNavigator
import ae.geekhome.panel.navigation.RouteNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {
    @Binds abstract fun bindCoapService(impl: CaliforniumCoapService): CoapService
    @Binds abstract fun bindRouteNavigator(impl: MyRouteNavigator): RouteNavigator
}
