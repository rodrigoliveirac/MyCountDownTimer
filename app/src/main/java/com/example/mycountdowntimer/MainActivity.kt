package com.example.mycountdowntimer

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvTimeValue: TextView
    private lateinit var clickToStart: Button
    private lateinit var timer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        clickToStart = findViewById(R.id.button)

        tvTimeValue = findViewById(R.id.tv_time)


        tvTimeValue.text = updateTimeTextView(1200000L) /* 1200000 milliseconds = 20 minutes */

        clickToStart.setOnClickListener {
            timer.start()
        }

        setupCountDownTimer()
    }

    private fun setupCountDownTimer() {
        timer = object : CountDownTimer(1200000L, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                tvTimeValue.text = updateTimeTextView(millisUntilFinished)
            }

            override fun onFinish() {
                tvTimeValue.text = "Done"
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

}