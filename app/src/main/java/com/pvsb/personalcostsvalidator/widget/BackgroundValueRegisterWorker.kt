package com.pvsb.personalcostsvalidator.widget

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class BackgroundValueRegisterWorker(
    appContext: Context,
    params: WorkerParameters
) : Worker(appContext, params) {

    override fun doWork(): Result {

        val valueToRegister = inputData.getString(WidgetProvider.VALUE_TO_REGISTER_TAG)



        return Result.success()
    }
}