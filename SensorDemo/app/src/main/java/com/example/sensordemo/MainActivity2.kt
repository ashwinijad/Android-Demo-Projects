package com.example.sensordemo

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast


class MainActivity2 : Activity() {
    var sm: SensorManager? = null
    var textView1: TextView? = null
    var list: List<*>? = null
    var sel: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent) {
            val values = event.values
            textView1!!.text = """
                x: ${values[0]}
                y: ${values[1]}
                z: ${values[2]}
                """.trimIndent()
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        /* Get a SensorManager instance */sm = getSystemService(SENSOR_SERVICE) as SensorManager
        textView1 = findViewById<View>(R.id.textView1) as TextView
        list = sm!!.getSensorList(Sensor.TYPE_ACCELEROMETER)
        if (list?.size!! > 0) {
            sm!!.registerListener(sel, list?.get(0) as Sensor, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            Toast.makeText(baseContext, "Error: No Accelerometer.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStop() {
        if (list!!.size > 0) {
            sm!!.unregisterListener(sel)
        }
        super.onStop()
    }
}