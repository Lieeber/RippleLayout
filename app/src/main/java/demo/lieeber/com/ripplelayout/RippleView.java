package demo.lieeber.com.ripplelayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.liuguangqiang.ripplelayout.Point;

import static android.support.v7.widget.AppCompatDrawableManager.get;

/**
 * Created by lieeber on 16/11/15.
 */

public class RippleView extends View {

    private Paint mPaint;

    float radius = 0;
    private int x;
    private int y;
    private boolean animationEnd;

    public RippleView(Context context) {
        super(context);
        initView();
    }

    public void start() {
        double a = getHeight();
        double b = getWidth();

        ValueAnimator animator = ValueAnimator.ofFloat(0, (float) Math.sqrt(a * a + b * b));
        animator.setDuration(1000);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                radius = (float) valueAnimator.getAnimatedValue();
                invalidate();

            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                animationEnd = false;
                if (listener != null) {
                    listener.onOpen();
                }
            }

            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                animationEnd = true;

            }
        });
        animator.start();
    }

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void back() {
        double a = getHeight();
        double b = getWidth();
        ValueAnimator animator = ValueAnimator.ofFloat((float) Math.sqrt(a * a + b * b), 0);
        animator.setDuration(1000);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                radius = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                animationEnd = false;
            }

            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animationEnd = true;
                if (listener != null) {
                    listener.onClose();
                }
            }
        });
        animator.start();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(Color.parseColor("#123456"));
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, radius, mPaint);
    }

    public void setPoint(Point point) {
        x = point.x;
        y = point.y;

    }

    public boolean isAnimationEnd() {

        return animationEnd;
    }


    public interface OnStateChangeListener {
        void onOpen();

        void onClose();
    }

    private OnStateChangeListener listener;

    public void setOnStateChangeListener(OnStateChangeListener listener) {
        this.listener = listener;
    }
}
