package com.example.jobschedulersample1

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ExamplService : JobService() {
    val TAG = "ExamplService"
    var isJobCancelled = false
    var thread: Thread? = null

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob called -> " + Gson().toJson(params))
        Toast.makeText(this, "Stop Job ", Toast.LENGTH_SHORT).show()
//        thread?.interrupt()
        isJobCancelled = true
        return true
    }

    private fun doBackgroundWork(params: JobParameters?) {
        thread = Thread {
            for (i in 0..10000) {
                Log.d(
                    TAG,
                    "doBackgroundWork -> $i" + ", " + thread?.isInterrupted + ", isJobCancelled -> " + isJobCancelled
                )
//                Toast.makeText(this, "doBackgroundWork -> $i", Toast.LENGTH_SHORT).show()
                if (isJobCancelled) {
                    thread?.interrupt()
                }
                try {
                    Thread.sleep(2000)
                } catch (e: Exception) {
//                    e.printStackTrace()
                }
            }
            if (!isJobCancelled) {
                Log.d(TAG, "doBackgroundWork -> Job finished")
                jobFinished(params, true)
            }
        }
        thread?.start()
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob called -> " + params)
        Toast.makeText(this, "Start Job ", Toast.LENGTH_SHORT).show()
        doBackgroundWork(params)

        return true
    }
}