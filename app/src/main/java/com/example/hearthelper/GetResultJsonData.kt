package com.example.hearthelper

import android.util.Log
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject

private const val TAG = "GetResultJsonData"
class GetResultJsonData {

    suspend fun parseJsonData(data : String) : ArrayList<Double> {
        Log.d(TAG, "parseJsonData starts with $data")
        val valueList = ArrayList<Double>()
        withContext(Default) { // Calls the specified suspending block with a given coroutine context, Default dispatcher for json parsing
            try {
                val jsonData = JSONObject(data)
                val results = jsonData.getJSONObject("Results")
                val output = results.getJSONObject("output1")
                val value = output.getJSONObject("value")
                Log.d(TAG, "parseJsonData value is$value")
                val itemsArray = value.getJSONArray("Values")
                Log.d(TAG, "parseJsonData itemsArray is$itemsArray")
                val item = itemsArray.getJSONArray(0)
                Log.d(TAG, "parseJsonData item is$item")
                for(i in 0 until item.length()) {
                    valueList.add(item.getDouble(i))
                }

            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e(TAG, ".parseJsonData: Error processing Json data. ${e.message}")
            }
        }
        Log.d(TAG, "parseJsonData ends with $valueList")
        return valueList
    }
}