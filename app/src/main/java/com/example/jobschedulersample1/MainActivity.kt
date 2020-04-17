package com.example.jobschedulersample1

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun scheduleJob(view: View) {
        Log.d(TAG, "scheduleJob called")
        val componentName = ComponentName(this, ExamplService::class.java)
        val jobInfo =
            JobInfo.Builder(123, componentName).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(true)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
//                .setOverrideDeadline(0)
                .build()
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val request = scheduler.schedule(jobInfo)
        if (request == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled successfully.")
        } else {
            Log.d(TAG, "Job not scheduled.")
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun cancelJob(view: View) {
        Log.d(TAG, "cancelJob called")
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
//        scheduler.cancel(123)
        scheduler.cancelAll()
        Log.d(TAG, "Job cancelled.")
    }
}
