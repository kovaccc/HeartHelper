package com.example.hearthelper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.hearthelper.model.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_result.*
import org.koin.android.viewmodel.ext.android.getViewModel


private const val TAG = "ResultActivity"



class ResultActivity : AppCompatActivity() {

    private var user:User? = null
    private var requestBody: String? = null

    private var snackbar:Snackbar? = null

    private lateinit var resultViewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        resultViewModel = getViewModel()

        user = intent.extras?.getParcelable<User>(USER_TRANSFER) as User
        Log.d(TAG, "onCreate user is $user")

        resultViewModel.currentResultLD.observe(this, { result ->
            if( result >= 0.51) {
                tvDeathEventResult.text  = getString(R.string.negative_death_result)
            }
            else {
                tvDeathEventResult.text  = getString(R.string.positive_death_result)
            }
        })

        resultViewModel.currentSnackBarLD.observe(this, {
            if(it) {
                createSnackBar()
                snackbar!!.show()
            }
            else {
                if(snackbar != null) {
                    snackbar!!.dismiss()
                }
            }
        })
        Log.d(TAG, "onCreate ends")
    }

    private fun createSnackBar() {
        snackbar =  Snackbar.make(findViewById(android.R.id.content), getString(R.string.snackbar_error_message), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.snackbar_try_again)) {
                if (user != null && requestBody != null) {
                    Log.d(TAG, "createSnackBar: runAzureML starts")
                    resultViewModel.runAzureML(requestBody!!)
                    resultViewModel.resetSnackBarState()
                    Log.d(TAG, "createSnackBar: runAzureML ends")
                }
            }
    }

    override fun onResume() {
        Log.d(TAG, "onResume starts")
        super.onResume()
        if(user != null) {
            Log.d(TAG, "onResume: azure ML response starts user is $user")
            requestBody = "{\n" +
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
                    "          \"${user!!.age}\",\n" +
                    "          \"${user!!.anaemia}\",\n" +
                    "          \"${user!!.creatinine_phosphokinase}\",\n" +
                    "          \"${user!!.diabetes}\",\n" +
                    "          \"${user!!.ejection_fraction}\",\n" +
                    "          \"${user!!.high_blood_pressure}\",\n" +
                    "          \"${user!!.platelets}\",\n" +
                    "          \"${user!!.serum_creatinine}\",\n" +
                    "          \"${user!!.serum_sodium}\",\n" +
                    "          \"${user!!.sex}\",\n" +
                    "          \"${user!!.smoking}\",\n" +
                    "          \"0\"\n" +
                    "        ],\n" +
                    "        ]\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"GlobalParameters\": {}\n" +
                    "}"

            resultViewModel.runAzureML(requestBody!!)
            resultViewModel.resetSnackBarState()
        }
        Log.d(TAG, "onResume ends")
    }

}