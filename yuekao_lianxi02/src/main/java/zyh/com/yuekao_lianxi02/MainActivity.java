package zyh.com.yuekao_lianxi02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zyh.com.adapter.MyAdapter;
import zyh.com.bean.BeanJson;
import zyh.com.core.MyInterface;
import zyh.com.presenter.Basepresenter;

public class MainActivity extends AppCompatActivity implements MyInterface,MyAdapter.TotalPriceListener {

    Basepresenter basepresenter = new Basepresenter(this);
    ArrayList<BeanJson.DataBean> list = new ArrayList<>();
    private ExpandableListView mGoodsList;
    private TextView mSumPrice;
    private CheckBox mCheckAll;
    private MyAdapter myAdapter;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取控件
        mGoodsList = findViewById(R.id.list_cart);
        mSumPrice = findViewById(R.id.goods_sum_price);
        mCheckAll = findViewById(R.id.check_all);
        button = findViewById(R.id.button_tiaoguo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TwoActivity.class));
            }
        });

        //设置全选
        mCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myAdapter.checkAll(isChecked);
            }
        });
        //适配器
        myAdapter = new MyAdapter(list, this);
        mGoodsList.setAdapter(myAdapter);
        myAdapter.setTotalPriceListener(this);//设置总价回调器

        mGoodsList.setGroupIndicator(null);
        mGoodsList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        basepresenter.getrepuse();

    }

    @Override
    public void initData(BeanJson beanJson) {
        List<BeanJson.DataBean> data = beanJson.getData();
        list.addAll(data);

        int size = list.size();
        for (int i = 0; i < size; i++) {
            mGoodsList.expandGroup(i);
        }

        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void totalPrice(double allprice) {
        String s = String.valueOf(allprice);
        mSumPrice.setText(s);
    }


}
