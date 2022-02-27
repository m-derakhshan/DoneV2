package m.derakhshan.done.feature_home.data.data_source

import m.derakhshan.done.feature_home.data.data_source.dto.InspirationQuoteServerResponse
import retrofit2.http.GET

interface HomeApi {
    @GET("today/")
    suspend fun getTodayQuote(): List<InspirationQuoteServerResponse>
}