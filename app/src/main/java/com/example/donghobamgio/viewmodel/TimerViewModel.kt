package com.example.donghobamgio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.donghobamgio.model.TimerModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TimerViewModel : ViewModel(), CoroutineScope {

    private var job: Job? = null
    private val timerModel = TimerModel()
    private val _timeLiveData = MutableLiveData<String>()
    val timeLiveData: LiveData<String> get() = _timeLiveData

    private val _isRunning = MutableLiveData<Boolean>(false)
    val isRunning: LiveData<Boolean> get() = _isRunning

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    fun start() {
        job?.cancel()
        job = launch {
            try {
                _isRunning.postValue(true)
                while (isActive) {
                    delay(10)
                    timerModel.incrementTime(10)
                    _timeLiveData.postValue(formatTime(timerModel.getTime()))
                }
            } catch (e: Exception) {
                // Handle exception
            } finally {
                _isRunning.postValue(false)
            }
        }
    }

    fun pause() {
        job?.cancel()
        _isRunning.postValue(false)
    }

    fun reset() {
        job?.cancel()
        timerModel.resetTime()
        _timeLiveData.postValue(formatTime(timerModel.getTime()))
        _isRunning.postValue(false)
    }

    private fun formatTime(timeInMillis: Long): String {
        val minutes = (timeInMillis / 60000).toInt()
        val seconds = (timeInMillis % 60000 / 1000).toInt()
        val milliseconds = (timeInMillis % 1000 / 10).toInt()
        return String.format("%02d:%02d.%02d", minutes, seconds, milliseconds)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
