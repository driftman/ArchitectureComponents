package com.driftman.fuckingandroid.repository

/**
 * Created by abk on 15/07/2018.
 */
data class SynchronizationResult (
        val serviceName: String,
        var status: String = "waiting ..."
) {}