package com.moto.extremesaver.security

import android.content.Context
import android.hardware.biometrics.BiometricPrompt
import android.os.CancellationSignal

class AuthManager(private val context: Context) {
    fun authenticate(onSuccess: () -> Unit, onError: () -> Unit) {
        val builder = BiometricPrompt.Builder(context)
            .setTitle("Exit Extreme Saver Mode")
            .setSubtitle("Authenticate to restore standard mode")
            .setDeviceCredentialAllowed(true) // Allows standard PIN/Pattern fallback

        val prompt = builder.build()
        val executor = context.mainExecutor

        prompt.authenticate(CancellationSignal(), executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode != BiometricPrompt.BIOMETRIC_ERROR_USER_CANCELED) {
                    onError()
                }
            }
        })
    }
}
