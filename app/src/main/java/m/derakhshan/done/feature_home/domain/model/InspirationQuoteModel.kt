package m.derakhshan.done.feature_home.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class InspirationQuoteModel(
    @PrimaryKey
    val id: Int,
    val quote: String,
    val author: String
)
