package ae.geekhome.panel.di

import ae.geekhome.panel.coap.CoapService
import ae.geekhome.panel.coap.impl.ActiveSceneResource
import ae.geekhome.panel.coap.impl.CaliforniumCoapService
import ae.geekhome.panel.navigation.MyRouteNavigator
import ae.geekhome.panel.navigation.RouteNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {
  @Provides
  fun bindCoapService(activeSceneResource: ActiveSceneResource): CoapService {
    return CaliforniumCoapService(activeSceneResource)
  }
  @Provides
  fun bindRouteNavigator(impl: MyRouteNavigator): RouteNavigator {
    return impl
  }
}
