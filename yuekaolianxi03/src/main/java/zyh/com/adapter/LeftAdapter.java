package zyh.com.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import zyh.com.bean.BeanString;
import zyh.com.yuekaolianxi03.R;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.MyHoadler> {

    private OnItemClickListenter onItemClickListenter;
    private ArrayList<BeanString.DataBean> list;
    private Context context;

    public void setOnItemClickListenter(OnItemClickListenter onItemClickListenter) {
        this.onItemClickListenter = onItemClickListenter;
    }

    public LeftAdapter(ArrayList<BeanString.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LeftAdapter.MyHoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.left_list_item, null);
        MyHoadler myHoadler = new MyHoadler(view);
        return myHoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull LeftAdapter.MyHoadler myHoadler, int i) {
        final BeanString.DataBean dataBean = list.get(i);
        myHoadler.textView.setText(dataBean.getSellerName());
        myHoadler.textView.setBackgroundColor(dataBean.getBackground());
        myHoadler.textView.setTextColor(dataBean.getTextColor());

        myHoadler.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).setTextColor(0xffffffff);
                    list.get(j).setBackground(R.color.grayblack);
                }
                dataBean.setBackground(R.color.white);
                dataBean.setTextColor(0xff000000);
                notifyDataSetChanged();
                onItemClickListenter.onItemClick(dataBean);//切换右边的列表
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyHoadler extends RecyclerView.ViewHolder{

        private final TextView textView;

        public MyHoadler(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.left_text);
        }
    }

    public interface OnItemClickListenter{
        public void onItemClick(BeanString.DataBean list);
    }
}
