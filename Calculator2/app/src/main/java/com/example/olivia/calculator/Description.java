package com.example.olivia.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Description extends AppCompatActivity {

    Button GoToCalculate;
    Button GoToMain;
    Button Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ExitApplication.getInstance().addActivity(this);

        GoToMain = (Button) findViewById(R.id.button4);
        GoToCalculate = (Button) findViewById(R.id.button5);
        Exit = (Button) findViewById(R.id.button6);

        GoToMain.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Description.this.finish();
            }
        });

        GoToCalculate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Description.this.finish();
            }
        });

        Exit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitApplication.getInstance().exit();
            }
        });
    }
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.xml.main3_out_from_left, R.xml.main_in_from_right);
        overridePendingTransition(R.xml.main3_out, R.xml.main2_in_from_left);
    }
}
