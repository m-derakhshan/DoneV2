package m.derakhshan.done.feature_authentication.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.done.feature_authentication.data.repository.AuthenticationRepositoryImplTest
import m.derakhshan.done.feature_authentication.domain.repository.AuthenticationRepository
import m.derakhshan.done.feature_authentication.domain.use_case.AuthenticationUseCase
import m.derakhshan.done.feature_authentication.domain.use_case.LoginUseCase
import m.derakhshan.done.feature_authentication.domain.use_case.ResetPassword
import m.derakhshan.done.feature_authentication.domain.use_case.SignUpUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModuleTest {


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
    fun provideAuthenticationRepository(): AuthenticationRepository {
        return AuthenticationRepositoryImplTest()
    }

}