package com.example.accountdetail

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        addItemsOnSpinner()
        showAlert()

        val editText = findViewById<EditText>(R.id.textInputEditTextdate)
        editText.transformIntoDatePicker(this, "MM/dd/yyyy")
        editText.transformIntoDatePicker(this, "MM/dd/yyyy", Date())

        val refresh = findViewById<Button>(R.id.update_button)
        refresh.setOnClickListener(this)

        val add = findViewById<Button>(R.id.add_button)
        add.setOnClickListener {
            Toast.makeText(this,"Currently not available",Toast.LENGTH_SHORT).show()
        }


    }
    fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Data are processing...")

        val alert = builder.create()
        alert.show()

        val timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run() {
                alert.dismiss()
                timer.cancel()
            }
        },5000)
    }

    fun showAlert2(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Refreshing...")

        val alert = builder.create()
        alert.show()

        val timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run() {
                alert.dismiss()
                timer.cancel()
            }
        },5000)
    }


    fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }

    private fun addItemsOnSpinner() {

        var genderSpinner = findViewById<Spinner>(R.id.gender_spinner)

        val list: MutableList<String> = ArrayList()
        list.add("Debit")
        list.add("Debit")
        list.add("Credit")

        val genderAdapter = object : ArrayAdapter<Any>(
            this, R.layout.spinner,
            list as List<Any>
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return super.getDropDownView(position, convertView, parent).also { view ->
                    if (position == genderSpinner.selectedItemPosition) {
                        // view.setBackgroundColor(resources.getColor(R.color.color_light))
                        view.findViewById<TextView>(android.R.id.text1)
                            .setTextColor(resources.getColor(R.color.black))
                        if (position != 0) {
                            view.findViewById<TextView>(android.R.id.text1)
                                .setCompoundDrawablesWithIntrinsicBounds(
                                    0,
                                    0,
                                    R.drawable.ic_baseline_edit_24,
                                    0
                                )

                        } else {
                            view.findViewById<TextView>(android.R.id.text1)
                                .setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                        }
                    } else {

                        view.findViewById<TextView>(android.R.id.text1)
                            .setTextColor(resources.getColor(android.R.color.black))
                        view.findViewById<TextView>(android.R.id.text1)
                            .setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                }
            }
        }

        genderAdapter.setDropDownViewResource(R.layout.spinner)
        genderSpinner.adapter = genderAdapter

    }

    override fun onClick(p0: View?) {
        showAlert2()
    }
}