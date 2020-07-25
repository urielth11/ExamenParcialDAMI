package com.examenparcial.servicio;

import com.examenparcial.entidad.Cliente;
import com.examenparcial.entidad.Marca;
import com.examenparcial.entidad.Pedido;
import com.examenparcial.entidad.Producto;
import com.examenparcial.entidad.Ubigeo;

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
    @GET("listaMarca")
    public abstract Call<List<Marca>> listaMarca();

    @POST("marca")
    public abstract Call<Marca> agregaMarca(@Body Marca pedido);

    @PUT("marca")
    public abstract Call<Marca> actualizaMarca(@Body Marca pedido);

    @DELETE("marca/{idMarca}")
    public abstract Call<Marca> eliminaMarca(@Path("idMarca") int id);

    //Registrar Pedido
    @GET("listaPedido")
    public abstract Call<List<Pedido>> listaPedido();

    @POST("pedido")
    public abstract Call<Pedido> agregaPedido(@Body Pedido pedido);

    //Lista Ubigeo

    @GET("departamentos")
    Call<List<String>> listaDepartamentos();

    @GET("provincias/{dep}")
    Call<List<String>> listaProvincias(@Path("dep")String idDep);

    @GET("distritos/{dep}/{pro}")
    Call<List<Ubigeo>> listaDistritos(@Path("dep")String idDep, @Path("pro")String idPro);

    //Cliente
    @GET("listaCliente")
    public abstract Call<List<Cliente>> listaCliente();

    //Productos
    @GET("listaProducto")
    public abstract Call<List<Producto>> listaProducto();

}
