package com.example.glancewidget.glance

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class StockAppWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget = StockAppWidget()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onEnabled(context: Context) {
        super.onEnabled(context)

        scope.launch {
            while (isActive) {
                PriceDataRepo.update()
                StockAppWidget().updateAll(context)
                delay(20.seconds)
            }
        }
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        scope.cancel()
    }
}