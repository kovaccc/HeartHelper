package com.example.hearthelper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.getViewModel


private const val TAG = "MainActivity"
internal const val USER_TRANSFER = "USER_TRANSFER"
private const val POSITIVE_RB_VALUE = 1L
private const val NEGATIVE_RB_VALUE = 0L
class MainActivity : AppCompatActivity() {

    private lateinit var parameterViewModel : ParameterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        parameterViewModel = getViewModel()

        btnConfirmData.setOnClickListener {
            Log.d(TAG, "btnConfirmData clicked")
            if(etAge.text.isEmpty() || etCreatininePhosphokinase.text.isEmpty() || etEjectionFraction.text.isEmpty() || etPlatelets.text.isEmpty() || etSerumCreatinine.text.isEmpty() || etSerumSodium.text.isEmpty()) {
                Toast.makeText(this@MainActivity, getString(R.string.toast_error_message), Toast.LENGTH_SHORT).show()
            }
            else {
               val user =  parameterViewModel.createUser(etAge.text.toString().toDouble(), etCreatininePhosphokinase.text.toString().toLong(),
                    etEjectionFraction.text.toString().toLong(), etPlatelets.text.toString().toDouble(), etSerumCreatinine.text.toString().toDouble(), etSerumSodium.text.toString().toLong())

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(USER_TRANSFER, user)
                startActivity(intent)
            }
        }
        Log.d(TAG, "onCreate ends")
    }

    fun onClickCheckSex(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.getId()) {
            R.id.rbSexMale -> {
                if (checked) parameterViewModel.updateSex(POSITIVE_RB_VALUE)
            }
            R.id.rbSexFemale -> {
                if (checked) parameterViewModel.updateSex(NEGATIVE_RB_VALUE)
            }
        }
    }



    fun onClickCheckAnaemia(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.getId()) {
            R.id.rbAnemiaPositive -> {
                if (checked) parameterViewModel.updateAnaemia(POSITIVE_RB_VALUE)
            }
            R.id.rbAnemiaNegative -> {
                if (checked) parameterViewModel.updateAnaemia(NEGATIVE_RB_VALUE)
            }
        }
    }

    fun onClickCheckDiabetes(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.getId()) {
            R.id.rbDiabetesPositive -> {
                if (checked) parameterViewModel.updateDiabetes(POSITIVE_RB_VALUE)
            }
            R.id.rbDiabetesNegative -> {
                if (checked) parameterViewModel.updateDiabetes(NEGATIVE_RB_VALUE)
            }
        }
    }

    fun onClickCheckHighBloodPressure(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.getId()) {
            R.id.rbHighBloodPressurePositive -> {
                if (checked) parameterViewModel.updateBloodPressure(POSITIVE_RB_VALUE)
            }
            R.id.rbHighBloodPressureNegative -> {
                if (checked) parameterViewModel.updateBloodPressure(NEGATIVE_RB_VALUE)
            }
        }
    }

    fun onClickCheckSmoking(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.getId()) {
            R.id.rbSmokingPositive -> {
                if (checked) parameterViewModel.updateSmoking(POSITIVE_RB_VALUE)
            }
            R.id.rbSmokingNegative -> {
                if (checked) parameterViewModel.updateSmoking(NEGATIVE_RB_VALUE)
            }
        }
    }
}