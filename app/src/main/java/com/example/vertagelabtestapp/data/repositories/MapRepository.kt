package com.example.vertagelabtestapp.data.repositories

import com.example.vertagelabtestapp.api.FakeApi
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val api: FakeApi
) {
    suspend fun getPlaces() = api.getPlaces()
}