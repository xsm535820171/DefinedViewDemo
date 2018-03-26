package app.my.com.definedviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by AOW on 2018/3/20.
 * xml布局页面
 */

public class FirstActivity extends Activity {
    @ViewInject(R.id.drawView)
    DrawView drawView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一种xml控制布局
        setContentView(R.layout.activity_first);
        ViewUtils.inject(this);
        drawView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//          修改组件的currentX和currentY的两个属性 motionEvent.getX()手指触屏坐标
                drawView.currentX = motionEvent.getX();
                drawView.currentY = motionEvent.getY();
                //通知组件重绘
                drawView.invalidate();
                return true;
            }
        });
//        第二种java控制布局
//        LinearLayout first_ll = new LinearLayout(this);
//        first_ll.setOrientation(LinearLayout.VERTICAL);
//        setContentView(first_ll);
//        final DrawView drawView = new DrawView(this);
//        drawView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
////          修改组件的currentX和currentY的两个属性 motionEvent.getX()手指触屏坐标
//                drawView.currentX = motionEvent.getX();
//                drawView.currentY = motionEvent.getY();
//                //通知组件重绘
//                drawView.invalidate();
//                return true;
//            }
//        });
//        first_ll.addView(drawView);
    }
}
