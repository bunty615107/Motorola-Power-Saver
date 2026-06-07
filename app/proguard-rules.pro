# Proguard rules for Extreme Saver Mode

# Keep Room entities
-keep class com.moto.extremesaver.data.entity.** { *; }

# Keep Hilt generated code
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep Kotlin metadata
-keepattributes *Annotation*
-keep class kotlin.Metadata { *; }
