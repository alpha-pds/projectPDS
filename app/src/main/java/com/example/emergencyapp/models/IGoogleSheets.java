package com.example.emergencyapp.models;

public interface IGoogleSheets {
    @GET
    Call<String> getPeople(@Url String url);

    @POST("exec")
    Call<String> getStringRequestBody(@Body String body);
}
