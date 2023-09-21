package ae.geekhome.panel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideBinaryFormat() : BinaryFormat {
        return Cbor
    }
}