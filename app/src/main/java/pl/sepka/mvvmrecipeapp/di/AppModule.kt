package pl.sepka.mvvmrecipeapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.sepka.mvvmrecipeapp.BaseApplication
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    // fun someRandomString(): String {
    //    return "some random string"
    // }
    @Singleton
    @Provides
    fun someRandomString() = "some random string xdxdxdx"
}
