package com.example.mycountdowntimer

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.example.mycountdowntimer.R.id.*
import com.example.mycountdowntimer.R.layout.activity_main
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var timer: CountDownTimer
    private var timeValue: Long = 0
    private var currentValueTime: Long = 0

    private var isRunning = false

    private lateinit var tvTimeValue: TextView
    private lateinit var editTextTime: EditText

    private lateinit var clickToStart: Button
    private lateinit var clickToNewTime: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CREATE", "onCreate")
        if (savedInstanceState != null) {
            timeValue = savedInstanceState.getLong(TIME_VALUE_KEY)
            isRunning = savedInstanceState.getBoolean(IS_RUNNING_KEY)
            currentValueTime = savedInstanceState.getLong(CURRENT_TIME_VALUE)
        }

        setContentView(activity_main)

        clickToStart = findViewById(button)

        tvTimeValue = findViewById(tv_time)

        clickToNewTime = findViewById(newTime_button)

        editTextTime = findViewById(editText_time)

        if (isRunning) {
            editTextTime.visibility = View.INVISIBLE
            tvTimeValue.visibility = View.VISIBLE
            clickToNewTime.visibility = View.GONE
        } else {
            editTextTime.visibility = View.VISIBLE
            tvTimeValue.visibility = View.INVISIBLE
        }

        //* 1200000 milliseconds = 20 minutes */

        clickToStart.setOnClickListener {

            timeValue = editTextTime.text.toString().toLong() * 1000L

            if (currentValueTime <= 0L) currentValueTime = timeValue else currentValueTime

            tvTimeValue.text = updateTimeTextView(currentValueTime)

            editTextTime.visibility = View.INVISIBLE
            tvTimeValue.visibility = View.VISIBLE

            if (isRunning) {
                pause()
            } else {
                start()
            }
        }

        clickToNewTime.setOnClickListener {
            editTextTime.visibility = View.VISIBLE
            tvTimeValue.visibility = View.INVISIBLE
            clickToStart.text = "START"
        }

    }

    private fun start() {
        setupTimer()
        isRunning = true
        clickToStart.text = "PAUSE"
    }

    private fun setupTimer() {
        timer = object : CountDownTimer(currentValueTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentValueTime = millisUntilFinished
                tvTimeValue.text = updateTimeTextView(currentValueTime)
            }

            override fun onFinish() {
                tvTimeValue.text = "Done"
                isRunning = false
                currentValueTime = 0
                clickToStart.text = "START AGAIN"
                clickToNewTime.visibility = View.VISIBLE
            }

        }.start()
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