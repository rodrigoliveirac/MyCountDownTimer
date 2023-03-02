package com.example.mycountdowntimer

import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import java.util.*


data class Timer(var time: Long)
class MainActivity : AppCompatActivity() {

    private var currentValueTime: Long = 0L

    private var isRunning = false

    private lateinit var tvTimeValue: TextView
    private lateinit var clickToStart: Button

    private lateinit var timer: CountDownTimer

    private var timeValue: Long = 120000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("CREATE", "onCreate")

        if (savedInstanceState != null) {
            timeValue = savedInstanceState.getLong(TIME_VALUE_KEY)
            isRunning = savedInstanceState.getBoolean(IS_RUNNING_KEY)
            currentValueTime = savedInstanceState.getLong(CURRENT_TIME_VALUE)
        }

        setContentView(R.layout.activity_main)

        clickToStart = findViewById(R.id.button)

        tvTimeValue = findViewById(R.id.tv_time)

        tvTimeValue.text = updateTimeTextView(timeValue) //* 1200000 milliseconds = 20 minutes */

        if (currentValueTime <= 0L) currentValueTime = timeValue else currentValueTime

        clickToStart.setOnClickListener {
            if (isRunning) {
                pause()
            } else {
                start()
            }
        }

    }

    private fun start() {
        Log.i("currentTime", currentValueTime.toString())
        timer = object : CountDownTimer(currentValueTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentValueTime = millisUntilFinished
                tvTimeValue.text = updateTimeTextView(currentValueTime)
            }

            override fun onFinish() {
                tvTimeValue.text = "Done"
                isRunning = false
                currentValueTime = 0L
            }

        }.start()

        isRunning = true
        clickToStart.text = "PAUSE"
    }

    private fun pause() {
        timer.cancel()
        isRunning = false
        clickToStart.text = "START"
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {

        if (savedInstanceState != null) {
            timeValue = savedInstanceState.getLong(TIME_VALUE_KEY)
            currentValueTime = savedInstanceState.getLong(CURRENT_TIME_VALUE)
            isRunning = savedInstanceState.getBoolean(IS_RUNNING_KEY)
            tvTimeValue.text = savedInstanceState.getString(TV_TIME)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putLong(TIME_VALUE_KEY, timeValue)
            putLong(CURRENT_TIME_VALUE, currentValueTime)
            putBoolean(IS_RUNNING_KEY, isRunning)
            putString(TV_TIME, tvTimeValue.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        if (isRunning) {
            start()
        }
        Log.d("START", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("PAUSE", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("STOP", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DESTROY", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("RESTART", "onRestart")
        Log.i("time", timeValue.toString())
        Log.i("currentTime", currentValueTime.toString())
    }

    private fun setupCountDownTimer() {

    }

    private fun updateTimeTextView(ms: Long): String {
        val hour = (ms / 1000) / 3600
        val minute = (ms / 1000 / 60) % 60
        val seconds = (ms / 1000) % 60

        return java.lang.String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d",
            hour,
            minute,
            seconds
        )
    }

    companion object {
        private const val TIME_VALUE_KEY = "time_value_key"
        private const val IS_RUNNING_KEY = "IS_RUNNING_KEY"
        private const val TV_TIME = "TV_TIME"
        private const val CURRENT_TIME_VALUE = "CURRENT_TIME_VALUE"
    }

}