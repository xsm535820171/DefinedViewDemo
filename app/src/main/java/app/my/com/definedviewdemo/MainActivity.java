package app.my.com.definedviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.EventBase;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 自定义控件的实现有三种方式，分别是：自绘控件、组合控件和继承控件。
 *
 *
 * 自绘控件：自绘控件的内容都是自己绘制出来的，在View的onDraw方法中完成绘制
 *         绘制基本上由measure()、layout()、draw()这个三个函数完成
 *
 * 组合控件：顾名思义就是将一些小的控件组合起来形成一个新的控件，这些小的控件多是系统自带的控件。
 *
 * 继承控件：就是继承已有的控件，创建新控件，保留继承的父控件的特性，并且还可以引入新特性。
 * 功能：支持横向滑动删除列表项的自定义ListView的实现
 * */

public class MainActivity extends AppCompatActivity {
    //快速把libs下的jar包添加到build.gradle,右键libs下边的jar包，选择add as library...

    @ViewInject(R.id.main_ll)
    LinearLayout main_ll;
    @ViewInject(R.id.first_bt)
    Button first_bt;
    @ViewInject(R.id.second_bt)
    Button second_bt;
    @ViewInject(R.id.third_bt)
    Button third_bt;
    @ViewInject(R.id.five_bt)
    Button five_bt;

    @OnClick({R.id.first_bt,R.id.second_bt,R.id.third_bt,R.id.four_bt,R.id.five_bt})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.first_bt:
                Intent intent1 = new Intent(MainActivity.this,FirstActivity.class);
                startActivity(intent1);
                break;
            case R.id.second_bt:
                Intent intent2 = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent2);
                break;
            case R.id.third_bt:
                Intent intent3 = new Intent(MainActivity.this,ThirdActivity.class);
                startActivity(intent3);
                break;

            case R.id.four_bt:
                Intent intent4 = new Intent(MainActivity.this,FourActivity.class);
                startActivity(intent4);
                break;
            case R.id.five_bt:
                Intent intent5 = new Intent(MainActivity.this,FivaActivity.class);
                startActivity(intent5);
                break;
            default:
                break;


        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
    }
}
