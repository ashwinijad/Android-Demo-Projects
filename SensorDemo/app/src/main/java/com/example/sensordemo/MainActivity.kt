package com.example.sensordemo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), SensorEventListener {
    private var textView: TextView? = null
    private var textViewse: TextView? = null
    private var sensorManager: SensorManager? = null
    private var accelerometerSensor: Sensor? = null
    private var proximitySensor: Sensor? = null
    private var lightSensor: Sensor? = null
    private var stepCounterSensor: Sensor? = null
    private var tempSensor: Sensor? = null
    private var gyroscopeSensor: Sensor? = null
    private var currentSensor = 0
    private var lastUpdate: Long = 0
    private var last_x = 0f
    private var last_y = 0f
    private var last_z = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.tvResult)
        textViewse = findViewById(R.id.tvsense)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        proximitySensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        lightSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        stepCounterSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        gyroscopeSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        tempSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        val sensorList = sensorManager!!.getSensorList(Sensor.TYPE_ALL)
        var sensorInfo = ""
        for (s in sensorList) {
            sensorInfo = """
        $sensorInfo${s.name}
        
        """.trimIndent()
        }
        textViewse!!.text = "Steps : $sensorInfo"


    }

    fun checkSensorAvailability(sensorType: Int): Boolean {
        var isSensor = false
        if (sensorManager!!.getDefaultSensor(sensorType) != null) {
            isSensor = true
        }
        return isSensor
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == currentSensor) {
            if (currentSensor == Sensor.TYPE_LIGHT) {
                val valueZ = event.values[0]
                textView!!.text = "Brightness $valueZ"
            } else if (currentSensor == Sensor.TYPE_PROXIMITY) {
                val distance = event.values[0]
                textView!!.text = "Proximity $distance"
            } else if (currentSensor == Sensor.TYPE_STEP_DETECTOR) {
                val steps = event.values[0]
                textView!!.text = "Steps : $steps"
            } else if (currentSensor == Sensor.TYPE_ACCELEROMETER) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val curTime = System.currentTimeMillis()
                if (curTime - lastUpdate > 100) {
                    val diffTime = curTime - lastUpdate
                    lastUpdate = curTime
                    val speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000
                    if (speed > SHAKE_THRESHOLD) {
                        Toast.makeText(applicationContext, "Your phone just shook", Toast.LENGTH_LONG).show()
                    }
                    last_x = x
                    last_y = y
                    last_z = z
                    textView!!.text = """
                x: ${x}
                y: ${y}
                z: ${z}
                """.trimIndent()
                }
            } else if (currentSensor == Sensor.TYPE_GYROSCOPE) {
                if (event.values[2] > 0.5f) {
                    textView!!.text = "Anti Clock"
                } else if (event.values[2] < -0.5f) {
                    textView!!.text = "Clock"
                }
            } else if (currentSensor == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                textView!!.text = "Ambient Temp in Celsius :" + event.values[0]
            }
        }
    }

    override fun onAccuracyChanged(sensor3: Sensor?, accuracy: Int) {}
    fun accelerometerSensorOnClick(view: View?) {
        if (checkSensorAvailability(Sensor.TYPE_ACCELEROMETER)) {
            currentSensor = Sensor.TYPE_ACCELEROMETER
        }
        textView!!.text = "Accelerometer not available"
    }

    fun proximitySensorOnClick(view: View?) {
        if (checkSensorAvailability(Sensor.TYPE_PROXIMITY)) {
            currentSensor = Sensor.TYPE_PROXIMITY
        }
        textView!!.text = "Proximity Sensor not available"
    }

    fun gyroscopeSensorOnClick(view: View?) {
        if (checkSensorAvailability(Sensor.TYPE_GYROSCOPE)) {
            currentSensor = Sensor.TYPE_GYROSCOPE
        } else {
            textView!!.text = "Gyroscope Sensor not available"
        }
    }

    fun lightSensorOnClick(view: View?) {
        if (checkSensorAvailability(Sensor.TYPE_LIGHT)) {
            currentSensor = Sensor.TYPE_LIGHT
        } else {
            textView!!.text = "Light Sensor not available"
        }
    }

    fun stepCounterOnClick(view: View?) {
        if (checkSensorAvailability(Sensor.TYPE_STEP_DETECTOR)) {
            currentSensor = Sensor.TYPE_STEP_DETECTOR
        } else {
            textView!!.text = "Step Counter Sensor not available"
        }
    }

    fun ambientTempSensorOnClick(view: View?) {
        if (checkSensorAvailability(Sensor.TYPE_AMBIENT_TEMPERATURE)) {
            currentSensor = Sensor.TYPE_AMBIENT_TEMPERATURE
        } else {
            textView!!.text = "Ambient Temperature Sensor not available"
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager!!.registerListener(this, lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager!!.registerListener(this, proximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager!!.registerListener(this, stepCounterSensor,
                SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager!!.registerListener(this, tempSensor,
                SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager!!.registerListener(this, gyroscopeSensor,
                SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    companion object {
        private const val SHAKE_THRESHOLD = 600
    }
}