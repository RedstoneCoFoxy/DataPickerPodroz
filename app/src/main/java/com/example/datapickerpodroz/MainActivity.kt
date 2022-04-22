package com.example.datapickerpodroz

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import android.widget.CalendarView.OnDateChangeListener
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    // Start

        val ButtonStart = findViewById<Button>(R.id.button_StartDate)
        val ButtonPhase1 = findViewById<Button>(R.id.button_EndPh1)
        val ButtonPhase2 = findViewById<Button>(R.id.button_EndPh2)
        val Kalendarz = findViewById<CalendarView>(R.id.calendarView)
        val DateBox = findViewById<TextView>(R.id.textView_WybraneDaty)

        val ButtonSimulate = findViewById<Button>(R.id.button_SimulationGo)
        val SymBox = findViewById<TextView>(R.id.textView_BoxSymulacja)
        val Seek = findViewById<SeekBar>(R.id.seekBar_TimeSpan)
        val Prog = findViewById<ProgressBar>(R.id.progressBar)

        var SelectedDate : String=""
        var DataStart : LocalDate = LocalDate.parse("2022-01-01")
        var DataEtap1 : LocalDate = LocalDate.parse("2022-01-01")
        var DataEtap2 : LocalDate = LocalDate.parse("2022-01-01")


        fun ZmianaDaty(){
            var OK : Boolean = true
            if(DataStart>=DataEtap1){DateBox.text="Początek nie może być równy lub większy końcowi Etapu I";OK=false}
            if(DataStart>=DataEtap2){DateBox.text="Początek nie może być równy lub większy końcowi Etapu II";OK=false}
            if(DataEtap1>=DataEtap2){DateBox.text="Koniec Etapu I nie może być równy lub większy końcowi Etapu II";OK=false}

            if(OK) {
                DateBox.text = "Data początkowa:"+DataStart.toString() + "\nData końca Etapu I:" + DataEtap1.toString() + "\nData końca Etapu II:" + DataEtap2.toString() + " "
                //Kalendarz.setDate(DataEtap1.atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
            }
        }
        ButtonStart.setOnClickListener(){
            DataStart = LocalDate.parse(SelectedDate)
            DateBox.text = SelectedDate
            ZmianaDaty()
        }
        ButtonPhase1.setOnClickListener(){
            DataEtap1 = LocalDate.parse(SelectedDate)
            ZmianaDaty()
        }
        ButtonPhase2.setOnClickListener(){
            DataEtap2 = LocalDate.parse(SelectedDate)
            ZmianaDaty()
        }

        Kalendarz.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth -> // TODO Auto-generated method stub
            SelectedDate=year.toString()+"-"
            if((month+1)>9){
                SelectedDate+=(month+1).toString()+"-"
            }else {
                SelectedDate += "0" + (month+1).toString() + "-"
            }

            if(dayOfMonth>9){
                SelectedDate+=dayOfMonth.toString()
            }else {
                SelectedDate += "0" + dayOfMonth.toString()
            }
        })


        ButtonSimulate.setOnClickListener(){
            var Multiplier = (Seek.progress.toFloat()/10)+1
            SymBox.text="Prędkość symulacji : "+Multiplier.toString()+"x"

            var CzasPodrozy=10
            var i=1

            while(i<=CzasPodrozy){
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        SymBox.text= SymBox.text.toString()+"a"
                        i++
                    },
                    (1000*Multiplier).toLong()
                )
            }

        }

    }// End
}