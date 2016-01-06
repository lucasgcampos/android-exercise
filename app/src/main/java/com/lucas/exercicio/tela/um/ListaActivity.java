package com.lucas.exercicio.tela.um;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.lucas.exercicio.R;

public class ListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarFragment();
    }

    public boolean carregarFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        boolean conectado = isConectado();

        if (conectado) {
            transaction.replace(R.id.tela, new LocalFragment());
        } else {
            transaction.replace(R.id.tela, new SemConexaoFragment());
        }

        transaction.addToBackStack(null).commit();

        return conectado;
    }

    private boolean isConectado() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
