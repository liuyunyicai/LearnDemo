package com.example.nealkyliu.toolsdemo.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
//import kotlinx.android.synthetic.main.activity_lifecycle.*
//import kotlinx.android.synthetic.main.layout_button_abs.*
import java.util.*

/**
 * Created by nealkyliu on 2018/7/1.
 */
open class KotlinActivity : AppCompatActivity() , View.OnClickListener{
    companion object {
        const val TAG : String = "KotlinActivity"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        }

    }

//    private lateinit var mTagText : TextView
    private var mMap : Map<String, Unit> = HashMap<String, Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_lifecycle)

//        mTagText = findViewById(R.id.mTagText)
//        mTagText.setText(getLocale("en-us").toString())

//        mTagText.setText("")
//        mNameText.setText("")

//        findViewById<View>(R.id.mGoLifeCycle)


//        hahahahhahah.setText();
    }

    fun getLocale(language : String?) = Locale(language)
}