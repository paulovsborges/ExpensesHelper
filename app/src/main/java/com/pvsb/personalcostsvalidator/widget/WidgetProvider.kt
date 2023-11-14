package com.pvsb.personalcostsvalidator.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.pvsb.personalcostsvalidator.R

class WidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        Log.d("", "## aaaa ")

        val remoteView = RemoteViews(
            context?.packageName,
            R.layout.app_widget_main
        ).apply {
            setOnClickPendingIntent(
                R.id.llRoot,
                registerValuePendingIntent(context = context)
            )
        }
//
//        setResult(RESULT_OK, Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId))

        appWidgetIds?.forEach { id ->
            appWidgetManager?.updateAppWidget(id, remoteView)
        }
    }

    private fun registerValuePendingIntent(
        context: Context?
    ): PendingIntent {

        return PendingIntent.getActivity(
            context,
            0,
            Intent(
                context,
                FloatingRegisterValueActivity::class.java
            ),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        const val VALUE_TO_REGISTER_TAG = "VALUE_TO_REGISTER_TAG"
    }
}