package com.moto.extremesaver.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.moto.extremesaver.MainActivity

/**
 * Re-launches the Extreme Saver launcher on device boot
 * if the mode was previously active.
 * 
 * The actual mode state check happens in MainActivity via the ViewModel,
 * which reads persisted state from Room.
 */
class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Boot completed. Relaunching Extreme Saver.")
            val launchIntent = Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(launchIntent)
        }
    }
}
