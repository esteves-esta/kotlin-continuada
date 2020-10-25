package fernanda.esteves.projetoac2.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {

    private val URL =  "https://5f95b8092de5f50016ca224d.mockapi.io/v1/"
    var retrofit: Retrofit? = null

    fun getApiClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit
    }
}