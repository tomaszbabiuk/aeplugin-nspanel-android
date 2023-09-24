package ae.geekhome.panel.di

import ae.geekhome.panel.coap.CoapService
import ae.geekhome.panel.coap.impl.ActiveSceneResource
import ae.geekhome.panel.coap.impl.AutomateEverythingResource
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
  fun provideCoapService(
      activeSceneResource: ActiveSceneResource,
      automateEverythingResource: AutomateEverythingResource
  ): CoapService {
    return CaliforniumCoapService(activeSceneResource, automateEverythingResource)
  }
  @Provides
  fun provideRouteNavigator(impl: MyRouteNavigator): RouteNavigator {
    return impl
  }
}
