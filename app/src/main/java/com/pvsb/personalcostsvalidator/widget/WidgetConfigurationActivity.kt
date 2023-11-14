package com.pvsb.personalcostsvalidator.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.activity.ComponentActivity
import com.pvsb.personalcostsvalidator.R

class WidgetConfigurationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        Log.d("", "## widget id $appWidgetId")

        val appWidgetManager = AppWidgetManager.getInstance(this)

        val views = RemoteViews(packageName, R.layout.app_widget_main)
        appWidgetManager.updateAppWidget(appWidgetId, views)

        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }
}