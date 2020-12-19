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

class HomeViewModel: ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _tracks = MutableLiveData<List<Track>>()

    val tracks: LiveData<List<Track>>
        get() = _tracks

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getTracks(query: String) {
        uiScope.launch {
            try {
                _tracks.postValue(TracksRepository.searchItem(query))
            } catch (t: Throwable){
                Log.d("HomeViewModel", "getTracks: ${t.message}")
            }
        }
    }
}