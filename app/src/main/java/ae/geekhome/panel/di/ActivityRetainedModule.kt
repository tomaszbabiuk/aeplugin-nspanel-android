package ae.geekhome.panel.di

import ae.geekhome.panel.coap.CoapService
import ae.geekhome.panel.coap.impl.DashboardResource
import ae.geekhome.panel.coap.impl.CaliforniumCoapService
import ae.geekhome.panel.coap.impl.ManifestResource
import ae.geekhome.panel.navigation.MyRouteNavigator
import ae.geekhome.panel.navigation.RouteNavigator
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class ActivityRetainedModule {

    @Provides
    @ActivityRetainedScoped
    fun provideCoapService(
        @ApplicationContext context: Context,
        dashboardResource: DashboardResource,
        manifestResource: ManifestResource
    ): CoapService {
        return CaliforniumCoapService(context, dashboardResource, manifestResource)
    }
    @Provides
    @ActivityRetainedScoped
    fun provideRouteNavigator(impl: MyRouteNavigator): RouteNavigator {
        return impl
    }
}
