package zyh.com.util;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyUtil {

    public static String get(String urlString) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(urlString).get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String postForm(String url, String[] name, String[] value) {

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder formBuild = new FormBody.Builder();
        for (int i = 0; i < name.length; i++) {
            formBuild.add(name[i], value[i]);
        }

        Request request = new Request.Builder().url(url).post(formBuild.build()).build();

        try {
            Response response = okHttpClient.newCall(request).execute();

            String result = response.body().string();
            Log.i("张雨航", "result" + result);

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
