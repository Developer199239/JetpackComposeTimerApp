package com.example.timerapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimerViewModel : ViewModel() {
    private val initialTime = 30L
    private val _remainingTime = MutableStateFlow(initialTime)
    val remainingTime: StateFlow<Long> = _remainingTime

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _progress = MutableStateFlow(1f)
    val progress: StateFlow<Float> = _progress

    fun toggleTimer() {
        if (_isRunning.value) {
            _isRunning.value = false
        } else {
            _isRunning.value = true
            viewModelScope.launch {
                while (_isRunning.value && _remainingTime.value > 0) {
                    delay(1000)
                    _remainingTime.value--
                    _progress.value = _remainingTime.value.toFloat() / initialTime
                }
                _isRunning.value = false
//                _remainingTime.value = initialTime
//                _progress.value = 0f
            }
        }
    }

    fun resetTimer() {
        _isRunning.value = false
        _remainingTime.value = initialTime
        _progress.value = 0f
    }
}
