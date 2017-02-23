package com.android.debounce;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = MainActivity.class.getName();
    private static final int RESET_TIMER = 0;
    private static final int HIT_API = 1;

    private static final int DEBOUNCE_PERIOD = 1000;
    private int apiHitCount = 0;

    private TextView debounceIndicator;
    private EditText searchInput;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RESET_TIMER:
                    removeMessages(HIT_API);
                    sendEmptyMessageDelayed(HIT_API, DEBOUNCE_PERIOD);
                    break;
                case HIT_API:
                    debounceIndicator.setText(" api hit count " + ++apiHitCount);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = (EditText) findViewById(R.id.search_input);
        debounceIndicator = (TextView) findViewById(R.id.debounce_indicator);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    handler.sendEmptyMessage(RESET_TIMER);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

}
