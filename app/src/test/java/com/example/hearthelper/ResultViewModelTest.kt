package com.example.hearthelper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule




@ExperimentalCoroutinesApi
class ResultViewModelTest{

    private lateinit var viewModel: ResultViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel = ResultViewModel()

    }


    @Test
    fun `when fetching result fails, resultLiveData equal to null, snackbarLiveData equal to true`() {

        mainCoroutineRule.runBlockingTest {

            viewModel.runAzureML("Random String")
            val resultValue = viewModel.currentResultLD.value

            val snackbarValue = viewModel.currentSnackBarLD.getOrAwaitValueTest(5)

            assertThat(resultValue).isEqualTo(null)
            assertThat(snackbarValue).isTrue()
        }
    }


    @Test
    fun `when fetching result is success, resultLiveData equal is not null, snackbarLiveData equal to false`() {
            val job = CoroutineScope(Dispatchers.Default).launch { // because of parsing JSON in Default dispatcher don't cause exception

                val requestBody = "{\n" +
                        "  \"Inputs\": {\n" +
                        "    \"input1\": {\n" +
                        "      \"ColumnNames\": [\n" +
                        "        \"age\",\n" +
                        "        \"anaemia\",\n" +
                        "        \"creatinine_phosphokinase\",\n" +
                        "        \"diabetes\",\n" +
                        "        \"ejection_fraction\",\n" +
                        "        \"high_blood_pressure\",\n" +
                        "        \"platelets\",\n" +
                        "        \"serum_creatinine\",\n" +
                        "        \"serum_sodium\",\n" +
                        "        \"sex\",\n" +
                        "        \"smoking\",\n" +
                        "        \"DEATH_EVENT\"\n" +
                        "        ],\n" +
                        "        \"Values\": [\n" +
                        "        [\n" +
                        "          \"1\",\n" +
                        "          \"1\",\n" +
                        "          \"1\",\n" +
                        "          \"1\",\n" +
                        "          \"1\",\n" +
                        "          \"1\",\n" +
                        "          \"1\",\n" +
                        "          \"1\",\n" +
                        "          \"1\",\n" +
                        "          \"1\",\n" +
                        "          \"1\",\n" +
                        "          \"0\"\n" +
                        "        ],\n" +
                        "        ]\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"GlobalParameters\": {}\n" +
                        "}"
                viewModel.runAzureML(requestBody)
                val resultValue = viewModel.currentResultLD.getOrAwaitValueTest(5)
                val snackbarValue = viewModel.currentSnackBarLD.getOrAwaitValueTest(5)

                assertThat(resultValue).isNotNull()
                assertThat(snackbarValue).isFalse()
            }
        job.cancel()
    }

    @Test
    fun `resetSnackBarState, snackbar Live data equal to false`() {
        viewModel.resetSnackBarState()
        val snackbarValue = viewModel.currentSnackBarLD.getOrAwaitValueTest()
        assertThat(snackbarValue).isFalse()
    }


}