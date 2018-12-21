package zyh.com.zhoukao3_lianxi02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import zyh.com.adapter.BuJuAdapter;
import zyh.com.bean.JsonBean;
import zyh.com.core.Feag02Interface;
import zyh.com.presenter.Frag02presenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,XRecyclerView.LoadingListener,Feag02Interface {

    private static final int GRID_LAYOUT_MANAGER = 1;
    private static final int LINEAR_LAYOUT_MANAGER = 2;
    Frag02presenter presenter = new Frag02presenter(this);
    ArrayList<JsonBean.DataBean> list = new ArrayList<>();
    private boolean isTrue= true;
    private ImageView imageView;
    private XRecyclerView xRecyclerView;
    private BuJuAdapter buJuAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取控件
        radioGroup = findViewById(R.id.radioGroup_two);
        //获取控件
        imageView = findViewById(R.id.imageView02_buju);
        xRecyclerView = findViewById(R.id.xRecyclerView_frag02);
        xRecyclerView.setLoadingListener(this);
        imageView.setOnClickListener(this);

        gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        xRecyclerView.setLayoutManager(linearLayoutManager);

        buJuAdapter = new BuJuAdapter(list, this);
        xRecyclerView.setAdapter(buJuAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageView02_buju:

                if (xRecyclerView.getLayoutManager().equals(linearLayoutManager)) {

                    isTrue=true;
                    buJuAdapter.setViewType(BuJuAdapter.GRID_TYPE);
                    xRecyclerView.setLayoutManager(gridLayoutManager);

                } else {
                    isTrue=false;
                    buJuAdapter.setViewType(BuJuAdapter.LINEAR_TYPE);
                    xRecyclerView.setLayoutManager(linearLayoutManager);

                }

                buJuAdapter.notifyDataSetChanged();

                break;
        }
    }

    @Override
    public void onRefresh() {
        presenter.requests(true);
    }

    @Override
    public void onLoadMore() {
        presenter.requests(false);
    }

    @Override
    public void getBeean(JsonBean jsonBean) {
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
        List<JsonBean.DataBean> data = jsonBean.getData();
        list.addAll(data);
        Log.v("张雨航zyh","data"+data);
        buJuAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unBind();
    }
}
