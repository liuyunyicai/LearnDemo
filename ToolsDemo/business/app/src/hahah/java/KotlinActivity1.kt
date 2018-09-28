import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.nealkyliu.toolsdemo.R
import com.example.nealkyliu.toolsdemo.utils.DebugLogger
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
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
        val async1 = async {
            DebugLogger.dt("KotlinActivity1 Job1")
        }

        val async2 = async {
            DebugLogger.dt("KotlinActivity1 Job2")
        }

        launch(UI) {
            DebugLogger.dt("KotlinActivity1 Job3")
            async1.await()
            DebugLogger.dt("KotlinActivity1 Job4")
            async2.await()
            DebugLogger.dt("KotlinActivity1 Job5")
        }
    }

    fun getLocale(language : String?) = Locale(language)
}