package zyh.com.yuekaolianxi03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zyh.com.adapter.LeftAdapter;
import zyh.com.adapter.RightAdapter;
import zyh.com.bean.BeanString;
import zyh.com.core.MyInterface;
import zyh.com.presenter.Basepresenter;

public class MainActivity extends AppCompatActivity implements MyInterface {

    ArrayList<BeanString.DataBean> listLeft = new ArrayList<>();
    ArrayList<BeanString.DataBean.ListBean> listRight = new ArrayList<>();
    Basepresenter basepresenter = new Basepresenter(this);
    private RecyclerView mLeftRecycler;
    private RecyclerView mRightRecycler;
    private TextView mSumPrice;
    private TextView mCount;
    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取控件
        mSumPrice = findViewById(R.id.goods_sum_price);
        mCount = findViewById(R.id.goods_number);
        mLeftRecycler = findViewById(R.id.left_recycler);
        mRightRecycler = findViewById(R.id.right_recycler);

        mLeftRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRightRecycler.setLayoutManager(new LinearLayoutManager(this));

        mLeftAdapter = new LeftAdapter(listLeft, this);
        mLeftAdapter.setOnItemClickListenter(new LeftAdapter.OnItemClickListenter() {
            @Override
            public void onItemClick(BeanString.DataBean list) {
                listRight.clear();//清空数据
                listRight.addAll(list.getList());
                mRightAdapter.notifyDataSetChanged();
            }
        });

        mLeftRecycler.setAdapter(mLeftAdapter);
        mRightAdapter = new RightAdapter(listRight, this);
        mRightAdapter.setOnNumListenter(new RightAdapter.OnNumListenter() {
            @Override
            public void onItemClick() {
                allPrice(listLeft);
            }
        });
        mRightRecycler.setAdapter(mRightAdapter);


        basepresenter.getrepuse();
    }

    @Override
    public void initData(BeanString beanJson) {

        listLeft.addAll(beanJson.getData());//左边的添加类型
        List<BeanString.DataBean> data = beanJson.getData();
        //得到默认选中的shop，设置上颜色和背景
        BeanString.DataBean dataBean = data.get(1);
        dataBean.setTextColor(0xff000000);
        dataBean.setBackground(R.color.white);
        listRight.addAll(dataBean.getList());
        //刷新适配器


    }
    //计算价格
    public void allPrice(ArrayList<BeanString.DataBean> beanString){
        double totalPrice=0;
        int totalNum=0;
        for (int i = 0; i < beanString.size(); i++) {
            BeanString.DataBean dataBean = beanString.get(i);
            for (int j = 0; j < dataBean.getList().size(); j++) {
                BeanString.DataBean.ListBean listBean = dataBean.getList().get(j);
                totalPrice = totalPrice + listBean.getNum() * listBean.getNum();
                totalNum+=listBean.getNum();
            }
        }
        mSumPrice.setText("价格"+totalPrice);
        mCount.setText(totalNum+"");
    }//计算价格

    @Override
    protected void onDestroy() {
        super.onDestroy();
        basepresenter.nuPresenter();
    }
}
