package com.shreyas.incomingcalltest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

private var PREF_NAME: String = "MYAPP"
private var PREF_MODE: Int = 0

private var ISCALL: String = "ISCALL"
private var CALLSTATE: String = "CALLSTATE"
private var TIMELIMIT: String = "TIMELimit"

fun setIsCall(context: Context, isCall: Boolean) {
    var prefs = context.getSharedPreferences(PREF_NAME, PREF_MODE)
    var editor = prefs.edit()
    editor.putBoolean(ISCALL, isCall)
    editor.apply()
}

fun getIsCall(context: Context): Boolean {
    val prefs = context.getSharedPreferences(PREF_NAME, PREF_MODE)
    return prefs.getBoolean(ISCALL, false)
}

fun setCallState(context: Context, isCall: String) {
    var prefs = context.getSharedPreferences(PREF_NAME, PREF_MODE)
    var editor = prefs.edit()
    editor.putString(CALLSTATE, isCall)
    editor.apply()
}

fun getCallState(context: Context): String {
    val prefs = context.getSharedPreferences(PREF_NAME, PREF_MODE)
    return prefs.getString(CALLSTATE, "IDLE")!!
}

fun setTime(context: Context, timeLimit: Long) {
    val prefs = context.getSharedPreferences(PREF_NAME, PREF_MODE)
    val editor = prefs.edit()
    editor.putLong(TIMELIMIT, timeLimit)
    editor.apply()
}

fun getTime(context: Context): Long {
    var prefs = context.getSharedPreferences(PREF_NAME, PREF_MODE)
    return prefs.getLong(TIMELIMIT, 5000)
}

fun enableReciver(context: Context) {
    val receiver = ComponentName(context, PhoneCallRevicer::class.java)

    context.packageManager.setComponentEnabledSetting(
        receiver,
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )
}


fun disableReciver(context: Context) {
    val receiver = ComponentName(context, PhoneCallRevicer::class.java)

    context.packageManager.setComponentEnabledSetting(
        receiver,
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        PackageManager.DONT_KILL_APP
    )
}

private val POWERMANAGER_INTENTS = arrayOf(
    Intent().setComponent(
        ComponentName(
            "com.miui.securitycenter",
            "com.miui.permcenter.autostart.AutoStartManagementActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.letv.android.letvsafe",
            "com.letv.android.letvsafe.AutobootManageActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.huawei.systemmanager",
            "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.huawei.systemmanager",
            "com.huawei.systemmanager.optimize.process.ProtectActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.huawei.systemmanager",
            "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.coloros.safecenter",
            "com.coloros.safecenter.permission.startup.StartupAppListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.coloros.safecenter",
            "com.coloros.safecenter.startupapp.StartupAppListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.oppo.safe",
            "com.oppo.safe.permission.startup.StartupAppListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.iqoo.secure",
            "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.iqoo.secure",
            "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.vivo.permissionmanager",
            "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.samsung.android.lool",
            "com.samsung.android.sm.ui.battery.BatteryActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.htc.pitroad",
            "com.htc.pitroad.landingpage.activity.LandingPageActivity"
        )
    ),
    Intent().setComponent(
        ComponentName(
            "com.asus.mobilemanager",
            "com.asus.mobilemanager.MainActivity"
        )
    )
)


fun autoStart(context: Context) {
    for (intent in POWERMANAGER_INTENTS) if (context.getPackageManager().resolveActivity(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        ) != null
    ) {
        context.startActivity(intent)
        break
    }
}

