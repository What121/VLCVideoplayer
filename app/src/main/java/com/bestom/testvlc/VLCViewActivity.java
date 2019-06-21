package com.bestom.testvlc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.bestom.testvlc.util.LibVLCUtil;
import com.bestom.testvlc.view.VLCVideoView;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;


public class VLCViewActivity extends AppCompatActivity {
    private IVLCVout vlcVout;
    private MediaPlayer mediaPlayer;

    private VLCVideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlc_view);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        String path =intent.getStringExtra("VideoUrl");

        RelativeLayout rlPlayer = (RelativeLayout) findViewById(R.id.rlPlayer);

        mVideoView = (VLCVideoView) findViewById(R.id.video_view);

        if (!path.equals("")||path!=null){
            mVideoView.setVideoPath(path);
            mVideoView.start();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        try {
            super.onNewIntent(intent);
        } catch (Exception e) {
            Log.d("vlc-newintent", e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();

            finish();
        } catch (Exception e) {
            Log.d("vlc-back", e.toString());
        }
    }
}