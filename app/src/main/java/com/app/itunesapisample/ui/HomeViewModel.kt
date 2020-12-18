package com.app.itunesapisample.ui

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
    val tracks = MutableLiveData<List<Track>>()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getTracks(query: String) {
        uiScope.launch {
            tracks.postValue(TracksRepository.searchItem(query))
        }
    }
}