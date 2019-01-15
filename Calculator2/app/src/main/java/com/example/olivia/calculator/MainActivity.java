package com.example.olivia.calculator;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public RadioGroup rg;
    public RadioButton rg1,rg2;
    public ListView listdata;
    public ListView listdata2;
    public ListView listdata3;
    public ListView listdata4;
    public ListView listdata5;
    List<String> dataListTall = new ArrayList<>();
    List<String> dataListName = new ArrayList<>();
    List<String> dataListSex  = new ArrayList<>();
    List<String> dataListAge  = new ArrayList<>();
    List<String> dataListWeight  = new ArrayList<>();
    EditText Name;
    EditText Age;
    EditText Tall;
    EditText Weight;
    Button GoToCalculate;
    Button Reset;
    Button Description;
    Button Save2;
    Button Load2;
    Button Delete;
    double BMR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExitApplication.getInstance().addActivity(this);

        Name = findViewById(R.id.editText4);
        Age = findViewById(R.id.editText);
        Tall = findViewById(R.id.editText2);
        Weight =  findViewById(R.id.editText3);
        GoToCalculate = findViewById(R.id.button);
        Reset = findViewById(R.id.button2);
        Description = findViewById(R.id.button8);
        Save2 = findViewById(R.id.button11);
        Load2 = findViewById(R.id.button12);
        Delete = findViewById(R.id.button9);
        rg1 = findViewById(R.id.male);
        rg2 = findViewById(R.id.female);
        listdata = findViewById(R.id.list_view);
        listdata2 = findViewById(R.id.list_view2);
        listdata3 = findViewById(R.id.list_view3);
        listdata4 = findViewById(R.id.list_view4);
        listdata5 = findViewById(R.id.list_view5);

        /*-------------------------------Adapter-----------------------------------*/
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, dataListTall) ;
        listdata2.setAdapter(adapter2);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, dataListName) ;
        listdata.setAdapter(adapter);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, dataListSex) ;
        listdata3.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, dataListAge) ;
        listdata4.setAdapter(adapter4);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, dataListWeight) ;
        listdata5.setAdapter(adapter5);
        /*--------------------------------Adapter----------------------------------*/
        //TASK 3: ESTABLISH THE REFERENCES TO OUTPUT ELEMENTS

        rg = findViewById(R.id.radioGroup1);
        rg.setOnCheckedChangeListener(listener);


        Save2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !("".equals(Tall.getText().toString())
                        || "".equals(Weight.getText().toString())
                        || "".equals(Age.getText().toString())
                        || "".equals(Name.getText().toString()))
                        &&!(dataListName.contains(Name.getText().toString()))) {

                    dataListName.add(Name.getText().toString());
                    dataListTall.add(Tall.getText().toString());
                    dataListAge.add(Age.getText().toString());
                    dataListWeight.add(Weight.getText().toString());
                    if(rg1.isChecked()){
                        dataListSex.add("M");
                    }
                    else if(rg2.isChecked()){
                        dataListSex.add("F");
                    }
                }
                else if(dataListName.contains(Name.getText().toString())){

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("修改");
                    builder.setMessage("確認修改此紀錄?");
                    builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int idx = dataListName.indexOf(Name.getText().toString());
                            dataListTall.set(idx,Tall.getText().toString());
                            dataListWeight.set(idx,Weight.getText().toString());
                            dataListAge.set(idx,Age.getText().toString());
                            if(rg1.isChecked()){
                                dataListSex.set(idx,"M");
                            }
                            else if(rg2.isChecked()){
                                dataListSex.set(idx,"F");
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                else {
                    Toast.makeText(MainActivity.this, R.string.save_fail, Toast.LENGTH_LONG).show();
                }

            }
        });


        GoToCalculate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判斷條件 身高 跟 體重 都有輸入值才執行
                if ( !("".equals(Tall.getText().toString())
                        || "".equals(Weight.getText().toString())
                        || "".equals(Age.getText().toString())) )
                {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,CalculateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("height",Tall.getText().toString());
                    bundle.putString("width",Weight.getText().toString());
                    bundle.putString("age",Age.getText().toString());
                    bundle.putDouble("state",BMR);
                    intent.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
                    startActivity(intent);
                }
            }
        });

        Reset.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name.setText("");
                Age.setText("");
                Weight.setText("");
                Tall.setText("");
                rg1.setChecked(true);
                dataListName.clear();
                dataListTall.clear();
                dataListSex.clear();
                dataListAge.clear();
                dataListWeight.clear();
            }
        });

        Description.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Description.class);
                startActivity(intent);
            }
        });

        listdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Load2.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Name.setText(dataListName.get(position));
                        Tall.setText(dataListTall.get(position));
                        if(dataListSex.get(position)=="M"){
                            rg1.setChecked(true);
                        }
                        else
                            rg2.setChecked(true);
                        Age.setText(dataListAge.get(position));
                        Weight.setText(dataListWeight.get(position));
                    }
                });
                Delete.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("刪除");
                                builder.setMessage("確認刪除此紀錄?");
                                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Name.setText("");
                                        Tall.setText("");
                                        Age.setText("");
                                        Weight.setText("");
                                        rg1.setChecked(true);
                                        dataListName.remove(position);
                                        dataListTall.remove(position);
                                        dataListSex.remove(position);
                                        dataListAge.remove(position);
                                        dataListWeight.remove(position);
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
        });


    }


    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // TODO Auto-generated method stub

            switch(checkedId){
                case R.id.male:
                    BMR=1.0;
                    break;
                case R.id.female:
                    BMR=2.0;
                    break;
            }
        }
    };

    //動畫效果
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.xml.main_out, R.xml.main2_in_from_left);
        overridePendingTransition(R.xml.main_out_to_left, R.xml.main3_in);
    }
}
