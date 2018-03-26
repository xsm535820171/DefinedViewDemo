package app.my.com.definedviewdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static app.my.com.definedviewdemo.R.id.item_tv;

/**
 * Created by AOW on 2018/3/22.
 */

public class FivaActivity extends Activity {

    List<String> list= new ArrayList<>();
    @ViewInject(R.id.dev_list)
    DefaultExtendsView dev_list;
    ExtendsAdapter extendsAdapter=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        ViewUtils.inject(this);

        for (int i=0;i<20;i++){
            list.add(""+i);
        }
        extendsAdapter = new ExtendsAdapter(this,list);
        dev_list.setAdapter(extendsAdapter);

        dev_list.setOnDeleteListener(new DefaultExtendsView.OnDeleteListener() {
            @Override
            public void delete(int index) {
                list.remove(index);
                extendsAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * ListView优化主要有下面几个方面：

     1、convertView重用
     首先讲下ListView的原理：ListView中的每一个Item显示都需要Adapter调用一次getView()的方法，
     这个方法会传入一个convertView的参数，这个方法返回的View就是这个Item显示的View。如果当Item的数量足够大，
     再为每一个Item都创建一个View对象，必将占用很多内存空间，即创建View对象（mInflater.inflate(R.layout.lv_item, null);
     从xml中生成View，这是属于IO操作）是耗时操作，所以必将影响性能。
     Android提供了一个叫做Recycler(反复循环)的构件，就是当ListView的Item从滚出屏幕视角之外，对应Item的View会被缓存到Recycler中，
     相应的会从生成一个Item，而此时调用的getView中的convertView参数就是滚出屏幕的缓存Item的View，
     所以说如果能重用这个convertView，就会大大改善性能。

     当这个convertView不存在时，即第一次使用它，我们就创建一个item布局的View对象并赋给convertView，以后使用convertView时，
     只需从convertView中getTag取出来就可以，不需要再次创建item的布局对象了，这样便提高了性能。

     2、ViewHolder的子View复用
     我们都知道在getView()方法中的操作是这样的：先从xml中创建view对象（inflate操作，我们采用了重用convertView方法优化），
     然后在这个view去findViewById，找到每一个item的子View的控件对象，如：ImageView、TextView等。
     这里的findViewById操作是一个树查找过程，也是一个耗时的操作，所以这里也需要优化，就是使用ViewHolder，
     把每一个item的子View控件对象都放在Holder中，当第一次创建convertView对象时，
     便把这些item的子View控件对象findViewById实例化出来并保存到ViewHolder对象中。
     然后用convertView的setTag将viewHolder对象设置到Tag中， 当以后加载ListView的item时便可以直接从Tag中取出复用ViewHolder对象中的，
     不需要再findViewById找item的子控件对象了。这样便大大提高了性能。

     3、缓存数据复用
     */
    class ExtendsAdapter extends BaseAdapter{
        private Context mcontext=null;
        private List<String> list = new ArrayList<String>();

        public ExtendsAdapter(Context mcontext, List<String> list) {
            this.mcontext = mcontext;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertview, ViewGroup viewGroup) {

            ViewHolder viewHolder=null;
            if(null == convertview){
                viewHolder = new ViewHolder();
                convertview = LayoutInflater.from(mcontext).inflate(R.layout.list_item,null);
                viewHolder = new ViewHolder();
                ViewUtils.inject(viewHolder,convertview);
                convertview.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertview.getTag();
            }
            viewHolder.item_tv.setText(list.get(i));
            return convertview;
        }

        //ViewHolder，主要是进行一些性能优化，减少一些不必要的重复操作
        class ViewHolder{
            @ViewInject(R.id.item_tv)
            TextView item_tv;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(dev_list.isDeleteShown()){
            dev_list.hideDelete();
            return;
        }
    }
}
