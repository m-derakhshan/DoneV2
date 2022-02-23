package m.derakhshan.done.feature_authentication.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthenticationModule {


    @Singleton
    @Provides
    fun provideAuthenticationFirebaseInstant(): FirebaseAuth {
        return Firebase.auth
    }
}