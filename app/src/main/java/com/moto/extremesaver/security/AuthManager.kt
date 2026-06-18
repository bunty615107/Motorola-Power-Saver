package com.moto.extremesaver.security

import android.content.Context
import android.os.Build
import android.hardware.biometrics.BiometricPrompt
import android.hardware.biometrics.BiometricManager.Authenticators
import android.os.CancellationSignal

class AuthManager(private val context: Context) {
    fun authenticate(onSuccess: () -> Unit, onError: () -> Unit) {
        val builder = BiometricPrompt.Builder(context)
            .setTitle("Exit Extreme Saver Mode")
            .setSubtitle("Authenticate to restore standard mode")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            builder.setAllowedAuthenticators(Authenticators.BIOMETRIC_WEAK or Authenticators.DEVICE_CREDENTIAL)
        } else {
            @Suppress("DEPRECATION")
            builder.setDeviceCredentialAllowed(true)
        }

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
