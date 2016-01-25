package com.lucas.exercicio.tela.dois;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.lucas.exercicio.AppService;
import com.lucas.exercicio.CustomImageView;
import com.lucas.exercicio.R;
import com.lucas.exercicio.RestClient;
import com.lucas.exercicio.tela.um.ListaActivity;
import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DescricaoFragment extends Fragment {

    public static final String URL_FOTOS = "http://aviewfrommyseat.com/photos/";
    public static final String VENUE = "venue";

    private RestClient restClient = new RestClient();
    private View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.layout = inflater.inflate(R.layout.fragment_descricao, container, false);

        final String venue = getActivity().getIntent().getStringExtra(VENUE);

        if (venue != null) {
            AppService appService = restClient.getAppService();
            final Call<ListaDescricao> callback = appService.getDescricao(venue);

            callback.enqueue(new Callback<ListaDescricao>() {
                @Override
                public void onResponse(Response<ListaDescricao> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        final Descricao resultado = response.body().getAvfms().get(0);

                        TextView venueT = (TextView) layout.findViewById(R.id.venue_descricao);
                        TextView address = (TextView) layout.findViewById(R.id.address);
                        TextView city = (TextView) layout.findViewById(R.id.city);
                        TextView state = (TextView) layout.findViewById(R.id.state);
                        TextView country = (TextView) layout.findViewById(R.id.country);
                        TextView rating = (TextView) layout.findViewById(R.id.rating);

                        TextView stats = (TextView) layout.findViewById(R.id.stats);
                        TextView link = (TextView) layout.findViewById(R.id.link);

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

                        final CustomImageView imageView = (CustomImageView) layout.findViewById(R.id.imagem_descricao);
                        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                imageView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (getResources().getBoolean(R.bool.isLand)) {
                                            Picasso.with(getActivity()).load(URL_FOTOS + resultado.getNewest_image()).resize(imageView.getLargura(), imageView.getHeight()).centerCrop().into(imageView);
                                        } else {
                                            Picasso.with(getActivity()).load(URL_FOTOS + resultado.getNewest_image()).resize(imageView.getWidth(), imageView.getAltura()).centerCrop().into(imageView);
                                        }

                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "Não foi possível consultar os dados.", Toast.LENGTH_LONG).show();
                        throw new RuntimeException("#ERROR: Não obteve sucesso ao realizar uma requisição");
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), "Não foi possível consultar os dados.", Toast.LENGTH_LONG).show();
                    throw new RuntimeException("Falha ao consultar os dados. #ERROR: " + t.getMessage());
                }
            });
        } else {
            Intent intent = new Intent(layout.getContext(), ListaActivity.class);
            layout.getContext().startActivity(intent);
        }

        return layout;
    }

}
