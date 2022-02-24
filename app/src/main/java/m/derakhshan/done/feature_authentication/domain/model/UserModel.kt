package m.derakhshan.done.feature_authentication.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserModel(
    @PrimaryKey
    val uid: String,
    val email: String,
    val password: String,
    val name: String
)
