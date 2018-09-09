package com.qongyong.esq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qongyong.esq.MainActivity;
import com.qongyong.esq.R;

public class Regist_sucess extends AppCompatActivity {

    private TextView phone;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_sucess);
        Intent data = getIntent();
        phone = (TextView) findViewById(R.id.regist_suce_num);
        login = (Button) findViewById(R.id.regist_suce_login);
        phone.setText(data.getStringExtra("phone"));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent data = new Intent(Regist_sucess.this,MainActivity.class);
                data.putExtra("phone",phone.getText().toString());
                startActivity(data);
            }
        });
    }
}
