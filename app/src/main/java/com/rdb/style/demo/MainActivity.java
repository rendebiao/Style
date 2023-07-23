package com.rdb.style.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.rdb.style.State;
import com.rdb.style.Style;
import com.rdb.style.Theme;
import com.rdb.style.VariableColor;
import com.rdb.style.widget.Button;
import com.rdb.style.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = Style.getCurTheme().getBundle();
        final int styleId = Style.getCurTheme().getStyleId();
        int fullscreenStyleId = bundle.getInt("fullscreenStyleId");
        int translucentStyleId = bundle.getInt("translucentStyleId");
        int translucentFullscreenStyleId = bundle.getInt("translucentFullscreenStyleId");
        setTheme(styleId);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.button);
        final TextView text = findViewById(R.id.text);
        final VariableColor pressColor = new VariableColor(State.STATES_DISABLE_SELECTED_PRESSED_OTHER, true) {
            @Override
            protected void updateColor(int[] colors) {
                colors[0] = Style.getDisableColor();
                colors[1] = Style.getCurTheme().getColor();
                colors[2] = Style.getCurTheme().getColor();
                colors[3] = Style.getForegroundColor();
            }
        };
        final VariableColor themeColor = new VariableColor(State.STATES_DISABLE_OTHER, true) {
            @Override
            protected void updateColor(int[] colors) {
                colors[0] = Style.getDisableColor();
                colors[1] = Style.getCurTheme().getColor();
            }
        };
        button.setTextColor(pressColor);
        text.setTextColor(themeColor);
        button.setOnClickListener(new View.OnClickListener() {
            int i = 0;

            @Override
            public void onClick(View v) {
                int count = Style.getThemes().length;
                Theme theme = Style.getThemes()[i % count];
                Style.apply(theme);
                i++;
            }
        });
    }
}