package hu.bence.flyhigh.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://10.0.2.2:5159/";


    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static UseradatokApi getUserApi() {
        return getRetrofit().create(UseradatokApi.class);
    }

    public static GepAdatokApi getGepAdatokApi() {
        return getRetrofit().create(GepAdatokApi.class);
    }

    public static JegyAdatokApi getJegyAdatokApi() {
        return getRetrofit().create(JegyAdatokApi.class);
    }

    public static JegykezelesApi getJegykezelesApi() {
        return getRetrofit().create(JegykezelesApi.class);
    }




}
