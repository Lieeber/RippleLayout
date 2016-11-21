package demo.lieeber.com.ripplelayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.liuguangqiang.ripplelayout.Point;
import com.liuguangqiang.ripplelayout.Ripple;

import static com.liuguangqiang.ripplelayout.Ripple.ARG_START_LOCATION;
import static com.liuguangqiang.ripplelayout.Ripple.dip2px;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private FloatingActionButton btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest = (FloatingActionButton) findViewById(R.id.btn_test);
        btnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test:
//                Ripple.startActivity(MainActivity.this, TargetActivity.class, btnTest);

                int[] location = {0, 0};
                btnTest.getLocationOnScreen(location);
                location[1] = location[1] - dip2px(this, 25);
                Point point = new Point(location[0] + btnTest.getWidth() / 2, location[1] + btnTest.getHeight() / 2);

                Intent intent = new Intent(this, ThirdActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_START_LOCATION, point);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
        }
    }

}
