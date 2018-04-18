package org.favcode54.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Abdrex on 1/16/2017.
 */
public class BoldTextView extends android.support.v7.widget.AppCompatTextView{
    public BoldTextView(Context context) {
        super(context);
        applyTypeface(context,"fonts/OpenSans-Bold.ttf");

    }

    private void applyTypeface(Context context, String d) {
        Typeface tr = Typeface.createFromAsset(context.getAssets(),d);
        setTypeface(tr);
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyTypeface(context,"fonts/OpenSans-Bold.ttf");
    }

    public BoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyTypeface(context,"fonts/OpenSans-Bold.ttf");
    }
}
