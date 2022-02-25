package m.derakhshan.done.feature_authentication.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.done.core.data.data_source.NoteDatabase
import m.derakhshan.done.feature_authentication.data.repository.AuthenticationRepositoryImpl
import m.derakhshan.done.feature_authentication.domain.repository.AuthenticationRepository
import m.derakhshan.done.feature_authentication.domain.use_case.AuthenticationUseCase
import m.derakhshan.done.feature_authentication.domain.use_case.LoginUseCase
import m.derakhshan.done.feature_authentication.domain.use_case.ResetPassword
import m.derakhshan.done.feature_authentication.domain.use_case.SignUpUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthenticationModule {


    @Singleton
    @Provides
    fun provideAuthenticationFirebaseInstant(): FirebaseAuth {
        return Firebase.auth
    }

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Singleton
    @Provides
    fun provideAuthenticationUseCase(repository: AuthenticationRepository): AuthenticationUseCase {
        return AuthenticationUseCase(
            loginUseCase = LoginUseCase(repository = repository),
            signUpUseCase = SignUpUseCase(repository = repository),
            resetPassword = ResetPassword(repository = repository)
        )
    }

    @Singleton
    @Provides
    fun provideAuthenticationRepository(
        authentication: FirebaseAuth,
        storage: FirebaseFirestore,
        database: NoteDatabase
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(
            authentication = authentication,
            storage = storage,
            databaseDao = database.userDao
        )
    }
}