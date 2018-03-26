package app.my.com.definedviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by AOW on 2018/3/21.
 * 　组合控件，顾名思义就是将一些小的控件组合起来形成一个新的控件，这些小的控件多是系统自带的控件。
 */

public class DefaultCombineView extends RelativeLayout {

    private RelativeLayout dcv_rl;
    private ImageView img_back;
    private TextView tv_title;
    private int cTextColor;
    private int cBackgroundColor;
    private String cText;
    public DefaultCombineView(Context context) {
        super(context);
        initView(context);
    }

    public DefaultCombineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeArray(context,attrs);
        initView(context);
    }

    public void setBackClickLister(OnClickListener listener){
        img_back.setOnClickListener(listener);
    }
    public void setDefaultTitle(String title){
        tv_title.setText(title);
    }

    // 通过AttributeSet可以获得布局文件中定义的所有属性的key和value
//    AttributeSet中的确保存的是该View声明的所有的属性，并且外面的确可以通过它去获取（自定义的）属性
    public void initTypeArray(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.DefaultCombineView);

        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        cBackgroundColor = ta.getColor(R.styleable.DefaultCombineView_background_color, Color.BLUE);
        cTextColor = ta.getColor(R.styleable.DefaultCombineView_text_color, Color.RED);
        //获取资源后要及时回收
        ta.recycle();
    }
    public void initView(Context context){
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.combine_title,this);
        dcv_rl = findViewById(R.id.dcv_rl);
        img_back = findViewById(R.id.img_back);
        tv_title = findViewById(R.id.tv_title);
        dcv_rl.setBackgroundColor(cBackgroundColor);
        tv_title.setTextColor(cTextColor);
//        setTitle(cText);
    }
//    public void setTitle(String titlename) {
//        if (!TextUtils.isEmpty(titlename)) {
//            tv_title.setText(titlename);
//        }
//    }
}
