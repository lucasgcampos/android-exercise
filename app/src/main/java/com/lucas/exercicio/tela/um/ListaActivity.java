package com.lucas.exercicio.tela.um;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lucas.exercicio.tela.dois.DescricaoActivity;
import com.lucas.exercicio.ModelService;
import com.lucas.exercicio.R;
import com.lucas.exercicio.RestClient;

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
                    final ListaModelo resultados = response.body();

                    ModeloAdapter adapter = new ModeloAdapter(resultados, ListaActivity.this);

                    ListView listView = (ListView) findViewById(R.id.lista);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ListaActivity.this, DescricaoActivity.class);
                            intent.putExtra("venue", resultados.getAvfms().get(position).getVenue());
                            startActivity(intent);
                        }
                    });
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
