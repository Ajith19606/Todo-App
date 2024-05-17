package com.example.todoapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("/v3/8dd19376-9d63-4741-97e9-3d351e3fc202/")
    Call <DataResponse> getData();

    @GET("/v3/8dd19376-9d63-4741-97e9-3d351e3fc202/")
    Call <DataResponse> getData1();

}
