package app.my.com.definedviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by AOW on 2018/3/21.
 */

public class FourActivity extends Activity {
    @ViewInject(R.id.dcv)
    DefaultCombineView dcv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        ViewUtils.inject(this);
        dcv.setDefaultTitle("组合控件");
        dcv.setBackClickLister(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FourActivity.this,"成功了成功了，开心 撒花~~",Toast.LENGTH_LONG).show();
//                finish();
            }
        });

    }
}
