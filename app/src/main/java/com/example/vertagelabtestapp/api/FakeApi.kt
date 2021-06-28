package com.example.vertagelabtestapp.api

import com.example.vertagelabtestapp.data.models.FakeResponse
import retrofit2.Response
import retrofit2.http.GET

interface FakeApi {
    @GET("/kyiv/places")
    suspend fun getPlaces() : Response<FakeResponse>
}