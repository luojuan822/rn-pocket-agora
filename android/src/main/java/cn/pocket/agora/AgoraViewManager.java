package cn.pocket.agora;


import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.io.File;
import java.util.Locale;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

/**
 * Created by jessica on 16/12/11.
 */

public class AgoraViewManager extends SimpleViewManager<AgoraView> {
    private static String _appId = "b010f9fcd9f949d3a1c310a838f3d4ff";
    private static AgoraView agoraView;

    @Override
    public String getName() {
        return "RCTAgora";
    }

    @Override
    protected AgoraView createViewInstance(final ThemedReactContext context) {
        agoraView = new AgoraView(context);

        if(AgoraView.getRtcEngine() == null) {
            RtcEngine m_agoraMedia = RtcEngine.create(context, _appId, new IRtcEngineEventHandler() {
                @Override
                public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
                    System.out.println("onFirstRemoteVideoDecoded===========" + uid + "===" + width + "===" + height + "===" + elapsed);
                    final int remoteUId = uid;
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("======run===========");
                            agoraView.getLocalView().setVisibility(View.VISIBLE);
                            AgoraView.getRtcEngine().setupLocalVideo(new VideoCanvas(agoraView.getLocalView(), VideoCanvas.RENDER_MODE_FIT, 0));
                            AgoraView.getRtcEngine().setupRemoteVideo(new VideoCanvas(agoraView.getRemoteView(), VideoCanvas.RENDER_MODE_HIDDEN, remoteUId));

//                        ObjectAnimator.ofFloat(localView, "rotationX", 0.0F, 360.0F).setDuration(500)//.start();

                            ScaleAnimation scale = new ScaleAnimation(1f, 0.3f, 1f, 0.3f);
//                                Animation.RELATIVE_TO_SELF, 0.5f,
//                                Animation.RELATIVE_TO_SELF, 0.5f);
                            scale.setDuration(2000);
//                scale.setFillAfter(true);
                            agoraView.getLocalView().startAnimation(scale);
//                        invalidate();
                        }
                    });
//                postInvalidate();
                }

                @Override
                public void onError(int err) {
                    System.out.println("onError===========" + err);
                }

                @Override
                public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
                    System.out.println("onJoinChannelSuccess===========" + channel);
                }

                @Override
                public void onLeaveChannel(RtcStats stats) {
                    System.out.println("onLeaveChannel===========" + stats.toString());
                    AgoraView.getRtcEngine().stopPreview();
                    AgoraModel.getAgoraAPI().logout();
                }
            });
            m_agoraMedia.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
            m_agoraMedia.enableVideo();
            m_agoraMedia.enableAudioVolumeIndication(200, 3); // 200 ms

            m_agoraMedia.setParameters(String.format(Locale.US, "{\"rtc.log_file\":\"%s\"}", Environment.getExternalStorageDirectory()
                    + File.separator + context.getPackageName() + "/log/agora-rtc.log"));
            AgoraView.setRtcEngine(m_agoraMedia);
        }

        AgoraView.getRtcEngine().setupLocalVideo(new VideoCanvas(agoraView.getRemoteView(), VideoCanvas.RENDER_MODE_FIT, 0));
        AgoraView.getRtcEngine().startPreview();
        return agoraView;
    }

    public static void initSDK(String appId) {
        _appId = appId;
    }

    public static String getAppId() {
        return _appId;
    }
}
