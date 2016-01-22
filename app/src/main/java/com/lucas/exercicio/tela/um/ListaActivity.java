package com.lucas.exercicio.tela.um;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.lucas.exercicio.R;

public class ListaActivity extends AppCompatActivity {

    public static final String LISTA_FRAGMENT = "home";
    public static final String NO_CONNECTION_FRAGMENT = "no-connection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        carregarFragment();
    }

    public boolean carregarFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        boolean conectado = isConectado();

        if (conectado) {
            boolean hasListaFragment = fragmentManager.popBackStackImmediate(LISTA_FRAGMENT, 0);
            if (!hasListaFragment) {
                transaction.replace(R.id.tela, new LocalFragment()).addToBackStack(LISTA_FRAGMENT);
            }
        } else {
            boolean hasNoConnectionFragment = fragmentManager.popBackStackImmediate(NO_CONNECTION_FRAGMENT, 0);
            if (!hasNoConnectionFragment) {
                transaction.replace(R.id.tela, new SemConexaoFragment()).addToBackStack(NO_CONNECTION_FRAGMENT);
            }
        }

        transaction.commit();

        return conectado;
    }

    private boolean isConectado() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
