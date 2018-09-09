package com.qongyong.esq;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import Data.Car;
import Data.Dealer;
import Data.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.content.ContentValues.TAG;

public class Updata extends Service {

    public Updata() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new updata();
    }

    public class updata extends Binder{

        public int  setdata(User user){


            final String name = user.getName();
            final String num = user.getNum();
            final String pwd = user.getPwd();
            final int MemberType = user.getMemberType();
            final int MemberTime = user.getMemberTime();
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            BmobQuery<User> query = new BmobQuery<>();
            query.addWhereEqualTo("num",user.getNum());
            final int[] sucess = new int[1];
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if(e==null){
                        list.get(0).setName(name);
                        list.get(0).setNum(num);
                        list.get(0).setPwd(pwd);
                        list.get(0).setMemberTime(MemberTime);
                        list.get(0).setMemberType(MemberType);
                        list.get(0).update();
                        sucess[0] = 1;
                        countDownLatch.countDown();

                    }
                    else {
                        sucess[0] = 0;
                        countDownLatch.countDown();
                        return  ;
                    }

                }
            });
            /*
            * 更新用户数据*/
            return 1;

        }

        public int setdata (Dealer data){

            /*
            * 更新发牌员数据*/
            return 0;
        }

        public  int setdata (Car data){

            /*
            * 更新车辆数据*/
            return 0;
        }
        public int addData(Dealer data){
            /*
            * 添加发牌员数据*/

            return 0;
        }
        public int addData(Car data){
            /*
            * 添加车辆数据*/
            return 0;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(){

            @Override
            public void run() {
                super.run();
                /*
                * 更新数据库*/



                onDestroy();
            }
        }.start();





    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
