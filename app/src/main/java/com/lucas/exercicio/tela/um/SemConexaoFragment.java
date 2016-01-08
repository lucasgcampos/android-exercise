package com.lucas.exercicio.tela.um;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lucas.exercicio.R;

public class SemConexaoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_sem_localizacao, container, false);

        Button button = (Button) layout.findViewById(R.id.try_again);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListaActivity activity = (ListaActivity) getActivity();
                boolean conectado = activity.carregarFragment();

                if (!conectado) {
                    Toast.makeText(activity, "Você ainda não está conectado, tente mais tarde!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return layout;
    }

}
