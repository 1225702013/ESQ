package com.qongyong.esq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.List;

import Data.User;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    private EditText phone;
    private EditText pwd;
    private Button login;
    private TextView reg;
    private TextView question;
    private LoadingDialog load;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "848cad5ba37c4fd27d8e0fcbee8e6ac9");

        phone = (EditText) findViewById(R.id.login_phone);
        pwd = (EditText) findViewById(R.id.login_pwd);
        login = (Button) findViewById(R.id.login);
        reg =  (TextView) findViewById(R.id.login_reg);
        question = (TextView) findViewById(R.id.login_ques);






        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                load = setLoad(load);

                load.show();

                BmobQuery<User> query = new BmobQuery<>();
                query.addWhereEqualTo("num",phone.getText().toString());

                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {

                        if(e!=null){

                            load.loadFailed();
                            show(String.valueOf(e.getErrorCode()));
                        }else{

                            if(list.get(0).getPwd().equals(pwd.getText().toString())){
                                Intent login_data = new Intent(LoginActivity.this,MainActivity.class);
                                login_data.putExtra("phone",list.get(0).getNum());
                                load.loadSuccess();
                                load.close();
                                startActivity(login_data);
                                finish();
                            }else{
                                load.loadFailed();
                                show("1001");
                            }
                        }

                    }
                });
            }
        });


        //注册
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,Regist_Phone.class));
            }
        });


    }

    private void show(String e) {


        switch (e){

            case "9015":
                Toast.makeText(LoginActivity.this,"登录失败，请检查手机号是否正确，错误代码"+e,Toast.LENGTH_LONG).show();
                break;
            case "1001":
                Toast.makeText(LoginActivity.this,"登录失败:密码错误！错误代码"+e,Toast.LENGTH_LONG).show();
                break;
                default:Toast.makeText(LoginActivity.this,"登录失败:未知错误，错误代码"+e,Toast.LENGTH_LONG).show();



        }

    }

    private LoadingDialog setLoad(LoadingDialog load){

        load = new LoadingDialog(this);
        load.setLoadingText("正在登录...");
        load.setSuccessText("登录成功");//显示加载成功时的文字
        load.setFailedText("登录失败");
        load.setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        return load;

    }


}
