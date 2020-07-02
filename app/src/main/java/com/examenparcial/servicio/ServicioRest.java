package com.examenparcial.servicio;

import com.examenparcial.entidad.Marca;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServicioRest {
    //Crud de Rol
    @GET("marca")
    public abstract Call<List<Marca>> listaMarca();

    @POST("marca")
    public abstract Call<Marca> agregaMarca(@Body Marca rol);

    @PUT("marca")
    public abstract Call<Marca> actualizaMarca(@Body Marca rol);

    @DELETE("marca/{idMarca}")
    public abstract Call<Marca> eliminaMarca(@Path("idMarca") int id);
}
