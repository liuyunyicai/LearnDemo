import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.nealkyliu.toolsdemo.R
import kotlinx.android.synthetic.main.activity_lifecycle.*
import java.util.*

/**
 * Created by nealkyliu on 2018/7/1.
 */
abstract class KotlinActivity1 : AppCompatActivity() , View.OnClickListener{
    companion object {
        const val TAG : String = "KotlinActivity"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        }

        KotlinTest.hello()
    }

//    private lateinit var mTagText : TextView
    private var mMap : Map<String, Unit> = HashMap<String, Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

//        mTagText = findViewById(R.id.mTagText)
//        mTagText.setText(getLocale("en-us").toString())

        mTagText.setText(R.string.tag_name)
//        mNameText.setText("")
//        mNameText1111.setText("")

//        findViewById<View>(R.id.mGoLifeCycle)


//        hahahahhahah.setText();
    }

    fun getLocale(language : String?) = Locale(language)
}