import com.example.eznstore.LoginResponse
import com.example.eznstore.Producto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.GET

interface FakeStoreApiService {
    @GET("products")
    fun obtenerProductos(): Call<List<Producto>>

    @FormUrlEncoded
    @POST("auth/login")
    fun iniciarSesion(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}
