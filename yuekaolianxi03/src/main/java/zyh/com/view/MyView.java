package zyh.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import zyh.com.yuekaolianxi03.R;

public class MyView extends LinearLayout implements View.OnClickListener {


    private TextView mAddBtn;
    private TextView mSubBtn;
    private TextView mNumText;

    public MyView(Context context) {
        super(context);
        initView();
    }

    public MyView(Context context,AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        View view = View.inflate(getContext(), R.layout.car_brn_item, this);
        mAddBtn = view.findViewById(R.id.btn_add);
        mSubBtn = view.findViewById(R.id.btn_sub);
        mNumText = view.findViewById(R.id.text_number);
        mAddBtn.setOnClickListener(this);
        mSubBtn.setOnClickListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int wight= r-1;
        int height=b-t;
    }

    @Override
    public void onClick(View v) {

        int number = Integer.parseInt(mNumText.getText().toString());
        switch (v.getId()) {
            case R.id.btn_add:
                number++;
                mNumText.setText(number+"");
                break;
            case R.id.btn_sub:
                if (number==0){
                    Toast.makeText(getContext(),"数量不能小于0",Toast.LENGTH_LONG).show();
                    return;
                }
                number--;
                mNumText.setText(number+"");
                break;
        }
        if (addSubListenter!=null){
            addSubListenter.addSub(number);
        }

    }

    public void setCount(int count){
        mNumText.setText(count+"");
    }

    private AddSubListenter addSubListenter;

    public void setAddSubListenter(AddSubListenter addSubListenter) {
        this.addSubListenter = addSubListenter;
    }

    public interface AddSubListenter{
        public void addSub(int count);
    }
}
