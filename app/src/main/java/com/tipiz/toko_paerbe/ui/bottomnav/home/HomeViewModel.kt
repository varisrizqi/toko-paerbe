package com.tipiz.toko_paerbe.ui.bottomnav.home

import androidx.lifecycle.ViewModel
import com.tipiz.core.domain.usecase.TokoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeViewModel(private val useCase: TokoUseCase) : ViewModel() {

    //===== Local =====
    fun clearSession() {
        runBlocking(Dispatchers.IO) {
            useCase.clearSession()
        }
    }

    fun setTheme(value: Boolean) {
        runBlocking(Dispatchers.IO) {
            useCase.setTheme(value)
        }
    }

    fun getTheme(): Boolean {
       return runBlocking(Dispatchers.IO) {
           useCase.getTheme().first()
       }
    }

    fun setLocalize(value:String){
        runBlocking {
            useCase.setLocalize(value)
        }
    }

    fun getLocalize():String{
        return runBlocking (){
            useCase.getLocalize().first()
        }
    }

    fun resetAll(){
        runBlocking(Dispatchers.IO) {
            useCase.resetAll()
        }
    }
}