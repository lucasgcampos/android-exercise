package com.lucas.exercicio.tela.um;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lucas.exercicio.AppService;
import com.lucas.exercicio.R;
import com.lucas.exercicio.RestClient;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LocalFragment extends Fragment {

    private RestClient restClient = new RestClient();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_local, container, false);

        AppService appService = restClient.getAppService();
        final Call<ListaLocal> callBack = appService.getModelos();

        callBack.enqueue(new Callback<ListaLocal>() {
            @Override
            public void onResponse(Response<ListaLocal> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    final ListaLocal resultados = response.body();

                    LocalAdapter adapter = new LocalAdapter(resultados, getActivity());

                    RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.lista);
                    recyclerView.setAdapter(adapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(layout.getContext());
                    recyclerView.setLayoutManager(layoutManager);
                } else {
                    Log.i("ERROR", "Status: " + response.code() + ". " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Não foi possível consultar os dados.", Toast.LENGTH_LONG).show();
                throw new RuntimeException("Falha ao consultar os dados. #ERROR: " + t.getMessage());
            }
        });

        return layout;
    }

}
