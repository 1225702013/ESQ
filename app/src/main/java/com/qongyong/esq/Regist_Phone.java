package com.qongyong.esq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qongyong.esq.R;

public class Regist_Phone extends AppCompatActivity {

    private EditText num;
    private Button next;
    private TextView read;
    private CheckBox right;
    private int true_num = 0;
    private int true_box = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist__phone);
        num = (EditText) findViewById(R.id.regist_phone_num);
        next = (Button) findViewById(R.id.regist_phone_next);
        read = (TextView) findViewById(R.id.regist_phone_text);
        right = (CheckBox) findViewById(R.id.regist_right);


        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.length()>10){
                    true_num = 1;
                    if(true_box==1){
                        /*
                        * 更改按钮颜色*/
                        next.setBackgroundResource(R.mipmap.ic_launcher_round);
                    }
                }else{
                    next.setBackgroundResource(R.mipmap.ic_launcher);
                }

            }
        });
        right.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    true_box = 1;
                    if(true_num==1){
                        /*
                        * 修改按钮*/
                        next.setBackgroundResource(R.mipmap.ic_launcher_round);

                    }else{
                        next.setBackgroundResource(R.mipmap.ic_launcher);


                    }
                }else {
                    true_box = 0;
                    next.setBackgroundResource(R.mipmap.ic_launcher);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(true_box==1){
                    if(true_num==1){
                        Intent data = new Intent(Regist_Phone.this,Regist_code.class);
                        data.putExtra("phone",num.getText().toString());
                        startActivity(data);
                    }else{

                    }
                }else{

                }
            }
        });
    }
}
