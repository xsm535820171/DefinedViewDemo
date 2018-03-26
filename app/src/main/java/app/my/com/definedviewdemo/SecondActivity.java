package app.my.com.definedviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

/**
 * Created by AOW on 2018/3/20.
 *
 */

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UI布局界面
        setContentView(R.layout.activity_second);
//        采用java布局界面
//        LinearLayout linearLayout = new LinearLayout(this);
//        setContentView(linearLayout);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//
//        DrawRect drawRect = new DrawRect(this);
//        linearLayout.addView(drawRect);


    }
}
