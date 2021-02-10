package com.app.msgalarmclock.presentation.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.msgalarmclock.App
import com.app.msgalarmclock.R
import com.app.msgalarmclock.presentation.base.BaseActivity
import com.app.msgalarmclock.presentation.ui.main.alarmlist.AlarmListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layoutId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent?.inject(this)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        checkIfPermissionsGranted()
        letAppRunInBackground()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            val allGranted = grantResults.all {
                it == PackageManager.PERMISSION_GRANTED
            }
            if (allGranted) {
                showAlarmList()
            } else {
                finish()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("BatteryLife")
    private fun letAppRunInBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((getSystemService(Context.POWER_SERVICE) as? PowerManager)
                    ?.isIgnoringBatteryOptimizations(packageName) == false
            ) {
                startActivity(Intent().apply {
                    action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                    flags = FLAG_ACTIVITY_NEW_TASK
                    data = Uri.parse("package:$packageName")
                })
            }
        }
    }

    private fun checkIfPermissionsGranted() {
        val allGranted = PERMISSIONS.all {
            (ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED)
        }
        if (allGranted) {
            showAlarmList()
        } else {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

    }

    private fun showAlarmList() {
        navigateTo(AlarmListFragment.getInstance(), false)
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 0
        private val PERMISSIONS = arrayOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_CONTACTS
        )
    }
}