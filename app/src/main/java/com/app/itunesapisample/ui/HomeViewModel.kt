package com.app.itunesapisample.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.itunesapisample.models.Track
import com.app.itunesapisample.repository.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class HomeViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _tracks = MutableLiveData<List<Track>>()
    private val _status = MutableLiveData<ApiStatus>()

    val tracks: LiveData<List<Track>>
        get() = _tracks
    val status: LiveData<ApiStatus>
        get() = _status

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getTracks(query: String) {
        val job = uiScope.launch {
            try {
                _status.postValue(ApiStatus.LOADING)
                _tracks.postValue(TracksRepository.searchItem(query))
                _status.postValue(ApiStatus.DONE)
            } catch (t: Throwable) {
                _status.postValue(ApiStatus.ERROR)
                Log.d("HomeViewModel", "getTracks: ${t.message}")
            }
        }
    }
}