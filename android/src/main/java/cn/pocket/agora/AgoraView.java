package cn.pocket.agora;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Environment;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.react.uimanager.annotations.ReactProp;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import io.agora.AgoraAPI;
import io.agora.AgoraAPIOnlySignal;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

import static android.R.attr.lockTaskMode;
import static android.R.attr.padding;
import static android.R.attr.right;

/**
 * Created by jessica on 16/12/11.
 */

public class AgoraView extends ViewGroup {

    private static RtcEngine rtcEngine;
    private SurfaceView localView;
    private SurfaceView remoteView;
    final Point size = new Point();

    private float localLeft;
    private float localTop;
    private float localWidth;
    private float localHeight;

    public AgoraView(final Context context) {
        super(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(size);

        remoteView = RtcEngine.CreateRendererView(context);
        remoteView.measure(MeasureSpec.makeMeasureSpec(size.x, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(size.y, MeasureSpec.EXACTLY));
//        remoteView.setZOrderOnTop(false);
//        remoteView.setZOrderMediaOverlay(false);
        this.addView(remoteView);

        localView = RtcEngine.CreateRendererView(context);
        localView.setZOrderOnTop(true);
        localView.getHolder().setFormat(PixelFormat.OPAQUE);
//        localView.setZOrderMediaOverlay(true);
        localView.measure(MeasureSpec.makeMeasureSpec(size.x/3, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(size.y/3, MeasureSpec.EXACTLY));
        localView.setVisibility(View.INVISIBLE);
        this.addView(localView);
    }

    public SurfaceView getLocalView() {
        return localView;
    }

    public SurfaceView getRemoteView() {
        return remoteView;
    }

    public static void setRtcEngine(RtcEngine rtcEngine) {
        AgoraView.rtcEngine = rtcEngine;
    }

    public static RtcEngine getRtcEngine() {
        return rtcEngine;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        System.out.println("==changed=" + changed + "=left " + l + "=top " + t + "=right " + r + "=bottom " + b);
        // 动态获取子View实例
        localView.layout(size.x - localView.getMeasuredWidth(), 0, size.x, localView.getMeasuredHeight());
        remoteView.layout(0, 0, remoteView.getMeasuredWidth(), remoteView.getMeasuredHeight());
    }

    @ReactProp(name = "localLeft")
    public void setLocalLeft(float localLeft) {
        this.localLeft = localLeft;
    }

    @ReactProp(name = "localTop")
    public void setLocalTop(float localTop) {
        this.localTop = localTop;
    }

    @ReactProp(name = "localWidth")
    public void setLocalWidth(float localWidth) {
        this.localWidth = localWidth;
    }

    @ReactProp(name = "localHeight")
    public void setLocalHeight(float localHeight) {
        this.localHeight = localHeight;
    }

    public String calcToken(String appID, String certificate, String account, long expiredTime){
        // Token = 1:appID:expiredTime:sign
        // Token = 1:appID:expiredTime:md5(account + vendorID + certificate + expiredTime)

        String sign = md5hex((account + appID + certificate + expiredTime).getBytes());
        return "1:" + appID + ":" + expiredTime + ":" + sign;

    }

    public static String hexlify(byte[] data){
        char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        /**
         * 用于建立十六进制字符的输出的大写字符数组
         */
        char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        char[] toDigits = DIGITS_LOWER;
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return String.valueOf(out);

    }

    public static String md5hex(byte[] s){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(s);
            return hexlify(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
