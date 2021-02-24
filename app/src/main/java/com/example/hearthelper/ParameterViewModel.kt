package com.example.hearthelper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hearthelper.model.User


private const val TAG = "ParameterViewModel"
class ParameterViewModel : ViewModel() {

    private val _currentAnaemiaMLD = MutableLiveData<Long>()
    val currentAnaemiaLD: LiveData<Long>
        get() = _currentAnaemiaMLD


    private val _currentDiabetesMLD = MutableLiveData<Long>()
    val currentDiabetesLD: LiveData<Long>
        get() = _currentDiabetesMLD



    private val _currentSmokingMLD = MutableLiveData<Long>()
    val currentSmokingLD: LiveData<Long>
        get() = _currentSmokingMLD

    private val _currentBloodPressureMLD = MutableLiveData<Long>()
    val currentHighBloodPressureLD: LiveData<Long>
        get() = _currentBloodPressureMLD


    private val _currentSexMLD = MutableLiveData<Long>()
    val currentSexLD: LiveData<Long>
        get() =  _currentSexMLD



    fun updateSmoking(smoking: Long) {
        Log.d(TAG, "updateSmoking starts")
        _currentSmokingMLD.value = smoking
    }

    fun updateDiabetes(diabetes: Long) {
        Log.d(TAG, "updateDiabetes starts")
        _currentDiabetesMLD.value = diabetes
    }

    fun updateBloodPressure(bloodPressure: Long) {
        Log.d(TAG, "updateBloodPressure starts")
        _currentBloodPressureMLD.value = bloodPressure
    }

    fun updateSex(sex: Long) {
        Log.d(TAG, "updateSex starts")
        _currentSexMLD.value = sex
    }

    fun updateAnaemia(anaemia: Long) {
        Log.d(TAG, "updateAnaemia starts")
        _currentAnaemiaMLD.value = anaemia
    }

    fun createUser(age: Double,creatininePhosphokinase: Long, ejectionFraction: Long, platelets: Double, serumCreatinine: Double, serumSodium: Long) : User {

        Log.d(TAG, "createUser starts")
        return User("1mka", age, _currentAnaemiaMLD.value ?: 0,
            creatininePhosphokinase, _currentDiabetesMLD.value ?: 0, ejectionFraction,
            _currentBloodPressureMLD.value ?: 0, platelets,
            serumCreatinine, serumSodium, _currentSexMLD.value ?: 0, _currentSmokingMLD.value ?: 0)
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared starts")
        super.onCleared()
        Log.d(TAG, "onCleared ends")
    }

}