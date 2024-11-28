package com.example.lengthconverterapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val units = arrayOf("Metre", "Millimetre", "Mile", "Foot")
    private val conversionFactors = mapOf(
        "Metre" to 1.0,
        "Millimetre" to 0.001,
        "Mile" to 1609.34,
        "Foot" to 0.3048
    )

    private var fromUnit: String = "Metre"
    private var toUnit: String = "Foot"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputField = findViewById<EditText>(R.id.inputValue)
        val fromUnitSpinner = findViewById<Spinner>(R.id.fromUnitSpinner)
        val toUnitSpinner = findViewById<Spinner>(R.id.toUnitSpinner)
        val convertButton = findViewById<Button>(R.id.convertButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromUnitSpinner.adapter = adapter
        toUnitSpinner.adapter = adapter

        fromUnitSpinner.setSelection(0)
        toUnitSpinner.setSelection(1)

        fromUnitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                fromUnit = units[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        toUnitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                toUnit = units[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        convertButton.setOnClickListener {
            val inputValue = inputField.text.toString().toDoubleOrNull()

            if (inputValue != null) {
                val fromFactor = conversionFactors[fromUnit] ?: 1.0
                val toFactor = conversionFactors[toUnit] ?: 1.0

                val result = inputValue * fromFactor / toFactor


                val formattedResult = if (result % 1.0 == 0.0) {
                    result.toInt().toString()
                } else {
                    result.toString().trimEnd('0').trimEnd('.')
                }

                val formattedInputValue = if (inputValue % 1.0 == 0.0) {
                    inputValue.toInt().toString()
                } else {
                    inputValue.toString().trimEnd('0').trimEnd('.')
                }

                val resultText = "$formattedInputValue $fromUnit = $formattedResult $toUnit"
                resultTextView.text = resultText
                resultTextView.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, getString(R.string.toast_invalid_number), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
