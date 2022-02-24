package m.derakhshan.done.feature_authentication.domain.model

data class UserModel(
    val token: String,
    val email: String,
    val password: String,
    val name: String
)
