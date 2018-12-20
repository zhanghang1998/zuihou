package zyh.com.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import zyh.com.bean.BeanJson;
import zyh.com.core.MyInterface;
import zyh.com.model.MainModel;


public class Basepresenter {

    private MyInterface myInterface;

    public Basepresenter(MyInterface myInterface) {
        this.myInterface = myInterface;
    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            BeanJson beanJson = (BeanJson) msg.obj;

            myInterface.initData(beanJson);
        }
    };

    public void getrepuse(final Object...args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                message.obj=MainModel.loginString();
                handler.sendMessage(message);
            }
        }).start();
    }

    public void nuPresenter() {
        this.myInterface = null;
    }

}
