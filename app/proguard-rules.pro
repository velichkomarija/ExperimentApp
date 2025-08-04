# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Jetpack Compose
-keep class androidx.compose.** { *; }
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class androidx.lifecycle.LifecycleOwner { *; }

# Для LocalLifecycleOwner
-keepclassmembers class androidx.compose.runtime.CompositionLocalKt { *; }

# Сохраняем LocalLifecycleOwner в релизе
-keepclassmembers class androidx.compose.ui.platform.LocalLifecycleOwner { *; }

-keep class com.velichkomarija.everydaykit.data.todo.Task { *; }

# Убираем агрессивные оптимизации
-dontoptimize
