package com.lucas.exercicio;

import retrofit.Call;
import retrofit.http.GET;

/**
 * @author Lucas Campos
 *         12/26/15
 */
public interface ModelService {

    @GET("featured.php?appkey=f6bcd8e8bb853890f4fb2be8ce0418fa")
    Call<ListaModelo> getModelos();

}
