package demo.lieeber.com.ripplelayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.liuguangqiang.ripplelayout.Point;
import com.liuguangqiang.ripplelayout.Ripple;
import com.liuguangqiang.ripplelayout.RippleLayout;

import demo.lieeber.com.ripplelayout.RippleView.OnStateChangeListener;

import static com.liuguangqiang.ripplelayout.Ripple.ARG_START_LOCATION;

public class ThirdActivity extends AppCompatActivity {

    private LinearLayout layoutTop;
    private LinearLayout layoutBottom;
    private RippleView rippleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        Point point = bundle.getParcelable(Ripple.ARG_START_LOCATION);
        setContentView(R.layout.activity_third);
        initToolbar();
        rippleLayout = (RippleView) findViewById(R.id.ripple);
        rippleLayout.setPoint(point);
        layoutTop = (LinearLayout) findViewById(R.id.layout_top);
        layoutBottom = (LinearLayout) findViewById(R.id.layout_bottom);
        rippleLayout.post(new Runnable() {
            @Override public void run() {
                rippleLayout.start();
            }
        });

        layoutTop.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            @Override public boolean onPreDraw() {
                layoutTop.getViewTreeObserver().removeOnPreDrawListener(this);
                layoutTop.setTranslationY(-layoutTop.getHeight());
                layoutBottom.setTranslationY(layoutBottom.getHeight());
                return true;
            }
        });
        rippleLayout.setOnStateChangeListener(new OnStateChangeListener() {
            @Override public void onOpen() {
                startIntoAnimation();
            }

            @Override public void onClose() {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    private void startIntoAnimation() {
        layoutTop.animate().translationY(0).setDuration(400).setInterpolator(new DecelerateInterpolator());
        layoutBottom.animate().translationY(0).setDuration(400).setInterpolator(new DecelerateInterpolator());
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_target_activity);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.ActionBar_Title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_close_white);
    }

    @Override public void onBackPressed() {
        if (rippleLayout.isAnimationEnd()) {
            startOutAnimation();
            rippleLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rippleLayout.back();
                }
            }, 300);
        }
    }

    private void startOutAnimation() {
        layoutTop.animate()
                .translationY(-layoutTop.getHeight())
                .alpha(0.0f)
                .setDuration(400)
                .setInterpolator(new DecelerateInterpolator());

        layoutBottom.animate()
                .translationY(layoutBottom.getHeight())
                .alpha(0.0f)
                .setDuration(400)
                .setInterpolator(new DecelerateInterpolator());
    }
}
