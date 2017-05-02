package com.herprogramacion.movielife.utilities;
import android.content.Context;
import android.graphics.Color;
import android.preference.Preference;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.herprogramacion.movielife.R;

public class SeekBarPreference extends Preference implements SeekBar.OnSeekBarChangeListener {
    private static final String ANDROIDNS = "http://schemas.android.com/apk/res/android";
    private static final String MYPREFERENCESNS = "com.hermosaprogramacion.movielife.seekbar";

    private static final int NULL = -1;
    private static final int DEFAULT_MIN_VALUE = 1;
    private static final int DEFAULT_MAX_VALUE = 100;

    private static final int LAYOUT_PADDING = R.dimen.preference_padding;
    private static final int SEEK_BAR_PADDING = 20;

    private SeekBar seekBar;
    private TextView titleText, valueText;
    private ImageView iconImage;
    private Context context;

    private int titleId, textId, iconId;
    private int maxValue, minValue, currentValue;

    public SeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        titleId = attrs.getAttributeResourceValue(ANDROIDNS, "title", NULL);
        textId = attrs.getAttributeResourceValue(ANDROIDNS, "text", NULL);
        iconId = attrs.getAttributeResourceValue(ANDROIDNS, "icon", NULL);
        maxValue = attrs.getAttributeIntValue(MYPREFERENCESNS, "maxValue", DEFAULT_MAX_VALUE);
        minValue = attrs.getAttributeIntValue(MYPREFERENCESNS, "minValue", DEFAULT_MIN_VALUE);
        currentValue = getPersistedInt(DEFAULT_MIN_VALUE);
    }

    ////////////////////////////////Preference methods/////////////////////////////////
    @Override
    protected View onCreateView(ViewGroup parent) {
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
        int padding = (int) getContext().getResources().getDimension(LAYOUT_PADDING);
        mainLayout.setGravity(Gravity.CENTER_VERTICAL);
        mainLayout.setPadding(padding * 2, padding, padding, padding);

        iconImage = new ImageView(context);
        if (iconId != NULL) {
            iconImage.setImageDrawable(ContextCompat.getDrawable(context, iconId));
            iconImage.setColorFilter(Color.GRAY);
        }
        mainLayout.addView(iconImage);

        RelativeLayout secondaryLayout = new RelativeLayout(context);
        secondaryLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        secondaryLayout.setPadding(padding * 2, padding, padding, padding);
        mainLayout.addView(secondaryLayout);

        titleText = new TextView(context);
        titleText.setId(1234);
        if (titleId != NULL) {
            titleText.setText(titleId);
            titleText.setTextColor(Color.BLACK);
            titleText.setPadding(SEEK_BAR_PADDING, 0, 0, 0);
        }
        RelativeLayout.LayoutParams titleTextParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleTextParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        secondaryLayout.addView(titleText, titleTextParams);

        valueText = new TextView(context);
        valueText.setId(2345);
        valueText.setGravity(Gravity.END);
        valueText.setPadding(0, 10, 0, 0);
        RelativeLayout.LayoutParams valueTextParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        valueTextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        valueTextParams.addRule(RelativeLayout.BELOW, titleText.getId());
        secondaryLayout.addView(valueText, valueTextParams);

        seekBar = new SeekBar(context);
        seekBar.setPadding(SEEK_BAR_PADDING, 10, SEEK_BAR_PADDING, 0);
        RelativeLayout.LayoutParams seekBarParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        seekBarParams.addRule(RelativeLayout.BELOW, titleText.getId());
        seekBarParams.addRule(RelativeLayout.LEFT_OF, valueText.getId());
        seekBarParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        secondaryLayout.addView(seekBar, seekBarParams);

        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(maxValue - minValue);
        seekBar.setProgress(currentValue - minValue);

        return mainLayout;
    }

    @Override
    protected void onBindView (View view){
        super.onBindView(view);
        seekBar.setMax(maxValue - minValue);
        seekBar.setProgress(currentValue - minValue);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue){
        if (restoreValue){
            currentValue = shouldPersist() ? getPersistedInt(currentValue) : 0;
        } else {
            currentValue = (Integer) defaultValue;
        }
    }

    /////////////////////////OnSeekBarChangeListener implementation/////////////////////////
    @Override
    public void onProgressChanged(SeekBar seek, int value, boolean fromTouch) {
        if (fromTouch) {
            currentValue = value + minValue;
            String sValue = String.valueOf(currentValue);
            valueText.setText(textId == NULL ? sValue : sValue.concat(" ").concat(getContext().getString(textId)));
        } else {
            valueText.setText(textId == NULL ? String.valueOf(currentValue) :
                    String.valueOf(currentValue).concat(" ").concat(getContext().getString(textId)));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seek) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seek) {
        if (shouldPersist()) {
            persistInt(currentValue);
        }
    }
}
