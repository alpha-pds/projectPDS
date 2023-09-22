package com.example.emergencyapp.models;

public class GoogleSheetsResponse {
    private static RetroFit getClientGetMethod(String baseUrl){
        if(retrofit == null){
            retrofit = new RetroFit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return  retrofit;
    }
}
