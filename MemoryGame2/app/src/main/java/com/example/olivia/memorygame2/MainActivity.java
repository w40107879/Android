package com.example.olivia.memorygame2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Chronometer			chronometer;
    private static int			rowCount	= 4;
    private static int			columeCount	= 4;
    private static int			items;
    private Context				context;
    private Drawable			backImage;
    private int[][]				cards;
    private List<Drawable>		images;
    private Card				firstCard;
    private Card				seconedCard;
    private ButtonListener		buttonListener;
    private static Object		lock		= new Object();
    int							pairCount;
    private TableLayout			mainTable;
    private UpdateCardsHandler	handler;
    int                         turns;
    private Button              button;
    private Button              button2;
    private SeekBar             seekBar;
    private TextView            CurrentState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new UpdateCardsHandler();
        loadImages();
        setContentView(R.layout.activity_main);

        backImage = getResources().getDrawable(R.drawable.empty);
        buttonListener = new ButtonListener();
        mainTable = findViewById(R.id.MyTableLayout);
        context = mainTable.getContext();
        CurrentState = findViewById(R.id.textView3);

        chronometer = findViewById(R.id.MyChronometer);
        chronometer.setFormat("遊戲時間： %s");

        button =  findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
             CurrentState.setText("大小設定 : "+progress);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //把seek bar的值放數變數value並傳到GameActivity
                        Intent i = new Intent();
                        i.setClass(MainActivity.this, GameActivity.class);
                        int temp = progress;
                        i.putExtra("value",temp);
                        startActivity(i);
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
    }


    private void loadImages() {
        images = new ArrayList<Drawable>(); //生成image的list

        images.add(getResources().getDrawable(R.drawable.item01));
        images.add(getResources().getDrawable(R.drawable.item02));
        images.add(getResources().getDrawable(R.drawable.item03));
        images.add(getResources().getDrawable(R.drawable.item04));
        images.add(getResources().getDrawable(R.drawable.item05));
        images.add(getResources().getDrawable(R.drawable.item06));
        images.add(getResources().getDrawable(R.drawable.item07));
        images.add(getResources().getDrawable(R.drawable.item08));
        images.add(getResources().getDrawable(R.drawable.item09));
        images.add(getResources().getDrawable(R.drawable.item10));
        images.add(getResources().getDrawable(R.drawable.item11));
        images.add(getResources().getDrawable(R.drawable.item12));
        images.add(getResources().getDrawable(R.drawable.item13));
        images.add(getResources().getDrawable(R.drawable.item14));
        images.add(getResources().getDrawable(R.drawable.item15));
        images.add(getResources().getDrawable(R.drawable.item16));
        images.add(getResources().getDrawable(R.drawable.item17));
        images.add(getResources().getDrawable(R.drawable.item18));
        images.add(getResources().getDrawable(R.drawable.item19));
        images.add(getResources().getDrawable(R.drawable.item20));
        images.add(getResources().getDrawable(R.drawable.item21));
        images.add(getResources().getDrawable(R.drawable.item22));
        images.add(getResources().getDrawable(R.drawable.item23));
        images.add(getResources().getDrawable(R.drawable.item24));
        images.add(getResources().getDrawable(R.drawable.item25));
        images.add(getResources().getDrawable(R.drawable.item26));
    }

    private void loadCards() {
        try {

            int size = rowCount * columeCount; //size = 36
            ArrayList<Integer> list = new ArrayList<Integer>();

            for (int i = 0; i < size; i++) {
                list.add(new Integer(i)); // 加入所有卡片編號
            }

            Random r = new Random();

            for (int i = size - 1; i >= 0; i--) { // size - 1 = 35
                int t = 0;
                if (i > 0) {
                    t = r.nextInt(i);// 隨機取得編號 [0~i-1]
                }


                t = list.remove(t).intValue(); // 從 list 中取出編號
                cards[i % columeCount][i / columeCount] = t % (size / 2) ; // 將編號放入指定位置
            }

            // 再次洗牌

            for (int i = 0; i < rowCount; i++)
                for (int j = 0; j < columeCount; j++) {
                    int rc = r.nextInt(rowCount);
                    int cc = r.nextInt(columeCount);
                    int temp;

                    temp = cards[i][j];
                    cards[i][j] = cards[rc][cc];
                    cards[rc][cc] = temp;
                }
        }
        catch (Exception e) {
        }

    }

    private TableRow createRow(int y) {
        TableRow row = new TableRow(context);
        row.setHorizontalGravity(Gravity.CENTER);

        for (int x = 0; x < columeCount; x++) {
            row.addView(createImageButton(x, y));
        }
        return row;
    }

    private View createImageButton(int x, int y) {
        Button button = new Button(context);
        button.setBackgroundDrawable(backImage);
        button.setId(100 * x + y);
        button.setOnClickListener(buttonListener);
        return button;
    }

    class ButtonListener implements OnClickListener {
        public void onClick(View v) {
            synchronized (lock) {
                if (firstCard != null && seconedCard != null) {
                    return;
                }
                int id = v.getId();
                int x = id / 100;
                int y = id % 100;
                turnCard((Button) v, x, y);
            }
        }

        private void turnCard(Button button, int x, int y) {
            button.setBackgroundDrawable(images.get(cards[x][y]));

            if (firstCard == null) {
                firstCard = new Card(button, x, y);
            }
            else {
                if (firstCard.x == x && firstCard.y == y) {
                    return;
                     // 選到相同的卡片則不動作
                }

                seconedCard = new Card(button, x, y);

                TimerTask tt = new TimerTask() {
                        @Override
                        public void run() {
                        try {
                            synchronized (lock) {
                                handler.sendEmptyMessage(0);
                            }
                        }
                        catch (Exception e) {
                            Log.e("E1",e.getMessage());
                        }
                    }
                };

                Timer t = new Timer(false);
                t.schedule(tt, 500); // 停頓2秒
            }
        }
    }

    class UpdateCardsHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            synchronized (lock) {
                checkCards();
            }
        }

        public void checkCards() {
            if (cards[seconedCard.x][seconedCard.y] == cards[firstCard.x][firstCard.y]) {
                firstCard.button.setClickable(false);
                seconedCard.button.setClickable(false);
                firstCard.button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "按鍵無效！", Toast.LENGTH_SHORT).show();
                    }
                });
                seconedCard.button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "按鍵無效！", Toast.LENGTH_SHORT).show();
                    }
                });
                pairCount++;
                if (pairCount >= items) {
                    chronometer.stop();

                    Button button = new Button(MainActivity.this);
                    button.setText("確定");
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setTitle("恭喜你完成所有配對！");
                    dialog.setContentView(button);
                    dialog.show();
                    button.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }
            else {
                seconedCard.button.setBackgroundDrawable(backImage);
                firstCard.button.setBackgroundDrawable(backImage);
                turns++;

            }
            firstCard = null;
            seconedCard = null;
        }
    }
}

