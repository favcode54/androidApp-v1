package org.favcode54.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Abdrex on 1/16/2017.
 */
public class SemiBoldTextView extends android.support.v7.widget.AppCompatTextView{
    public SemiBoldTextView(Context context) {
        super(context);
        applyTypeface(context,"fonts/OpenSans-SemiBold.ttf");

    }

    private void applyTypeface(Context context, String d) {
        Typeface tr = Typeface.createFromAsset(context.getAssets(),d);
        setTypeface(tr);
    }

    public SemiBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyTypeface(context,"fonts/OpenSans-SemiBold.ttf");
    }

    public SemiBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyTypeface(context,"fonts/OpenSans-SemiBold.ttf");
    }
}
