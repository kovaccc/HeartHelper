package com.example.hearthelper

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val TAG = "ResultViewModel"


private const val END_POINT_URL = "https://ussouthcentral.services.azureml.net/workspaces/41833538fb9940d38dbd72ff54561347/services/75a8d53f615b4510b80d20852c1022bf/execute?api-version=2.0&details=true"
private const val AZURE_KEY = "yedgyO7zEkrKwEfSV144Zf35VTr8cjzx/vg43LPTBXpx3m4DI+kEbnJKjvibnrCsHYUHztP5HpMUDDRskGvdOw=="

class ResultViewModel: ViewModel() {

    private val _currentResultMLD = MutableLiveData<Double>()
    val currentResultLD: LiveData<Double>
        get() = _currentResultMLD

    private val _currentSnackBarMLD = MutableLiveData<Boolean>()
    val currentSnackBarLD: LiveData<Boolean>
        get() = _currentSnackBarMLD



    private fun updateDeathEventResult(data: ArrayList<Double>) {
        Log.d(TAG, "updateDeathEventResult: starts with $data")
        _currentResultMLD.value = data[13] // get only last value we need
        Log.d(TAG, "updateDeathEventResult: ends with ${_currentResultMLD.value}")
    }

    fun resetSnackBarState() {
        _currentSnackBarMLD.value = false
    }

    fun runAzureML(requestBody: String) {


        Log.d(TAG, "runAzureML: starts with $requestBody")
        val azureMLClient = AzureMLClient(END_POINT_URL, AZURE_KEY)
        viewModelScope.launch {
            val result =  withContext(IO) {azureMLClient.requestResponse(requestBody)}
            val status = azureMLClient.getDownloadStatus()
            Log.d(TAG, "runAzureML: azure ML response result is $result, and status $status")
            if(status == DownloadStatus.OK) {
                val arrayResults = convertJsonResponse(result)
                updateDeathEventResult(arrayResults)
            }
            else {
                // download failed
                _currentSnackBarMLD.value = true
                Log.d(TAG, "runAzureML failed with status $status. Error message is: $result, snackbarvalue: ${_currentSnackBarMLD.value}")
            }
        }
        Log.d(TAG, "runAzureML: ends")
    }

    private suspend fun convertJsonResponse(result: String) : ArrayList<Double>{
        Log.d(TAG, "convertJsonResponse: starts with $result")
        val getResultJsonData = GetResultJsonData()
        val parsedData = getResultJsonData.parseJsonData(result)
        Log.d(TAG, "convertJsonResponse: ends with $parsedData")
        return parsedData
    }


    override fun onCleared() {
        Log.d(TAG, "onCleared: starts")
        super.onCleared()
    }
}