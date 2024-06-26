

import com.example.bskl_kotlin.rest_api.ApiInterface
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
  //var BASE_URL = "https://bsklparentapp.com.my/bskl/"
  //var BASE_URL = "http://alpha.mobatia.in:808/bskl/"
//  var BASE_URL = "http://gama.mobatia.in:8080/BISKL2023/public/Api-V1/"
  var BASE_URL = "http://gama.mobatia.in:8080/bisklv10/public/Api-V1/"
 // var BASE_URL = "http://gama.mobatia.in:8080/BISKL2023/public/Api-V1/"
//    var BASE_URL =  "http://gama.mobatia.in:8080/bisklv10/public/Api-V1/"



    val getClient: ApiInterface
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(ApiInterface::class.java)

        }

}