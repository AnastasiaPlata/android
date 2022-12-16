package com.example.myagecalculator

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

   private var textviewDate:TextView? = null
    private var textviewInMinutes:TextView? = null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonDatePicker = findViewById<Button>(R.id.btn1)
         textviewDate= findViewById<TextView>(R.id.TextView4)
        textviewInMinutes= findViewById<TextView>(R.id.TextView6)
//        όταν πατηθεί το κουμπί για την επιλογή ημερομηνίας γέννησης , τότε τρέχει την function
        buttonDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickDatePicker() {
//  μεταβλητές που θα πάρουν τις τιμές (χρονιάς,μήνα,ημέρας)  από το ημερολόγιο
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
//        ανοίγω το ημερολόγιο και θέτω inputs
       val dpd = DatePickerDialog(this, { view, selectedYear, selectedMonth, selectedDayOfMonth ->
//            με το που πατήσω το οκ στο ημερολόγιο θα εμφανιστεί ενα μήνυμα
           Toast.makeText(this, "  ${selectedDayOfMonth+1}/ ${selectedMonth+1} /$selectedYear",
                Toast.LENGTH_SHORT).show()

            val selectedDate= "${selectedDayOfMonth}/${selectedMonth+1}/$selectedYear"
//           το text θα πάρει τα inputs
            textviewDate?.text= selectedDate
//          με συγκεκριμένο format
            val sdf=SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate= sdf.parse(selectedDate)

           theDate?.let {
//               μετατροπή ημερομηνίας σε λεπτά
               val selectedDateInMinutes = theDate.time/60000
//             παίρνω την τωρινή ημερομηνία
               val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

//               το let αποτρέπει το κρασάρισμα σε περίπτωση που δεν επιλεχτεί ημερομηνία

               currentDate?.let{
//                       μετατροπή τωρινής ημερομηνίας σε λεπτά
                   val currentDateInMinutes= currentDate.time/60000
//                 κάνω την αφαίρεση για να βρω την διαφορά
                   val differenceInMinutes=currentDateInMinutes - selectedDateInMinutes
//                επειδή τα λεπτά είναι datatype: long μετατρέπω σε string
                   textviewInMinutes?.text = differenceInMinutes.toString()
               }

           }
//            outputs
        },year,month,day)
//        επιτρέπει την επιλογή ημερομηνίας μέχρι και την τωρινή μετά το αποτέλεσμα βγάζει μείον
           dpd.datePicker.maxDate = System.currentTimeMillis()- 86400000
           dpd.show()

    

        }
          }