package app.my.com.definedviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by AOW on 2018/3/20.
 * 在onMeasure()已经实现了宽高尺寸相等的基础上,实现圆形
 * 视图的getLeft() ， getTop() ， getRight() ， getBottom() 的值是针对其父视图的相对的坐标位置
 * getMeasuredHeight()是实际View的大小，与屏幕无关，getHeight（）的大小此时则是屏幕的大小。
 */

public class DrawRect extends View {

    private int defalutSize;
    public DrawRect(Context context) {
        super(context);
    }

    // 通过AttributeSet可以获得布局文件中定义的所有属性的key和value
//    AttributeSet中的确保存的是该View声明的所有的属性，并且外面的确可以通过它去获取（自定义的）属性
    public DrawRect(Context context, @Nullable AttributeSet attrs) {
        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable+name
        super(context, attrs);

        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.DrawRect);
        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        defalutSize = ta.getDimensionPixelSize(R.styleable.DrawRect_default_size,100);
        //关于自定义属性 可参看博客 http://blog.csdn.net/lmj623565791/article/details/45022631/
//        String text=ta.getString(R.styleable.DrawRect_text);
//        Log.e("xsm", "text = " + text);
//        activity_second.xml   打印结果 text = lyh爱你 ^_^
//       最后记得将TypedArray对象回收
        ta.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //调用父View的onDraw函数，因为View这个类帮我们实现了一些
        // 基本的而绘制功能，比如绘制背景颜色、背景图片等
        //半径
        int r=getMeasuredHeight()/2;
        ////圆心的横坐标为当前的View的左边起始位置+半径
        int currentX=getLeft()+r;
        //圆心的纵坐标为当前的View的顶部起始位置+半径
        int currentY=getTop()+r;
//        int r=100;
//        int currentX=50+r;
//        int currentY=50+r;

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        canvas.drawCircle(currentX,currentY,r,paint);
    }

//    需求：将当前的View以正方形的形式显示，即要宽高相等，并且默认的宽高值为100像素。
//    onMeasure()一般用来确定当前view的宽高，并根据宽高等计算一些坐标默认的值。
    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMySize(defalutSize,heightMeasureSpec);
        int width = getMySize(defalutSize,widthMeasureSpec);

        if(height>width)
            height = width;
        else
            width = height;
        setMeasuredDimension(width,height);

    }

    private int getMySize(int defaultsize,int mearsureSpec){
        int defsize=defaultsize;
        int size = MeasureSpec.getSize(mearsureSpec);
        int mode = MeasureSpec.getMode(mearsureSpec);
        switch (mode){
            //UNSPECIFIED 父容器没有对当前View有任何限制，当前View可以任意取尺寸
            //如果没有指定大小，就设置为默认大小
            case MeasureSpec.UNSPECIFIED:
                defsize = defaultsize;
                break;
            //{固定尺寸（如100dp）,match_parent}-->EXACTLY 当前的尺寸就是当前View应该取的尺寸
            //如果是固定的大小，那就不要去改变它
            case MeasureSpec.EXACTLY:
                defsize = size;
                break;
            //wrap_content-->AT_MOST 当前尺寸是当前View能取的最大尺寸
            //如果测量模式是最大取值为size
            //我们将大小取最大值,你也可以取其他值
            case MeasureSpec.AT_MOST:
                defsize = size;
                break;
            default:
                break;
        }
        return defsize;
    }
}
