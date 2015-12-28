package com.lucas.exercicio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ListaActivity extends AppCompatActivity {

    private RestClient restClient = new RestClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        ModelService modelService = restClient.getModelService();
        final Call<ListaModelo> callBack = modelService.getModelos();

        callBack.enqueue(new Callback<ListaModelo>() {
            @Override
            public void onResponse(Response<ListaModelo> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ListaModelo resultados = response.body();

                    ModeloAdapter adapter = new ModeloAdapter(resultados, ListaActivity.this);

                    ListView listView = (ListView) findViewById(R.id.lista);
                    listView.setAdapter(adapter);
                } else {
                    Log.i("ERROR", "Status: "+ response.code() + ". " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("ERROR", t.getLocalizedMessage());
            }
        });
    }

}
