package com.lucas.exercicio.tela.um;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucas.exercicio.ModelService;
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

        ModelService modelService = restClient.getModelService();
        final Call<ListaLocal> callBack = modelService.getModelos();

        callBack.enqueue(new Callback<ListaLocal>() {
            @Override
            public void onResponse(Response<ListaLocal> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    final ListaLocal resultados = response.body();

                    ModeloAdapter adapter = new ModeloAdapter(resultados, getActivity());

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
                Log.i("ERROR", t.getLocalizedMessage());
            }
        });

        return layout;
    }

}
