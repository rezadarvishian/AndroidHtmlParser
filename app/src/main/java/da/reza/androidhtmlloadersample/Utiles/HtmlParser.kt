package da.reza.androidhtmlloadersample.Utiles

import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import androidx.annotation.DrawableRes
import kotlinx.coroutines.CoroutineScope
import kotlin.jvm.Throws

class HtmlParser {


    private var coroutineScope:CoroutineScope? = null

    private var textViewTarget:TextView? = null

    private var htmlText:String? = null

    @DrawableRes
    private var placeholder : Int? = null

    private var maxImageWidth = 500


    // set CoroutineScope with activity or fragments lifecycle for control coroutines jobs
    fun setCoroutineScope(scope: CoroutineScope) = apply {
        this.coroutineScope = scope
    }

    fun setPlaceHolder(id:Int) = apply {
        this.placeholder = id
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
        if (htmlText == null) throw Exception("htmlText is null")
        if (placeholder == null) throw Exception("placeholder is necessary")



    }

    inner class HtmlImageGetter :Html.ImageGetter{



        override fun getDrawable(p0: String?): Drawable {
            //TODO
        }

    }

}