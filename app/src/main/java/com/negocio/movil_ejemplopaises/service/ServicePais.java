package com.negocio.movil_ejemplopaises.service;

import com.negocio.movil_ejemplopaises.entity.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicePais {

    @GET("all")
    public abstract Call<List<Pais>> listaPais();

}
