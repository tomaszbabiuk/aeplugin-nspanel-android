package ae.geekhome.panel.di

import ae.geekhome.panel.coap.CoapService
import ae.geekhome.panel.coap.impl.ActiveSceneResource
import ae.geekhome.panel.coap.impl.ManifestResource
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
    manifestResource: ManifestResource
  ): CoapService {
    return CaliforniumCoapService(activeSceneResource, manifestResource)
  }
  @Provides
  fun provideRouteNavigator(impl: MyRouteNavigator): RouteNavigator {
    return impl
  }
}
