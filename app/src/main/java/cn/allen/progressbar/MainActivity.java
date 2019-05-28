package cn.allen.progressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.widget.SeekBar;

import cn.allen.progressbarview.CircleProgressBar;

public class MainActivity extends AppCompatActivity {

    private CircleProgressBar mCircleProgressBar;

    private SeekBar mSeekBar;

    private AppCompatEditText mEtProgress;

    private AppCompatButton mBtnProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircleProgressBar = findViewById(R.id.circleProgressBar);
        mSeekBar = findViewById(R.id.seekBar);
        mEtProgress = findViewById(R.id.etProgress);
        mBtnProgress = findViewById(R.id.btnProgress);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCircleProgressBar.setCurrentProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mBtnProgress.setOnClickListener(v -> {
            mCircleProgressBar.setCurrentProgress(Float.parseFloat(mEtProgress.getText().toString()), 500L);
        });
    }
}
