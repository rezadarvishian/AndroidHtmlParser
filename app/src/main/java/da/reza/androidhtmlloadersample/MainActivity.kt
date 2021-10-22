package da.reza.androidhtmlloadersample

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import da.reza.androidhtmlloadersample.Utiles.HtmlParser
import da.reza.androidhtmlloadersample.databinding.MainActivityBinding
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {


    private lateinit var binding: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        HtmlParser()
            .setCoroutineScope(lifecycleScope)
            .maxImageWidth(getScreenWidth())
            .setPlaceHolder(R.drawable.ic_launcher_foreground)
            .with(loadHtmlFromResource())
            .into(binding.htmlTextView)
            .parse()
    }

    //get displayWidth for set max of an image should be
    private fun getScreenWidth() = (Resources.getSystem().displayMetrics.widthPixels - 50).toFloat()


    //load sample html text with image link from assets
    private fun loadHtmlFromResource(): String {
        var tContents = ""
        try {
            val stream: InputStream = assets.open("sample_html.txt")
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            tContents = String(buffer)
        } catch (e: IOException) {
            Toast.makeText(this, "error to open file", Toast.LENGTH_SHORT).show()
            Log.i("htmlProvider" , e.message.toString())
        }
        return tContents
    }


}