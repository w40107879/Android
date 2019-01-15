package com.example.olivia.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;

public class CalculateActivity extends AppCompatActivity {

    TextView BMI;
    TextView BMR;
    TextView ShowState;
    TextView ShowBMR;
    TextView NiceWeigh;
    Button Back,Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ExitApplication.getInstance().addActivity(this);

        Back = (Button)findViewById(R.id.button3);
        Description = findViewById(R.id.button7);

        Bundle bundle = getIntent().getExtras();
        String h = bundle.getString("height" );
        String w = bundle.getString("width");
        String a = bundle.getString("age");
        Double state = bundle.getDouble("state",0.0);

        double fh = Double.parseDouble(h);      // 取得 身高輸入值
        double fw = Double.parseDouble(w);     // 取得 體重輸入值
        double age = Double.parseDouble(a);      //取得 年齡輸入值
        double MBMR,FBMR;
        double NiceWeighMale,NiceWeighFeMale;
        double fresult;                                     // BMI值 計算結果
        TextView result = findViewById(R.id.textView10);// 取得 顯示結果 物件
        double fhforBMR=fh;
        fh = fh/100; // 計算BMI
        fh = fh*fh;  // 計算BMI

        NumberFormat nf = NumberFormat.getInstance();   // 數字格式
        nf.setMaximumFractionDigits(1);                 // 限制小數第二位
        fresult = fw/fh;                                // 計算BMI
        result.setText(nf.format(fw/fh) );           // 顯示BMI計算結果
        TextView dia = findViewById(R.id.textView11);// 取得 顯示診斷 物件
        ShowState = findViewById(R.id.textView4);//顯示 建議
        ShowBMR =  findViewById(R.id.textView12);//顯示 BMR
        NiceWeigh = findViewById(R.id.textView18);         //顯示 理想體重

        //計算男女的理想體重和BMR
        MBMR = (13.7 * fw) + (5.0 * fhforBMR) - (6.8 * age) + 66;
        FBMR = (9.6 * fw) + (1.8 * fhforBMR) - (4.7 * age) + 655;
        NiceWeighMale = (fhforBMR - 80) * 0.7;
        NiceWeighFeMale = (fhforBMR - 70) * 0.6;

        if(state==1.0) {
            ShowBMR.setText(nf.format(MBMR));
            NiceWeigh.setText(nf.format(NiceWeighMale));
        }
        else {
            ShowBMR.setText(nf.format(FBMR));
            NiceWeigh.setText(nf.format(NiceWeighFeMale));
        }


        // 診斷結果 顯示
        if (fresult<18.5){
            dia.setText("體重過輕");
            ShowState.setText("「體重過輕」，需要多運動，均衡飲食，以增加體能，維持健康！");
        }
        else if (18.5 <= fresult && fresult< 24){
            dia.setText("正常範圍");
            ShowState.setText("「健康體重」，要繼續保持喔！");
        }
        else if (24 <=fresult && fresult < 27) {
            dia.setText("過    重");
            ShowState.setText("「體重過重」，要小心囉，趕快力行健康體重管理吧！");
        }
        else if (27 <=fresult && fresult < 30) {
            dia.setText("輕度肥胖");
            ShowState.setText("「肥胖」，需要立刻力行健康體重管理喔！");
        }
        else if (30 <= fresult && fresult < 35) {
            dia.setText("中度肥胖");
            ShowState.setText("「肥胖」，需要立刻力行健康體重管理喔！");
        }
        else if (fresult >= 35) {
            dia.setText("重度肥胖");
            ShowState.setText("「肥胖」，需要立刻力行健康體重管理喔！");
        }

        Back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateActivity.this.finish();
            }
        });

        Description.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CalculateActivity.this,Description.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.xml.main2_out, R.xml.main_in_from_right);
        overridePendingTransition(R.xml.main2_out_to_right, R.xml.main3_in);
    }

}
