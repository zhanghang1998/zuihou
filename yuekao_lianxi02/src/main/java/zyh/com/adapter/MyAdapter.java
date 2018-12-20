package zyh.com.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import zyh.com.bean.BeanJson;
import zyh.com.view.AddSubView;
import zyh.com.yuekao_lianxi02.R;

public class MyAdapter extends BaseExpandableListAdapter {

    private ArrayList<BeanJson.DataBean> list;
    private Context context;
    private TotalPriceListener totalPriceListener;

    public void setTotalPriceListener(TotalPriceListener totalPriceListener) {
        this.totalPriceListener = totalPriceListener;
    }

    public MyAdapter(ArrayList<BeanJson.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //优化
        GroupViewHoadler groupViewHoadler=null;

        if (convertView == null) {
            groupViewHoadler=new GroupViewHoadler();
            convertView = View.inflate(context,R.layout.group_item, null);
            groupViewHoadler.check01 = convertView.findViewById(R.id.checkBox_group);

            convertView.setTag(groupViewHoadler);

        } else {
            groupViewHoadler = (GroupViewHoadler) convertView.getTag();
        }

        BeanJson.DataBean dataBean = list.get(groupPosition);
        groupViewHoadler.check01.setText(dataBean.getSellerName());

        groupViewHoadler.check01.setChecked(dataBean.isCheck());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //优化
        ChildViewHoadler childViewHoadler=null;

        if (convertView == null) {
            childViewHoadler = new ChildViewHoadler();
            convertView = View.inflate(context,R.layout.child_item, null);
            childViewHoadler.text = convertView.findViewById(R.id.text);
            childViewHoadler.price = convertView.findViewById(R.id.text_price);
            childViewHoadler.image = convertView.findViewById(R.id.image);
            childViewHoadler.addSub = convertView.findViewById(R.id.add_sub_layout);
            childViewHoadler.check = convertView.findViewById(R.id.cart_goods_check);

            convertView.setTag(childViewHoadler);

        } else {
            childViewHoadler = (ChildViewHoadler) convertView.getTag();
        }

        BeanJson.DataBean dataBean = list.get(groupPosition);
        final BeanJson.DataBean.ListBean listBean = dataBean.getList().get(childPosition);
        childViewHoadler.text.setText(listBean.getTitle());
        childViewHoadler.price.setText("单价"+listBean.getPrice());
        //点击选中，计算价格
        childViewHoadler.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listBean.setSelected(isChecked?1:0);
                callPrice();//计算价格
            }
        });

        if (listBean.getSelected() == 0) {
            childViewHoadler.check.setChecked(false);
        } else {
            childViewHoadler.check.setChecked(true);
        }

        String imageurl = "https" + listBean.getImages().split("https")[1];
        Log.i("zyh", "imageUrl: " + imageurl);
        imageurl = imageurl.substring(0, imageurl.lastIndexOf(".jpg") + ".jpg".length());
        Glide.with(context).load(imageurl).into(childViewHoadler.image);//加载图片

        childViewHoadler.addSub.setCount(listBean.getNum());
        childViewHoadler.addSub.setAddSubListener(new AddSubView.AddSubListener() {
            @Override
            public void addSub(int count) {
                listBean.setNum(count);
                callPrice();//计算价格
            }
        });

        return convertView;
    }

    //全部选中或者取消
    public void checkAll(boolean isChecked) {
        for (int i = 0; i < list.size(); i++) {//循环的商家
            BeanJson.DataBean dataBean = list.get(i);
            dataBean.setCheck(isChecked);
            for (int j = 0; j < dataBean.getList().size(); j++) {
                BeanJson.DataBean.ListBean listBean = dataBean.getList().get(j);
                listBean.setSelected(isChecked?1:0);
            }
        }
        notifyDataSetChanged();
        callPrice();
    }

    //计算总价格
    private void callPrice(){
        double totalPrice=0;
        for (int i = 0; i < list.size(); i++) {//循环的商家
            BeanJson.DataBean dataBean = list.get(i);
            for (int j = 0; j < dataBean.getList().size(); j++) {
                BeanJson.DataBean.ListBean listBean = dataBean.getList().get(j);
                if (listBean.getSelected()==1) {//如果是选中状态
                    totalPrice=totalPrice+listBean.getNum()*listBean.getPrice();
                }
            }
        }//价格相加
        if (totalPriceListener!=null) {
            totalPriceListener.totalPrice(totalPrice);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHoadler{
        CheckBox check01;
        TextView text01;
    }

    class ChildViewHoadler{
        CheckBox check;
        TextView text;
        TextView price;
        ImageView image;
        AddSubView addSub;
    }

    public interface TotalPriceListener{

        public void totalPrice(double allprice);

    }
}
