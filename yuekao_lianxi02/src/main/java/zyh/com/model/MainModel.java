package zyh.com.model;

import com.google.gson.Gson;

import zyh.com.bean.BeanJson;
import zyh.com.util.MyUtil;

public class MainModel {

    public static BeanJson loginString() {

        String path = "http://120.27.23.105/product/getCarts?source=android&uid=99";

        String postForm = MyUtil.get(path);

        Gson gson = new Gson();

        BeanJson beanJson = gson.fromJson(postForm, BeanJson.class);

        return beanJson;

    }
}
