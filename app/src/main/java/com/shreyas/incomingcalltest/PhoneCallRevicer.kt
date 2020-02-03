package com.shreyas.incomingcalltest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log

class PhoneCallRevicer : BroadcastReceiver() {

    private var  tm:TelephonyManager? = null

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("state_111", "sdasd:   "+intent?.getStringExtra(TelephonyManager.EXTRA_STATE))
//        val phoneState  = PhoneState(context!!)
//        tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        tm!!.listen(phoneState, PhoneStateListener.LISTEN_CALL_STATE)

        if (intent?.getStringExtra(TelephonyManager.EXTRA_STATE).equals("RINGING")) {
            FlashMsg.setFalshScreen(context)
            setCallState(context, "RINGING")
        }

        if (intent?.getStringExtra(TelephonyManager.EXTRA_STATE).equals("OFFHOOK")) {
            if (getCallState(context).equals("RINGING")) {
                FlashMsg.setlimit(context)
            }
            setCallState(context, "OFFHOOK")
        }

        if (intent?.getStringExtra(TelephonyManager.EXTRA_STATE).equals("IDLE")) {
            FlashMsg.stopRunable()
            setCallState(context, "IDLE")
//            tm!!.listen(phoneState, PhoneStateListener.LISTEN_NONE)
        }
    }

}