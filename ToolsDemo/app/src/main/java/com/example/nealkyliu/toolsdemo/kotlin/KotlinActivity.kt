package com.example.nealkyliu.toolsdemo.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.nealkyliu.toolsdemo.R
import com.example.nealkyliu.toolsdemo.utils.Logger
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
//import kotlinx.android.synthetic.main.activity_lifecycle.*
//import kotlinx.android.synthetic.main.layout_button_abs.*
import java.util.*
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by nealkyliu on 2018/7/1.
 */
open class KotlinActivity : AppCompatActivity(), View.OnClickListener {
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
        setContentView(R.layout.layout_name_fragment1)

        launch(UI) {
            var job1 = getAsyncJob("Job1")
            var job2 = getAsyncJob("job2")
            var job3 = getAsyncJob2(tag = "job3", parent = job2)

            Logger.d("UI step0")
//            job3.await()
            Logger.d("UI step1")
//            job1.await()
            Logger.d("UI step2")
//            job2.await()
            Logger.d("UI step3")


        }

        launch(UI) {
            var job4 = getAsyncJob("Job4")
            Logger.d("UI2 step0")
            job4.await()
            Logger.d("UI2 step1")
        }

        getLocale(null) {

        }



        val items = mutableListOf<String>()
        items?.let { it.add("")
            it.size
        }

        val apply = items.apply {
            add("")
        }

        items.run {
            add("")
        }

        val with = with(items) {
            add("")
            "a"
        }

        with

//        setContentView(R.layout.activity_lifecycle)

//        mTagText = findViewById(R.id.mTagText)
//        mTagText.setText(getLocale("en-us").toString())

//        mTagText.setText("")
//        mNameText.setText("")

//        findViewById<View>(R.id.mGoLifeCycle)


//        hahahahhahah.setText();
    }

    private fun getAsyncJob(tag : String) = async() {
        Logger.d("async Job $tag start")
        Thread.sleep(1000)
        Logger.d("async Job $tag End")
    }

    private fun getAsyncJob2(tag : String, parent : Job ?= null) = async(parent = parent) {
        Logger.d("async Job $tag start")
        Thread.sleep(5000)
        Logger.d("async Job $tag End")
    }

    fun getLocale(language: String?, block: suspend CoroutineScope.() -> Unit) = {


    }

    public val key: Key<*> ?= null
    interface Key<e : String>
}