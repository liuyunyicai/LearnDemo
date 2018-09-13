package com.example.nealkyliu.toolsdemo.base

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/13
 */
object TestLock {

    private val NANOS_PER_MILLI = 1000 * 1000
    private val NANOS_PER_SECOND = NANOS_PER_MILLI * 1000
    private val MAX_FINALIZE_NANOS = 10L * NANOS_PER_SECOND
}
