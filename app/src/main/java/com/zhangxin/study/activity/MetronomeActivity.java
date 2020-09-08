package com.zhangxin.study.activity;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 节拍器
 */
public class MetronomeActivity extends BaseActivity {


    @BindView(R.id.edit_sudu)
    EditText editSudu;

    @BindView(R.id.edit_paishu)
    EditText editPaishu;

    @BindView(R.id.seekBar)
    SeekBar seekBar;

    @BindView(R.id.btnStar)
    TextView btnStar;

    @BindView(R.id.btnStop)
    TextView btnStop;

    @BindView(R.id.tvResult)
    TextView tvResult;

    public float tempo;
    public int section;
    public int pp;
    Handler handler;
    Timer mytimer;
    private SoundPool sndHigh;
    private SoundPool sndLow;
    private int hitOfHigh;
    private int hitOfLow;

    //声音控制
    private AudioManager audioManager;
    //声音变量
    private int volume = 0;
    //是否有声音
    private int flag = 1;

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_metronome;
    }

    @Override
    protected void initView() {
        pp = 1;
        initHandler();
        initSeekBar();
//        volume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
//        //把当前音量的值 设置给进度条
//        seekBar.setProgress(volume);
        sndHigh = new  SoundPool( 10 , AudioManager.STREAM_SYSTEM, 5 );
        //载入音频流
        hitOfHigh = sndHigh.load(this, R.raw.high, 0 );
        sndLow = new  SoundPool( 10 , AudioManager.STREAM_SYSTEM, 5 );
        //载入音频流
        hitOfLow = sndLow.load(this, R.raw.low, 0 );
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        tvResult.setText(pp + "");
                        if (pp == 1) {
                            //播放音频，可以对左右音量分别设置，还可以设置优先级，循环次数以及速率
                            sndHigh.play(hitOfHigh, 1, 1, 0, 0, 1);
                        } else {
                            sndLow.play(hitOfLow, 1, 1, 0, 0, 1);
                        }
                        if (pp != section) {
                            pp++;
                        } else {
                            pp = 1;
                        }
                        break;
                }
            }
        };
    }

    private void initSeekBar() {
        seekBar.setMax(7);
        seekBar.setProgress(5);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnStar, R.id.btnStop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnStar:
                tempo = Float.parseFloat(editSudu.getText().toString());
                section = Integer.parseInt(editPaishu.getText().toString());
                mytimer = new Timer();
                float tempFloat = 60 / tempo * 1000;
                mytimer.schedule(new MyTimerTask(), 0, (long) tempFloat);
                break;
            case R.id.btnStop:
                mytimer.cancel();
                sndHigh.pause(hitOfHigh);
                sndLow.pause(hitOfLow);
                break;
        }
    }
}
