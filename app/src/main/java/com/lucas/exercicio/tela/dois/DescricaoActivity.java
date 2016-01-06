package com.lucas.exercicio.tela.dois;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucas.exercicio.AppService;
import com.lucas.exercicio.R;
import com.lucas.exercicio.RestClient;
import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DescricaoActivity extends AppCompatActivity {

    private RestClient restClient = new RestClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao);

        Intent intent = getIntent();
        final String venue = intent.getStringExtra("venue");

        if (venue != null) {
            AppService appService = restClient.getAppService();
            Call<ListaDescricao> callback = appService.getDescricao(venue);

            callback.enqueue(new Callback<ListaDescricao>() {
                @Override
                public void onResponse(Response<ListaDescricao> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Descricao resultado = response.body().getAvfms().get(0);

                        TextView venueT = (TextView) findViewById(R.id.venue_descricao);
                        TextView address = (TextView) findViewById(R.id.address);
                        TextView city = (TextView) findViewById(R.id.city);
                        TextView state = (TextView) findViewById(R.id.state);
                        TextView country = (TextView) findViewById(R.id.country);
                        TextView rating = (TextView) findViewById(R.id.rating);

                        TextView stats = (TextView) findViewById(R.id.stats);
                        TextView link = (TextView) findViewById(R.id.link);

                        venueT.setText(venue);
                        address.setText(resultado.getAddress());
                        city.setText(resultado.getCity());
                        state.setText(resultado.getState());
                        country.setText(resultado.getCountry());
                        rating.setText(resultado.getAverage_rating());
                        if (resultado.getStats() != null) {
                            stats.setText(Html.fromHtml(resultado.getStats()));
                        }
                        if (resultado.getLink() != null) {
                            link.setText(resultado.getLink());
                        }

                        ImageView imageView = (ImageView) findViewById(R.id.imagem_descricao);
                        Picasso.with(DescricaoActivity.this)
                                .load("http://aviewfrommyseat.com/photos/" + resultado.getNewest_image())
                                .fit()
                                .into(imageView);
                    } else {
                        Log.i("ERROR", "Status: "+ response.code() + ". " + response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.i("ERROR", t.getLocalizedMessage());
                }
            });


        } else {
            //TODO lançar error, pois sem venue não há como requisitar a API de descrição
        }
    }
}
