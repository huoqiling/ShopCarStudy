package com.zhangxin.study.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.utils.LogUtil;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebSocketActivity extends BaseActivity {

    @BindView(R.id.etContent)
    EditText etContent;

    @BindView(R.id.btnSend)
    TextView btnSend;

    @BindView(R.id.tvContent)
    TextView tvContent;

    private WebSocketClient mClient;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tvContent.setText(tvContent.getText().toString() + "\n" + msg.obj);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_socket;
    }

    @Override
    protected void initView() {
        initSocket();
    }

    private void initSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mClient = new WebSocketClient(new URI("ws://kongkong.33333666666.com/ws?token=daqi")) {
                        @Override
                        public void onOpen(ServerHandshake handshakedata) {
                            LogUtil.zhangx("打开通道" + handshakedata.getHttpStatus());
                            handler.obtainMessage(0, handshakedata.getHttpStatusMessage()).sendToTarget();
                        }

                        @Override
                        public void onMessage(String message) {
                            LogUtil.zhangx("接收" + message);
                            handler.obtainMessage(0, message).sendToTarget();
                        }

                        @Override
                        public void onWebsocketPing(WebSocket conn, Framedata f) {
                            super.onWebsocketPing(conn, f);
                            LogUtil.zhangx("onWebsocketPing");
                        }

                        @Override
                        public void onWebsocketPong(WebSocket conn, Framedata f) {
                            super.onWebsocketPong(conn, f);
                            LogUtil.zhangx("WebsocketPong");
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            LogUtil.zhangx("通道关闭");
                        }

                        @Override
                        public void onError(Exception ex) {
                            LogUtil.zhangx("链接错误");
                        }
                    };
                    mClient.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @OnClick({R.id.btnSend, R.id.btnPing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                if (null != mClient) {
                    mClient.send(etContent.getText().toString().trim());
                }
                break;
            case R.id.btnPing:
                if (mClient != null) {
                    mClient.sendPing();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mClient) {
            mClient.close();
        }
    }
}
