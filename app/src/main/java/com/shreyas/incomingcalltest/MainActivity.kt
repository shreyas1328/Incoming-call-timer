package com.shreyas.incomingcalltest

import android.Manifest.permission.*
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var br: PhoneCallRevicer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPerm()

        et_time.setText((getTime(this)/1000).toString())

        btn_scren_opts.setOnClickListener(this)

        Log.d("state_123", "hbh")

        btn_start.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "App is running", Toast.LENGTH_SHORT).show()
            val time = (et_time.text.toString().toLong()*1000)
            setTime(this, time)
            enableReciver(this)
        })

        btn_stop.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "App is stopped", Toast.LENGTH_SHORT).show()
            disableReciver(this)
            try {
                FlashMsg.stopRunable()
            }catch (e:Exception) {
                Log.d("state_1234", "runable: ${e.message}")
            }
        })

        btn_auto_start.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, android.os.Build.MANUFACTURER, Toast.LENGTH_SHORT).show()
            autoStart(this)
        })

    }

    private fun checkPerm() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                READ_PHONE_STATE,
                VIBRATE,
                WAKE_LOCK,
                SYSTEM_ALERT_WINDOW,
                RECEIVE_BOOT_COMPLETED
            ),
            1
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        initPhoneListner()
                } else {
                    checkPerm()
                }
            }
        }
    }

    private fun initPhoneListner() {
        br = PhoneCallRevicer()
        val filter = IntentFilter()
        filter.addAction("android.intent.action.PHONE_STATE")
        registerReceiver(br, filter)

    }

    private fun batteryOpts() {
        if (Build.VERSION.SDK_INT >= 23) {
            val intent = Intent()
            intent.setAction(Settings.ACTION_BATTERY_SAVER_SETTINGS)
            startActivityForResult(intent, 2345)
//            Toast.makeText(this, "Enable draw over other apps", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this, "Your phone doesnt requires it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun screenOverlay() {
        if (Build.VERSION.SDK_INT >= 23) {
//            Toast.makeText(applicationContext, "Disable for ${applicationContext.packageName}", Toast.LENGTH_LONG).show()
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivityForResult(intent, 1234)

        }else {
            Toast.makeText(this, "Your phone doesnt requires it", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {


            R.id.btn_scren_opts -> {
                screenOverlay()
            }
        }
    }

//    fun checkBatteryOptimized(): Boolean {
//        val pwrm = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
//        val name = applicationContext.packageName
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            return !pwrm.isIgnoringBatteryOptimizations(name)
//        }
//        return false
//    }
//
//    fun checkBattery() {
//        if (checkBatteryOptimized()) {
//            val name = resources.getString(R.string.app_name)
//            val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
//            startActivity(intent)
//        }
//    }
}
