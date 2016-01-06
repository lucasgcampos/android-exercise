package com.lucas.exercicio;

import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * @author Lucas Campos
 *         12/26/15
 */
public class RestClient {

    private static final String BASE_URL = "https://aviewfrommyseat.com/avf/api/";
    private AppService appService;
    private static OkHttpClient httpClient = new OkHttpClient();

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        appService = retrofit.create(AppService.class);
    }

    public AppService getAppService() {
        return appService;
    }

}
