package com.example.hearthelper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import kotlinx.coroutines.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class GetResultJsonDataTest{

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    // currently there is not method setDefault so we can change dispatcher to our dispatcher like we
    // did with MainCoroutineRule and injecting dispatcher in every class don't look clean, possibly in future some method will appear
    @Test
    fun `parsing unexpected JSON response in String format, returns null`() {
        val getResultJsonData = GetResultJsonData()
        val job = CoroutineScope(Dispatchers.Default).launch{
            val result =  getResultJsonData.parseJsonData("unexpected JSON format")
            Truth.assertThat(result).isNull()
        }

        job.cancel()
    }

    @Test
    fun `parsing expected JSON response in String format, returns array of doubles`() {
        val getResultJsonData = GetResultJsonData()
        val job = CoroutineScope(Dispatchers.Default).launch{
            val result =  getResultJsonData.parseJsonData("{\"Results\":{\"output1\":{\"type\":\"table\",\"value\":{\"ColumnNames\":[\"age\",\"anaemia\",\"creatinine_phosphokinase\",\"diabetes\",\"ejection_fraction\",\"high_blood_pressure\",\"platelets\",\"serum_creatinine\",\"serum_sodium\",\"sex\",\"smoking\",\"DEATH_EVENT\",\"Scored Labels\",\"Scored Probabilities\"],\"ColumnTypes\":[\"Double\",\"Double\",\"Double\",\"Double\",\"Double\",\"Double\",\"Double\",\"Double\",\"Double\",\"Double\",\"Double\",\"Double\",\"Double\",\"Double\"],\"Values\":[[\"-4.70184054212637\",\"-0.871104775220389\",\"-0.595500075001874\",\"-0.847579379526013\",\"-2.80012850963534\",\"-0.735688190337497\",\"-2.69716870286356\",\"6.3964532028258\",\"-29.8802855241678\",\"-1.35927151357595\",\"-0.687681906073503\",\"-0.687681906073503\",\"1.45416069721793\",\"0.999980866909027\"]]}}}}")
            val testArray: DoubleArray = doubleArrayOf(-4.70184054212637,-0.871104775220389,-0.595500075001874,-0.847579379526013,-2.80012850963534,-0.735688190337497,-2.69716870286356,6.3964532028258,-29.8802855241678,-1.35927151357595,-0.687681906073503,-0.687681906073503,1.45416069721793,0.999980866909027)
            Truth.assertThat(result).isEqualTo(testArray)
        }
        job.cancel()
    }
}