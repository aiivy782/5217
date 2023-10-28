package com.aiivy782.pomodoro

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.aiivy782.pomodoro.databinding.ActivityMainBinding
import java.util.Date
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var dataHelper: DataHelper

    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataHelper = DataHelper(applicationContext)

        binding.startPauseButton.setOnClickListener{ startStopAction() }
        binding.resetButton.setOnClickListener{ resetAction() }

        if (dataHelper.timerCounting()) {
            startTimer()
        }
        else {
            stopTimer()
            if (dataHelper.startTime() != null && dataHelper.stopTime() != null) {
                val time = Date().time - calcRestartTime().time
                binding.timerTV.text = timeStringFromLong(time)
            }
        }

        timer.scheduleAtFixedRate(TimeTask(), 0, 500)

    }

    private inner class TimeTask: TimerTask() {
        override fun run() {
            if (dataHelper.timerCounting()) {
                val time = Date().time - dataHelper.startTime()!!.time
                binding.timerTV.text = timeStringFromLong(time)
            }
        }
    }

    private fun startStopAction() {
        if (dataHelper.timerCounting()) {
            dataHelper.setStopTime(Date())
            stopTimer()
        }
        else {
            if (dataHelper.stopTime() != null) {
                dataHelper.setStartTime(calcRestartTime())
                dataHelper.setStopTime(null)
            }
            else {
                dataHelper.setStartTime(Date())
            }
            startTimer()
        }
    }

    private fun calcRestartTime(): Date {
        val diff = dataHelper.startTime()!!.time - dataHelper.stopTime()!!.time
        return Date(System.currentTimeMillis() + diff)
    }

    private fun stopTimer() {
        dataHelper.setTimerCounting(false)
        binding.startPauseButton.text = getString(R.string.start)
    }

    private fun startTimer() {
        dataHelper.setTimerCounting(true)
        binding.startPauseButton.text = getString(R.string.pause)
    }

    private fun resetAction() {
        dataHelper.setStopTime(null)
        dataHelper.setStartTime(null)
        stopTimer()
        binding.timerTV.text = timeStringFromLong(0)
    }

    private fun timeStringFromLong(ms: Long): String {
        val seconds = (ms / 1000) % 60
        val minutes = (ms / (1000 * 60) % 60)
        return makeTimeString(minutes, seconds)
    }

    private fun makeTimeString(minutes: Long, seconds: Long): String {
        return String.format("%02d:%02d", minutes, seconds)
    }
}