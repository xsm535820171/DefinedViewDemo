package app.my.com.definedviewdemo;

import android.content.Context;
import android.gesture.Gesture;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.gesture.GestureOverlayView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by AOW on 2018/3/21.
 * 继承控件：就是继承已有的控件，创建新控件，保留继承的父控件的特性，并且还可以引入新特性。
 * 功能：支持横向滑动删除列表项的自定义ListView的实现
 *
 * View类有个View.OnTouchListener内部接口，通过重写他的onTouch(View v, MotionEvent event)方法，
 * 我们可以处理一些touch事件，但是这个方法太过简单，如果需要处理一些复杂的手势，用这个接口就会很麻烦
 * 所以，Android sdk给我们提供了GestureDetector（Gesture：手势Detector：识别）类，
 * 通过这个类我们可以识别很多的手势，主要是通过他的onTouchEvent(event)方法完成了不同手势的识别。
 * GestureDetector这个类对外提供了两个接口和一个外部类接口：OnGestureListener，OnDoubleTapListener，
 * 内部类:SimpleOnGestureListener
 *
 * 要使用GestureDetector，有三步要走：
 * 1、创建OnGestureListener监听函数：
 * 2、创建GestureDetector实例mGestureDetector：
 * 3、onTouch(View v, MotionEvent event)中拦截：
 */

public class DefaultExtendsView extends ListView implements View.OnTouchListener,GestureDetector.OnGestureListener {

    // 设置删除监听事件
    public void setOnDeleteListener(OnDeleteListener deleteListener){
        mOnDeleteListener = deleteListener;
    }

    private OnDeleteListener mOnDeleteListener;
    // 删除按钮
    private View bt_delete;
    // 列表项布局
    private ViewGroup mItemLayout;
    // 选择的列表项
    private int mSeleteItem;
    // 删除按钮  默认 不显示
    private boolean isDeleteShown=false;
    // 手势动作探测器
    private GestureDetector gestureDetector;

    public DefaultExtendsView(Context context) {
        super(context);
    }

    public DefaultExtendsView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        创建GestureDetector实例mGestureDetector
        gestureDetector = new GestureDetector(getContext(), (GestureDetector.OnGestureListener) this);
        setOnTouchListener(this);
    }
    /**
     * 用户按下屏幕就会触发
     pointToPosition(int x, int y)通过x和y的位置来确定这个listView里面这个item的位置

     思路 根据当前点击的item的id和之前选择的item的id，还有删除按钮布局是否显示，
      判断是隐藏删除按钮 还是把mSeleteItem = currentSeleteItem;
     * */

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.e("xsm","onDown");
        //currentSeleteItem 现在点击条目编号
        int currentSeleteItem = pointToPosition((int) motionEvent.getX(),(int)motionEvent.getY());
        Log.e("xsm",currentSeleteItem+"=down="+mSeleteItem);
        if(currentSeleteItem == mSeleteItem){
            if(!isDeleteShown){
                mSeleteItem = currentSeleteItem;
            }else {
                hideDelete();
            }
        }
        else {
            if(!isDeleteShown){
                mSeleteItem = currentSeleteItem;
            }else {
                hideDelete();
            }
        }
        return false;
    }
    /**
     * 如果是按下的时间超过瞬间，而且在按下的时候没有松开或者是拖动的，
     那么onShowPress就会执行，具体这个瞬间是多久，自己尝试 哈哈哈……
     */
    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.e("xsm","onShowPress");

    }
/**
* 一次单独的轻击抬起操作
    触发顺序：
    点击一下非常快的（不滑动）Touchup：
    onDown->onSingleTapUp->onSingleTapConfirmed
    点击一下稍微慢点的（不滑动）Touchup：
    onDown->onShowPress->onSingleTapUp->onSingleTapConfirmed*/

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.e("xsm","onSingleTapUp");
        return false;
    }
