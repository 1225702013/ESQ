package com.qongyong.esq;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.app.Activity;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import Data.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.content.ContentValues.TAG;

public class getgold extends Activity implements View.OnClickListener,ServiceConnection {

    private TextView sum;
    private TextView number;
    private EditText mouth;
    private Updata.updata binder;
    private String num;
    int goldnum=0;
    User user = new User();
    LoadingDialog load;
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getgold);
        number = findViewById(R.id.getgold_getphone);
        sum = findViewById(R.id.getgold_num);
        mouth = findViewById(R.id.getgold_mouth);
        load = new LoadingDialog(this);
        Intent data = getIntent();
        num = data.getStringExtra("data");
        number.setText(num);

        load = new LoadingDialog(this);
        load.setLoadingText("正在登录...");
        load.setSuccessText("登录成功");//显示加载成功时的文字
        load.setFailedText("登录失败");
        load.setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);


        findViewById(R.id.three).setOnClickListener(this);
        findViewById(R.id.six).setOnClickListener(this);
        findViewById(R.id.end).setOnClickListener(this);
        findViewById(R.id.getgold_pay).setOnClickListener(this);
        bindService(new Intent(this,Updata.class),this, Context.BIND_AUTO_CREATE);

        //自定义开通
        mouth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    goldnum = Integer.valueOf(mouth.getText().toString()) * 30;
                    sum.setText(String.valueOf(goldnum));
                } catch (Exception e) {

                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.three :
                sum.setText(String.valueOf(30*3));
                mouth.setText(null);
                goldnum = 30*3;
                break;
            case R.id.six:
                sum.setText(String.valueOf(30*6) );
                mouth.setText(null);
                goldnum = 30*6;
                break;
            case R.id.end :
                sum.setText(String.valueOf(299));
                mouth.setText(null);
                goldnum = 299;
                break;
            case R.id.getgold_pay:{
                Intent i = new Intent();
                if(goldnum==0){
                    i.putExtra("data",0);
                    setResult(0,i);

                }
                else if(goldnum>0){


                    user.setNum(num);
                    user.setMemberTime(goldnum);
                    if(goldnum==299) {
                        user.setMemberType(2);
                    }
                    else {
                        user.setMemberType(1);
                    }
                }
                /*
                 * 调用服务上传数据到数据库*/
                load.show();

                        BmobQuery<User> query = new BmobQuery<>();
                        query.addWhereEqualTo("num",num);
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                list.get(0).setMemberType(user.getMemberType());
                                list.get(0).setMemberTime(list.get(0).getMemberTime() + user.getMemberTime());
                                if (binder != null) {
                                    if (binder.setdata(list.get(0)) == 1) {
                                        load.loadSuccess();
                                    } else {
                                        load.loadFailed();
                                    }
                                }
                                countDownLatch.countDown();
                            }
                        });
                    }
                    load.close();
            Intent data = new Intent(getgold.this,MainActivity.class);
            data.putExtra("phone",num);
            startActivity(data);
            finish();
                break;
            }
        }
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        Log.d(TAG, "onServiceConnected: 数据连接");
        binder = (Updata.updata) iBinder;
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d(TAG, "onServiceDisconnected: 服务释放");
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent data = new Intent(getgold.this,MainActivity.class);
        data.putExtra("phone",num);
        startActivity(data);
    }
}
