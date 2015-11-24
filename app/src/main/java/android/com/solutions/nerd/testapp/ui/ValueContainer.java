package android.com.solutions.nerd.testapp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by mookie on 11/24/15.
 * for Nerd.Solutions
 */
public class ValueContainer extends LinearLayout {

    private final TextView labelText;
    private final TextView valueText;

    public ValueContainer(Context context) {
        super(context);
        labelText = new TextView(context);
        valueText = new TextView(context);
        initialize();
    }

    public ValueContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        labelText = new TextView(context);
        valueText = new TextView(context);
        initialize();
    }

    public ValueContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        labelText = new TextView(context);
        valueText = new TextView(context);
        initialize();

    }

    public ValueContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        labelText = new TextView(context);
        valueText = new TextView(context);
        initialize();
    }

    private void initialize() {
        this.setOrientation(HORIZONTAL);
        this.addView(labelText);
        this.addView(valueText);
    }


}
