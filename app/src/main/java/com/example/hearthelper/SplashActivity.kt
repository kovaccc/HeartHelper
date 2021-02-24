package com.example.hearthelper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val TAG = "SplashActivity"
private const val COROUTINE_STATE = "Coroutine State"

class SplashActivity : AppCompatActivity() {

    private var coroutineState:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: starts")


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        if(savedInstanceState != null) { // checking if rotation happened to prevent creating next activity few times if it gets in coroutine

            coroutineState = savedInstanceState.getBoolean(COROUTINE_STATE)

            Log.d(TAG, "onCreate: value of coroutine state is $coroutineState ")
        }


        if(!coroutineState) {
            CoroutineScope(Main).launch {

                coroutineState = true
                delay(3_000)
                startActivity(MainActivity())

            }
        }
        Log.d(TAG, "onCreate: ends")

    }


    private fun startActivity(activity: Activity)
    {
        Log.d(TAG, "startActivity: starting activity $activity")
        val intent = Intent(this@SplashActivity, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }



    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.d(TAG, "onSaveInstanceState starts")
        super.onSaveInstanceState(outState, outPersistentState)
        Log.d(TAG, "onSaveInstanceState ends")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(COROUTINE_STATE, coroutineState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(TAG, "onRestoreInstanceState starts")
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState ends")
    }

}