package com.example.kotlin_mvp_movie.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.example.kotlin_mvp_movie.R
import kotlinx.android.synthetic.main.fragment_dialog.*

class DialogFragment : DialogFragment() {

    var updateDate:  MutableLiveData<String> = MutableLiveData()
    var selectedDate: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        main_calendar.setOnDateChangeListener { _, year, month, date ->

            val mYear = year.toString()
            val mMonth: String = if ((month + 1) > 9){
                (month+1).toString()
            }else{
                "0"+(month+1).toString()
            }
            val mDate: String = if (date < 10){
                "0$date"
            }else{
                date.toString()
            }
            selectedDate = mYear+"년"+mMonth+"월"+mDate+"일"
            updateDate.value = mYear+mMonth+mDate
            dismiss()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
