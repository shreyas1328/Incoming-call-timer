package com.shreyas.incomingcalltest

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

class FlashMsg {

    companion object {

            var handler:Handler? = null
            var runnable: Runnable? = null

        public fun setFalshScreen(context: Context) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val ll = LinearLayout(context.applicationContext)
            ll.setBackgroundColor(Color.WHITE)
            ll.orientation = LinearLayout.HORIZONTAL
            val myView = inflater.inflate(R.layout.flash_window, ll,false)
            setData(myView, wm)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val params = WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
                )
                params.gravity = Gravity.CENTER or Gravity.CENTER
                wm.addView(myView, params)

            } else {
                val params = WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                    PixelFormat.TRANSLUCENT
                )
                params.gravity = Gravity.CENTER or Gravity.CENTER
                wm.addView(myView, params)
            }
        }

        private fun setData(myView: View?, wm: WindowManager) {
            var title = myView?.findViewById<TextView>(R.id.tv_title)
            val des = myView?.findViewById<TextView>(R.id.tv_des)
            val mBtnOk = myView?.findViewById<TextView>(R.id.btn_ok)

            des?.setText("Hi this is flash message")

            mBtnOk?.setOnClickListener(View.OnClickListener {
                wm.removeView(myView)
            })
        }

        public fun setlimit(context: Context) {
            handler = Handler()
            runnable = Runnable {
                setActions(context)
                handler!!.postDelayed(runnable!!, 5000)
            }
            Log.d("test_333","delay: ${getTime(context)}")
            handler!!.postDelayed(runnable!!, getTime(context))

        }

        fun stopRunable() {
            try {
                handler!!.removeCallbacks(runnable)
            }catch (e:Exception) {
                Log.d("state_111","Exception: ${e.message}")
            }
        }

        private fun setActions(context: Context) {
            var vibrate = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrate.vibrate(VibrationEffect.createOneShot(500, 1))
            } else {
                vibrate.vibrate(500)
            }
        }




    }
}