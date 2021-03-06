package m.derakhshan.done.feature_authentication.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserModel(
    @PrimaryKey
    val uid: String,
    val email: String,
    val name: String,
    val profileImage: String = "https://www.constrack.ng/uploads/icon-user-default.png",
)
