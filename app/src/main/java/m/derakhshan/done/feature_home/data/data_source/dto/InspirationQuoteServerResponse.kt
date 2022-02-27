package m.derakhshan.done.feature_home.data.data_source.dto

import com.google.gson.annotations.SerializedName
import m.derakhshan.done.feature_home.domain.model.InspirationQuoteModel

data class InspirationQuoteServerResponse(
    @SerializedName("q")
    val quote: String,

    @SerializedName("a")
    val author: String,

    @SerializedName("h")
    val html: String
)

fun InspirationQuoteServerResponse.toInspirationQuoteModel(): InspirationQuoteModel {
    return InspirationQuoteModel(
        id = 1,
        quote = this.quote,
        author = this.author
    )
}
