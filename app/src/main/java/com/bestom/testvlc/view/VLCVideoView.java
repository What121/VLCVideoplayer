package com.bestom.testvlc.view;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bestom.testvlc.util.LibVLCUtil;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

public class VLCVideoView extends SurfaceView  {
    private Context mContext;
    public SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private LibVLC libvlc;
    private IVLCVout vlcVout;

    int videoWidth,videoHight;

    public VLCVideoView(Context context) {
        super(context);
        mContext=context;
        init();
    }

    public VLCVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VLCVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.setKeepScreenOn(true);
        //surfaceHolder.addCallback(this);
        libvlc = LibVLCUtil.getLibVLC(null);
        mediaPlayer = new MediaPlayer(libvlc);
        vlcVout = mediaPlayer.getVLCVout();
        vlcVout.addCallback(callback);
        vlcVout.setVideoView(this);
        vlcVout.attachViews();
    }

    public void setVideoPath(String path){
        Media media=new Media(libvlc, Uri.parse(path));
        mediaPlayer.setMedia(media);
        mediaPlayer.setEventListener(eventListener);
    }

    public void start(){
        mediaPlayer.play();
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        vlcVout.detachViews();
        vlcVout.removeCallback(callback);
        mediaPlayer.setEventListener(null);
    }

    public void resume() {
        vlcVout.setVideoView(this);
        vlcVout.attachViews();
        vlcVout.addCallback(callback);
        mediaPlayer.setEventListener(eventListener);
    }

    public void release(){
        pause();
        mediaPlayer.release();
    }

    public void setEventListener(MediaPlayer.EventListener eventListener){
        mediaPlayer.setEventListener(eventListener);
    }

    private IVLCVout.Callback callback=new IVLCVout.Callback() {
        @Override
        public void onNewLayout(IVLCVout ivlcVout, int i, int i1, int i2, int i3, int i4, int i5) {
//            try {
////                    totalTime = mediaPlayer.getLength();
////                    seekBarTime.setMax((int) totalTime);
////                    tvTotalTime.setText(SystemUtil.getMediaTime((int) totalTime));
//
//                videoWidth = i;
//                videoHight = i1;
//
//                WindowManager windowManager = (WindowManager) mContext. getSystemService(Context.WINDOW_SERVICE);
//                Display display = windowManager.getDefaultDisplay();
//                Point point = new Point();
//                display.getSize(point);
//
//                ViewGroup.LayoutParams layoutParams = getLayoutParams();
//                layoutParams.width = point.x;
//                layoutParams.height = (int) Math.ceil((float) videoHight * (float) point.x / (float) videoWidth);
//                setLayoutParams(layoutParams);
//            } catch (Exception e) {
//                Log.d("vlc-newlayout", e.toString());
//            }
        }

        @Override
        public void onSurfacesCreated(IVLCVout ivlcVout) {

        }

        @Override
        public void onSurfacesDestroyed(IVLCVout ivlcVout) {

        }

        @Override
        public void onHardwareAccelerationError(IVLCVout ivlcVout) {

        }
    };

    private MediaPlayer.EventListener eventListener=new MediaPlayer.EventListener() {
        @Override
        public void onEvent(MediaPlayer.Event event) {
            try {
                //播放结束
                if (mediaPlayer.getPlayerState() == Media.State.Ended) {
                    mediaPlayer.setTime(0);
                    mediaPlayer.stop();
                }
            }catch (Exception e){
                Log.e("VLCPlayer-event", e.toString());
            }

        }
    };

}
