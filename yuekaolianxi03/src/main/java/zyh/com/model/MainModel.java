package zyh.com.model;

import com.google.gson.Gson;

import zyh.com.bean.BeanString;
import zyh.com.util.MyUtil;

public class MainModel {

    public static BeanString loginString() {

        String path = "http://120.27.23.105/product/getCarts?source=android&uid=99";

        String postForm = MyUtil.get(path);

        Gson gson = new Gson();

        BeanString beanJson = gson.fromJson(postForm, BeanString.class);

        return beanJson;

    }
}
