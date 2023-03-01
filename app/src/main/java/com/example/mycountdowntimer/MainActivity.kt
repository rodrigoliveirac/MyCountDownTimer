package com.example.mycountdowntimer

import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private var isRunning = false

    private lateinit var tvTimeValue: TextView
    private lateinit var clickToStart: Button

    private lateinit var timer: CountDownTimer

    private var timeValue: Long = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            timeValue = savedInstanceState.getLong(TIME_VALUE_KEY)
            isRunning = savedInstanceState.getBoolean(IS_RUNNING_KEY)
        }

        setContentView(R.layout.activity_main)

        clickToStart = findViewById(R.id.button)

        tvTimeValue = findViewById(R.id.tv_time)


        tvTimeValue.text = updateTimeTextView(timeValue) //* 1200000 milliseconds = 20 minutes */

        clickToStart.setOnClickListener {
            timer.start()
            isRunning = true
        }

        setupCountDownTimer()
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        if (savedInstanceState != null) {
            timeValue = savedInstanceState.getLong(TIME_VALUE_KEY)
            isRunning = savedInstanceState.getBoolean(IS_RUNNING_KEY)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putLong(TIME_VALUE_KEY, timeValue)
            putBoolean(IS_RUNNING_KEY, isRunning)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        if (isRunning) {
            timer.start()
        }
    }

    private fun setupCountDownTimer() {
        timer = object : CountDownTimer(timeValue, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                tvTimeValue.text = updateTimeTextView(millisUntilFinished)
                timeValue = millisUntilFinished
            }

            override fun onFinish() {
                tvTimeValue.text = "Done"
                isRunning = false
            }

        }
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
    }

}