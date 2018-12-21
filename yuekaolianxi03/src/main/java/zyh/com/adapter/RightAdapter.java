package zyh.com.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import zyh.com.bean.BeanString;
import zyh.com.view.MyView;
import zyh.com.yuekaolianxi03.R;

public class RightAdapter extends RecyclerView.Adapter<RightAdapter.MyHoadler> {

    private OnNumListenter onNumListenter;
    private ArrayList<BeanString.DataBean.ListBean> list;
    private Context context;

    public void setOnNumListenter(OnNumListenter onNumListenter) {
        this.onNumListenter = onNumListenter;
    }

    public RightAdapter(ArrayList<BeanString.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RightAdapter.MyHoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.right_list_item, null);
        MyHoadler myHoadler = new MyHoadler(view);
        return myHoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull RightAdapter.MyHoadler myHoadler, int i) {
        final BeanString.DataBean.ListBean dataBean = list.get(i);

        myHoadler.text.setText(dataBean.getTitle());
        myHoadler.price.setText("单价：" + dataBean.getPrice());//单价

        String imageurl = "https" + dataBean.getImages().split("https")[1];
        Log.i("张雨航", "imageUrl: " + imageurl);
        imageurl = imageurl.substring(0, imageurl.lastIndexOf(".jpg") + ".jpg".length());
        Glide.with(context).load(imageurl).into(myHoadler.image);//加载图片

        myHoadler.addSub.setCount(dataBean.getNum());//设置商品数量
        myHoadler.addSub.setAddSubListenter(new MyView.AddSubListenter() {
            @Override
            public void addSub(int count) {
                dataBean.setNum(count);
                onNumListenter.onItemClick();//计算价格
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyHoadler extends RecyclerView.ViewHolder{

        private final MyView addSub;
        private final TextView text;
        private final TextView price;
        private final ImageView image;

        public MyHoadler(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            price = itemView.findViewById(R.id.text_price);
            image = itemView.findViewById(R.id.image);
            addSub = itemView.findViewById(R.id.add_sub_layout);
        }
    }

    public interface OnNumListenter{
        public void onItemClick();
    }
}
