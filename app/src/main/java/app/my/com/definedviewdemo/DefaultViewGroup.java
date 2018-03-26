package app.my.com.definedviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by AOW on 2018/3/20.
 * 具体的案例：将子View按从上到下垂直顺序一个挨着一个摆放，即模仿实现LinearLayout的垂直布局
 *
 * 思路：
 * 1.首先，我们得知道各个子View的大小吧，只有先知道子View的大小，我们才知道当前的ViewGroup该设置为多大去容纳它们。
 2.根据子View的大小，以及我们的ViewGroup要实现的功能，决定出ViewGroup的大小
 3.ViewGroup和子View的大小算出来了之后，接下来就是去摆放了吧，具体怎么去摆放呢？这得根据你定制的需求去摆放了，比如，你想让子View按照垂直顺序一个挨着一个放，或者是按照先后顺序一个叠一个去放，这是你自己决定的。
 4.已经知道怎么去摆放还不行啊，决定了怎么摆放就是相当于把已有的空间"分割"成大大小小的空间，每个空间对应一个子View，我们接下来就是把子View对号入座了，把它们放进它们该放的地方去。
 */

public class DefaultViewGroup extends ViewGroup{
    public DefaultViewGroup(Context context) {
        super(context);
    }

    public DefaultViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /***
     * 将所有子View的高度相加
     **/
    private int getTotalHeight(){
        int count=getChildCount();
        int height=0;
        for (int i=0;i<count;count++){
            View childView = getChildAt(i);
            height=height+childView.getMeasuredHeight();
        }
        return height;
    }
    /***
     * 获取子View中宽度最大的值
     */
    private int getMaxWidth(){
        int count=getChildCount();
        int maxWidth=0;
        for(int i=0;i<count;i++){
            View childView1 = getChildAt(i);
            if(childView1.getMeasuredWidth()>maxWidth){
                maxWidth = childView1.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

//    重写onMeasure，实现测量子View大小以及设定ViewGroup的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//      将所有的子View进行测量，这会触发每个子View的onMeasure函数
//      注意要与measureChild区分，measureChild是对单个view进行测量
//      计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//      getChildCount（）方法返回的是直接子元素的个数，不包含子元素内部包含的元素个数
//        只有ViewGroup类（及其子类如常用的LinearLayout，RelativeLayout等）才有这个方法
        int childCount = getChildCount();
//      如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
        if(childCount==0){
            setMeasuredDimension(0,0);
        }else {
//            UNSPECIFIED,AT_MOST,EXACTLY见DrawRect.java
            //如果宽高都是包裹内容
            if(heightMode==MeasureSpec.AT_MOST && widthMode==MeasureSpec.AT_MOST){
                //获取所有字view的高度
                int height = getTotalHeight();
                int width = getMaxWidth();
                setMeasuredDimension(width,height);
            }else if (heightMode==MeasureSpec.AT_MOST){//如果只有高度是包裹内容
                //宽度设置为ViewGroup自己的测量宽度，高度设置为所有子View的高度总和
                setMeasuredDimension(widthSize,getTotalHeight());
            }else if(widthMode == MeasureSpec.AT_MOST){
                setMeasuredDimension(getMaxWidth(),heightSize);
            }
//            else {
//                setMeasuredDimension(widthSize,heightSize);
//            }

        }

    }

    //    为子组件分配位置，大小，
    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        int count = getChildCount();
        //记录当前的高度位置
        int currentHeight = t;
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            int width = childView.getMeasuredWidth();
            int height = childView.getMeasuredHeight();
//          参数分别是子View矩形区域的左、上、右、下边
            childView.layout(1,currentHeight,1+width,currentHeight+height);
            currentHeight=currentHeight+height;
        }

    }
}
