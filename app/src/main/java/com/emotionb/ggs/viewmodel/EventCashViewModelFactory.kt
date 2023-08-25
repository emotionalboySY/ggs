package com.emotionb.ggs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EventCashViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EventCashViewModel::class.java)) {
            return EventCashViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}