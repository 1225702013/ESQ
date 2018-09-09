package com.qongyong.esq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Data.User;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView name;
    private  TextView num;
    private Button isgold;
    private TextView member;
    private String phone;


    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
                member.setText(member.getText()+" "+msg.what+" 天");
        };
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        name =  findViewById(R.id.person_name);
        num = findViewById(R.id.person_num);
        isgold = findViewById(R.id.get_gold);
        member = findViewById(R.id.person_member);


        Intent person_name = getIntent();
        phone = person_name.getStringExtra("phone");
        /*
        * 获取信息*/
        new MyThread().start();


        findViewById(R.id.get_gold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(MainActivity.this,getgold.class);
                data.putExtra("data",num.getText().toString());
                startActivityForResult(data,0);
                finish();
            }
        });


        findViewById(R.id.person_manager).setOnClickListener(this);
        findViewById(R.id.car_manager).setOnClickListener(this);
        findViewById(R.id.about_we).setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.person_manager :
                /*
                * 人员管理界面*/
                break;
            case R.id.car_manager :
                /*
                * 车辆管理*/
                break;
            case R.id.about_we :
                /*
                * 联系我们*/
                break;

        }

    }

    private class  MyThread extends Thread {

        public void run() {

            try {


                while (true){
                    BmobQuery<User> query = new BmobQuery<>();
                    query.addWhereEqualTo("num",phone);
                    query.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            name.setText(list.get(0).getName());
                            num.setText(list.get(0).getNum());
                            Log.d("Thread", "done: 更新一次");
                            switch (list.get(0).getMemberType()){

                                case 0:
                                    member.setText("试用会员："+list.get(0).getMemberTime()+" 天");
                                    break;
                                case 1:
                                    isgold.setText("续费会员");
                                    member.setText("正式会员："+list.get(0).getMemberTime()+" 天");
                                    break;
                                case 2:
                                    member.setText("永久会员："+list.get(0).getMemberTime()+" 天");
                                    isgold.setVisibility(View.INVISIBLE);
                                    break;

                            }

                        }
                    });
                    sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


