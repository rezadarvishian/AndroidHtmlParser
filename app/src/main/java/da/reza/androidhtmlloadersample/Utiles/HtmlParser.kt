package da.reza.androidhtmlloadersample.Utiles

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.util.Base64
import android.util.Patterns
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.HtmlCompat
import da.reza.androidhtmlloadersample.R
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.jvm.Throws

class HtmlParser {


    private var coroutineScope:CoroutineScope? = null

    private var textViewTarget:TextView? = null

    private var htmlText:String = ""

    @DrawableRes
    private var placeholder : Int = R.drawable.ic_launcher_foreground

    private var maxImageWidth = 500F

    private val context
    get() = textViewTarget!!.context


    // set CoroutineScope with activity or fragments lifecycle for control coroutines jobs
    fun setCoroutineScope(scope: CoroutineScope) = apply {
        this.coroutineScope = scope
    }

    fun setPlaceHolder(id:Int) = apply {
        this.placeholder = id
    }

    fun maxImageWidth(value:Float) = apply {
        this.maxImageWidth = value
    }

    fun with(html:String) = apply {
        this.htmlText = html
    }

    fun into(textView: TextView) = apply {
        this.textViewTarget = textView
    }


    @Throws
    fun parse(){


        if (coroutineScope == null) throw Exception("coroutineScope must be set")
        if (textViewTarget == null) throw Exception("textViewTarget is null")
        if (htmlText.isEmpty()) throw Exception("htmlText is Empty")


        coroutineScope!!.launch {

            var text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT)
            textViewTarget!!.text = text

            withContext(Dispatchers.IO) {

                async {
                    text = HtmlCompat.fromHtml(
                        htmlText,
                        HtmlCompat.FROM_HTML_MODE_COMPACT,
                        HtmlImageGetter(),
                        null
                    )
                }.join()

                withContext(Dispatchers.Main) {
                    textViewTarget!!.text = text
                }
            }
        }


    }

    inner class HtmlImageGetter :Html.ImageGetter{


        override fun getDrawable(p0: String?): Drawable {
            var drawable = AppCompatResources.getDrawable(
                textViewTarget!!.context,
                placeholder
            ) as Drawable

            try {

                if (p0.isNullOrBlank() || p0.length < 10) {
                    return drawable
                }


                if (p0.contains("https://")) {

                    if (!p0.isValidUrl()) {
                        return drawable
                    }

                    val url = URL(p0)
                    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    val input: InputStream = connection.inputStream
                    val bm = BitmapFactory.decodeStream(input)
                    drawable = BitmapDrawable(context.resources, bm)


                } else if (p0.contains("base64") && p0.length>200) {

                    val base64Image = p0.substringAfter(delimiter = ",")
                    val decodedString: ByteArray = Base64.decode(base64Image, Base64.DEFAULT)
                    val decodedByte =
                        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    drawable = BitmapDrawable(context.resources, decodedByte)


                }


                var width = (drawable).intrinsicWidth.toFloat()
                var height = (drawable).intrinsicHeight.toFloat()
                if (width > maxImageWidth) {
                    val scale = width / height
                    width = maxImageWidth
                    height = width / scale
                }

                drawable.setBounds(0, 0, width.toInt(), height.toInt())

                return drawable

            } catch (e: Exception) {
                drawable.setBounds(0, 0, 100, 100)
                return drawable
            }
        }
    }


    fun String.isValidUrl() = Patterns.WEB_URL.matcher(this).matches()


}