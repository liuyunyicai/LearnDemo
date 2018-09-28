package com.example.nealkyliu.toolsdemo.kotlin

//import kotlinx.android.synthetic.main.layout_button_abs.*
import android.os.Bundle
import android.view.View
import com.example.nealkyliu.toolsdemo.R
import com.example.nealkyliu.toolsdemo.livedata.activity.AbsActivity
import com.example.nealkyliu.toolsdemo.utils.DebugLogger
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import java.util.*

/**
 * Created by nealkyliu on 2018/7/1.
 */
open class KotlinActivity : AbsActivity(), View.OnClickListener {
    override fun initView() {
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_name_fragment1
    }

    override fun initData() {
    }

    companion object {
        const val TAG: String = "KotlinActivity"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        }

    }

    //    private lateinit var mTagText : TextView
    private var mMap: Map<String, Unit> = HashMap<String, Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.layout_name_fragment1)

//        launch(UI) {
//            var job1 = getAsyncJob("Job1")
//            var job2 = getAsyncJob("job2")
//            var job3 = getAsyncJob2(tag = "job3", parent = job2)
//
//            DebugLogger.dt("UI step0")
//            job1.await()
//            DebugLogger.dt("UI step1")
//            job2.await()
//            DebugLogger.dt("UI step2")
//            job3.await()
//            DebugLogger.dt("UI step3")
//
//
//        }
//
//        launch(UI) {
//            var job4 = getAsyncJob("Job4")
//            DebugLogger.dt("UI2 step0")
//            job4.await()
//            DebugLogger.dt("UI2 step1")
//        }
//
//        getLocale(null) {
//
//        }
//
//
//
//        val items = mutableListOf<String>()
//        items.let { it.add("")
//            it.size
//        }
//
//        val apply = items.apply {
//            add("")
//        }
//
//        items.run {
//            add("")
//        }
//
//        val with = with(items) {
//            add("")
//            "a"
//        }

//        setContentView(R.layout.activity_lifecycle)

//        mTagText.setText(getLocale("en-us").toString())

//        mTagText.setText("")
//        mNameText.setText("")

//        findViewById<View>(R.id.mGoLifeCycle)


//        hahahahhahah.setText();
    }

    private fun getAsyncJob(tag : String) = async() {
        DebugLogger.dt("async Job $tag start")
        Thread.sleep(1000)
        DebugLogger.dt("async Job $tag End")
    }

    private fun getAsyncJob2(tag : String, parent : Job ?= null) = async(parent = parent) {
        DebugLogger.dt("async Job $tag start")
        Thread.sleep(5000)
        DebugLogger.dt("async Job $tag End")
    }

    fun getLocale(language: String?, block: suspend CoroutineScope.() -> Unit) = {


    }

    public val key: Key<*> ?= null
    interface Key<e : String>
}