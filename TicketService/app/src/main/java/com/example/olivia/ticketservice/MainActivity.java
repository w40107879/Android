package com.example.olivia.ticketservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;



public class MainActivity extends AppCompatActivity {

    String Year,Mon,Day;
    DatePicker date;
    TextView tv_date,shownum;
    TextView OKFrom,OKTo;
    Button btn_return;
    Spinner spinner,spinner2;
    EditText InputNunber;
    Button OK;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        //下拉式選單-起站
        spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> FromPlace = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.from,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(FromPlace);
        OKFrom = findViewById(R.id.textView10);
        OKTo = findViewById(R.id.textView11);

        //下拉式選單-迄站
        spinner2 = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> ToPlace = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.from2,
                android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(ToPlace);

        InputNunber = findViewById(R.id.editText);
        OK = findViewById(R.id.button);
        shownum = findViewById(R.id.textView9);



        date = (DatePicker) this.findViewById(R.id.date);
        tv_date = (TextView) this.findViewById(R.id.tv_date);
        btn_return = (Button) this.findViewById(R.id.btn_return);

        Calendar calendar = Calendar.getInstance();
        int sYear = calendar.get(Calendar.YEAR);
        //取得年分
        int sMonth = calendar.get(Calendar.MONTH);
        //取得月份
        int sDay = calendar.get(Calendar.DAY_OF_MONTH);
        //取的天數

        Year = DateFix(sYear);
        Mon  = DateFix(sMonth);
        Day  = DateFix(sDay);

        date.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                //DatePicker年月日更改後，會觸發作以下的事情。
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                        Year = DateFix(year);
                        Mon  = DateFix(monthOfYear + 1); //月的初始是0，所以先加 1。
                        Day  = DateFix(dayOfMonth);

                    }
                });
        btn_return.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    System.exit(0);
            }
        });

        OK.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                display();
            }
        });
    }

    public void display(){
        tv_date.setText(Year+"/"+Mon+"/"+Day);
        shownum.setText(InputNunber.getText());
        OKFrom.setText(spinner.getSelectedItem().toString());
        OKTo.setText(spinner2.getSelectedItem().toString());
    }

    private static String DateFix(int c){
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}