/**
 * 在屏幕上拖动事件。无论是用手拖动view，或者是以抛的动作滚动，都会多次触发,这个方法
 在ACTION_MOVE动作发生时就会触发
 */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.e("xsm","onScroll");
        return false;
    }
    /***
     * 长按触摸屏，超过一定时长，就会触发这个事件
     触发顺序： onDown->onShowPress->onLongPress
     */
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.e("xsm","onLongPress");
        Toast.makeText(getContext(),"长按长按,,,",Toast.LENGTH_LONG).show();
    }

    /**
     * 滑屏，用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN,
          多个ACTION_MOVE, 1个ACTION_UP触发
          滑屏：手指触动屏幕后，稍微滑动后立即松开
          onDown-----》onScroll----》onScroll----》onScroll----》………----->onFling
          拖动
          onDown------》onScroll----》onScroll------》onFiling


     参数解释：
        motionEvent：第1个ACTION_DOWN MotionEvent
        motionEvent1：最后一个ACTION_MOVE MotionEvent
         v：X轴上的移动速度，像素/秒
         v1：Y轴上的移动速度，像素/秒

     思路 根据当前点击的item的id和之前选择的item的id，还有删除按钮布局是否显示，判断删除按钮是显示还是隐藏
     */

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        int currentSeleteItem = pointToPosition((int) motionEvent.getX(),(int)motionEvent.getY());
        //如果当前滑动item和之前选中的item不相等 隐藏之前的item删除按钮
        Log.e("xsm",currentSeleteItem+"==currentSeleteItem--mSeleteItem"+mSeleteItem+"---isDeleteShown=="+isDeleteShown);
//        判断 当前点击的item的id 不等于之前选择的item的id
        if(currentSeleteItem != mSeleteItem){
            if(isDeleteShown){
                hideDelete();
            }
//            判断 删除按钮是否显示
//            if (!isDeleteShown){
//                  如果在x轴上滑动的距离大于y轴
//                if (Math.abs(v) > Math.abs(v1)) {
//                    bt_delete = LayoutInflater.from(getContext()).inflate(R.layout.delete_button, null);
//                    bt_delete.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            mItemLayout.removeView(bt_delete);
//                            bt_delete = null;
//                            isDeleteShown = false;
//                            mOnDeleteListener.delete(mSeleteItem);
//                        }
//                    });
//                    mItemLayout = (ViewGroup) getChildAt(mSeleteItem - getFirstVisiblePosition());
//                    RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
//                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    param.addRule(RelativeLayout.CENTER_VERTICAL);
//                    mItemLayout.addView(bt_delete, param);
//                    isDeleteShown = true;
//                }
//            }else{
//              hideDelete();
//              }
        }else {
//            如果删除按钮不显示
            if (!isDeleteShown) {
//                如果在x轴上滑动的距离大于y轴
                if (Math.abs(v) > Math.abs(v1)) {
                    bt_delete = LayoutInflater.from(getContext()).inflate(R.layout.delete_button, null);
                    bt_delete.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mItemLayout.removeView(bt_delete);
                            bt_delete = null;
                            isDeleteShown = false;
                            mOnDeleteListener.delete(mSeleteItem);
                        }
                    });
                    mItemLayout = (ViewGroup) getChildAt(mSeleteItem - getFirstVisiblePosition());
                    RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    param.addRule(RelativeLayout.CENTER_VERTICAL);
                    mItemLayout.addView(bt_delete, param);
                    isDeleteShown = true;
                }
            }
        }
        Log.e("xsm","onFling");
        return false;
    }

    public interface OnDeleteListener{
        void delete(int index);

    }

    // 触摸监听事件
    //思路：如果当前点击的item的id不等于之前选择的item的id：
    //     如果不等于：判断删除按钮是否显示，如果没有显示，mSeleteItem=currentSeleteItem;否则，hideDelete();
//         如果等于：判断删除按钮是否显示，如果没有显示，mSeleteItem=currentSeleteItem;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int currentSeleteItem = pointToPosition((int) motionEvent.getX(),(int)motionEvent.getY());
        if(currentSeleteItem != mSeleteItem){
            if(!isDeleteShown){
                mSeleteItem=currentSeleteItem;
                return gestureDetector.onTouchEvent(motionEvent);
            }else {
                hideDelete();
                return false;
            }

        }else {
            if (!isDeleteShown){
                mSeleteItem=currentSeleteItem;
            }
            return gestureDetector.onTouchEvent(motionEvent);
        }
    }
    //隐藏删除按钮
    public void hideDelete(){
        mItemLayout.removeView(bt_delete);
        bt_delete = null;
        isDeleteShown = false;
    }
    //删除按钮是否显示
    public boolean isDeleteShown(){
        return isDeleteShown;
    }
}
