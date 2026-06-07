package com.moto.extremesaver.policy

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.moto.extremesaver.MainActivity

/**
 * Monitors foreground app transitions via AccessibilityService.
 * Blocks unauthorized apps by redirecting to the Extreme Saver launcher.
 *
 * The allowlist combines a hardcoded set of system-critical packages
 * with a dynamic set managed via SharedPreferences (written by AllowlistRepository).
 */
class PolicyEnforcerService : AccessibilityService() {

    companion object {
        private const val TAG = "PolicyEnforcer"
        private const val PREFS_NAME = "policy_prefs"
        private const val KEY_DYNAMIC_ALLOWLIST = "dynamic_allowlist"

        /** System-critical packages that must ALWAYS be allowed */
        private val SYSTEM_ALLOWLIST = setOf(
            "com.moto.extremesaver",
            "com.android.systemui",
            "com.android.settings",
            "com.google.android.dialer",
            "com.android.dialer",
            "com.motorola.dialer",
            "com.google.android.apps.messaging",
            "com.android.mms",
            "com.motorola.messaging",
            "com.android.phone",
            "com.android.server.telecom",
            "com.android.providers.telephony"
        )
    }

    private var cachedAllowlist: Set<String> = SYSTEM_ALLOWLIST
    private var lastBlockedPackage: String? = null
    private var lastBlockedTime: Long = 0L

    override fun onServiceConnected() {
        super.onServiceConnected()
        refreshDynamicAllowlist()
        Log.d(TAG, "PolicyEnforcerService connected. Allowlist size: ${cachedAllowlist.size}")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return

        val packageName = event.packageName?.toString() ?: return

        if (cachedAllowlist.contains(packageName)) return

        // Debounce: avoid spamming interceptor for the same app within 1 second
        val now = System.currentTimeMillis()
        if (packageName == lastBlockedPackage && now - lastBlockedTime < 1_000L) return

        lastBlockedPackage = packageName
        lastBlockedTime = now

        Log.d(TAG, "Blocked: $packageName")
        launchInterceptor()
    }

    private fun launchInterceptor() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

    private fun refreshDynamicAllowlist() {
        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val dynamicSet = prefs.getStringSet(KEY_DYNAMIC_ALLOWLIST, emptySet()) ?: emptySet()
        cachedAllowlist = SYSTEM_ALLOWLIST + dynamicSet
    }

    override fun onInterrupt() {
        // No-op
    }
}
