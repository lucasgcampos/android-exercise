package com.lucas.exercicio;

import com.lucas.exercicio.tela.dois.ListaDescricao;
import com.lucas.exercicio.tela.um.ListaLocal;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author Lucas Campos
 *         12/26/15
 */
public interface ModelService {

    @GET("featured.php?appkey=f6bcd8e8bb853890f4fb2be8ce0418fa")
    Call<ListaLocal> getModelos();

    @GET("venue.php?appkey=f6bcd8e8bb853890f4fb2be8ce0418fa&venue=Coleman+Coliseum&info=true")
    Call<ListaDescricao> getDescricao(@Query("venue") String venue);

}
