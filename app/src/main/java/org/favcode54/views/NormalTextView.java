package org.favcode54.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Intija on 10/29/2016.
 */
public class NormalTextView extends android.support.v7.widget.AppCompatTextView {
    public NormalTextView(Context context) {
        super(context);
        applyTypeface(context,"fonts/OpenSans-Regular.ttf");
    }

    private void applyTypeface(Context context, String d) {
        Typeface tr = Typeface.createFromAsset(getContext().getAssets(),d);
        setTypeface(tr);
    }

    public NormalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyTypeface(context,"fonts/OpenSans-Regular.ttf");
    }

    public NormalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyTypeface(context,"fonts/OpenSans-Regular.ttf");
    }


}
