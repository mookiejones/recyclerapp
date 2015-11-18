package android.com.solutions.nerd.testapp.boat;

import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.ui.BaseActivity;
import android.com.solutions.nerd.testapp.utils.LogUtils;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by mookie on 11/3/15.
 * for Nerd.Solutions
 */
public class BoatActivity extends BaseActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean shouldBeFloatingWindow = shouldBeFloatingWindow();


        Log.e(LogUtils.getLogTag(BoatActivity.class),"BoatActivity.onCreate");
        if (shouldBeFloatingWindow) {
            setupFloatingWindow(R.dimen.session_details_floating_width,
                    R.dimen.session_details_floating_height, 1, 0.4f);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.boat_activity);

        final Toolbar toolbar = getActionBarToolbar();
//        toolbar.setNavigationIcon(shouldBeFloatingWindow
        //               ? R.drawable.ic_ab_close : R.drawable.ic_up);
        toolbar.setNavigationContentDescription(R.string.close_and_go_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
