package com.example.nealkyliu.toolsdemo.kotlin

import android.view.View
import com.example.nealkyliu.toolsdemo.R.id.mGoLifeCycle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Created by nealkyliu on 2018/7/1.
 */
open class ClassA {



    class Delegate {
//        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
//            return "$thisRef, thank you for delegating '${property.name}' to me!"
//        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            println("$value has been assigned to '${property.name}' in $thisRef.")
        }
    }


    private var _table: Map<String, Int>? = null
    val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap()
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }

    open fun getUnit() = "string"


    internal companion object {
        fun getStaticMethod(): String = "jajjaj"
    }

    fun aaaa() {
//        mGoLifeCycle.setText("")
    }
}
