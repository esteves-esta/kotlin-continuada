package fernanda.esteves.projetoac2.services

import fernanda.esteves.projetoac2.models.Dream
import retrofit2.Call
import retrofit2.http.*

interface Rotas {
    @GET("/dream")
    fun getAll(): Call<List<Dream>>

    @GET("/dream/{id}")
    fun getOne(@Path("id") id: Int): Call<Dream>

    // iniciar viagem
    @POST("/dream")
    fun setOne(@Body dream: Dream) : Call<Void>
}