package com.qongyong.esq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;
import cn.bmob.newsmssdk.listener.VerifySMSCodeListener;


public class Regist_code extends AppCompatActivity{



    private TextView code;
    private EditText input;
    private Button next;
    private String phone;
    private TextView num;
    private Button to;
    int time = 60;
    /*
    * 更新UI的初始化*/

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what >0) {
                to.setText("重新发送 ("+msg.what+"s)");

            }else{
                to.setText("重新发送");
                to.setBackgroundColor(Color.BLUE);
                to.setTextColor(Color.WHITE);
            }
        };
    };






        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_regist_code);
            Log.d("Code", "onCreate: 初始化完成");
            Intent data = getIntent();
            phone = data.getStringExtra("phone");


            code = (TextView)findViewById(R.id.regist_code_text);
            input =(EditText) findViewById(R.id.reg_code_phone);
            next = (Button)findViewById(R.id.regist_code_next);
            num = (TextView)findViewById(R.id.regist_code_num);
            to = (Button) findViewById(R.id.regist_code_to);
            num.setText(phone);

            new MyThread().start();
            /*BmobSMS.requestSMSCode(Regist_code.this, "17854338307", "易记方注册验证码", new RequestSMSCodeListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if(e==null){
                        Toast.makeText(Regist_code.this,"验证码已经发送到您的手机",Toast.LENGTH_LONG);
                    }else {
                        Toast.makeText(Regist_code.this,"验证码已经发送失败："+e.getMessage(),Toast.LENGTH_LONG);
                    }
                }
            });*/






            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                     * 判断验证码是否正确*/


                    /*BmobSMS.verifySmsCode(Regist_code.this, phone, input.getText().toString(), new VerifySMSCodeListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                /*
                                * 进入下一个页面*/
                                Intent data = new Intent(Regist_code.this,Regist_setpwd.class);
                                data.putExtra("phone",phone);
                                startActivity(data);


                            /*}else{
                                Toast.makeText(Regist_code.this,"验证码错误！",Toast.LENGTH_LONG).show();
                            }
                        }
                    });*/




                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (time < 0) {
                        /*
                         * 发送验证码*/
                        BmobSMS.requestSMSCode(Regist_code.this, phone, "易记方注册验证码",
                                new RequestSMSCodeListener() {
                                    @Override
                                    public void done(Integer integer, BmobException e) {

                                        if(e==null){
                                            time = 60;
                                            to.setTextColor(Color.BLACK);
                                            to.setBackgroundColor(Color.GRAY);
                                        }
                                    }
                        });
                    }
                }
            });
        }

    private class  MyThread extends Thread {

        public void run() {
            to.setTextColor(Color.BLACK);
            to.setBackgroundColor(Color.GRAY);
            while (time >= 0) {
                try {
                    handler.sendEmptyMessage(time--);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }
    }
}