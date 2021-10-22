package da.reza.androidhtmlloadersample.Utiles

import android.app.Activity
import android.content.res.Resources
import android.util.Patterns




fun String.isValidUrl() = Patterns.WEB_URL.matcher(this).matches()

fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels
