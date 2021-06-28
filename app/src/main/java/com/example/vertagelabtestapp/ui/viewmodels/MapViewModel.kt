package com.example.vertagelabtestapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vertagelabtestapp.data.models.FakeResponse
import com.example.vertagelabtestapp.data.models.Place
import com.example.vertagelabtestapp.data.repositories.MapRepository
import com.example.vertagelabtestapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MapRepository
) : ViewModel() {
    private val _places: MutableLiveData<Resource<FakeResponse>> = MutableLiveData()
    val places: LiveData<Resource<FakeResponse>> get() = _places

    init {
        getPlaces()
    }

    fun getPlaces() = viewModelScope.launch {
        _places.postValue(Resource.Loading())
        val response = repository.getPlaces()
        _places.postValue(handleResponse(response))
    }

    private fun handleResponse(response: Response<FakeResponse>) : Resource<FakeResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}