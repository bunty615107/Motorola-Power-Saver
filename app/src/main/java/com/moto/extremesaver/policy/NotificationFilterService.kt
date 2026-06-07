package com.moto.extremesaver.policy

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

/**
 * Suppresses notifications from non-allowlisted apps while Extreme Saver is active.
 * Calls and SMS notifications are always allowed through.
 */
class NotificationFilterService : NotificationListenerService() {

    private val alwaysAllowedPackages = setOf(
        "com.moto.extremesaver",
        "com.android.dialer",
        "com.google.android.dialer",
        "com.motorola.dialer",
        "com.android.mms",
        "com.google.android.apps.messaging",
        "com.motorola.messaging",
        "com.android.systemui",
        "com.android.phone",
        "com.android.server.telecom"
    )

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null) return

        val packageName = sbn.packageName

        if (!alwaysAllowedPackages.contains(packageName)) {
            Log.d("NotificationFilter", "Suppressing notification from: $packageName")
            cancelNotification(sbn.key)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // No-op
    }
}
