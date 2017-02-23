package cn.pocket.agora;


import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

import io.agora.AgoraAPIOnlySignal;
import io.agora.AgoraAPI;

/**
 * Created by jessica on 16/12/15.
 */

public class AgoraModel extends ReactContextBaseJavaModule {

    private static AgoraAPIOnlySignal agoraAPI;
    final String EVENT_NAME = "Agora_Resp";

    @Override
    public String getName() {
        return "AgoraModel";
    }

    public AgoraModel(ReactApplicationContext context){
        super(context);
        agoraAPI = AgoraAPIOnlySignal.getInstance(context, AgoraViewManager.getAppId());
        agoraAPI.callbackSet(new AgoraAPI.CallBack() {

            private void sendMessage(WritableMap map) {
                RCTNativeAppEventEmitter emitter = getReactApplicationContext().getJSModule(RCTNativeAppEventEmitter.class);
                emitter.emit(EVENT_NAME, map);
            }
            @Override
            public void onLoginSuccess(int uid, int fd) {
                super.onLoginSuccess(uid, fd);
                System.out.println("onLoginSuccess========" + uid + "===" + fd);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "loginSuccess");
                map.putString("uid", "" + uid);
                map.putString("fd", "" + fd);
                sendMessage(map);
            }

            @Override
            public void onLogout(int ecode) {
                super.onLogout(ecode);
                System.out.println("onLogout========" + ecode);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "logout");
                map.putString("ecode", "" + ecode);
                sendMessage(map);
            }

            @Override
            public void onLoginFailed(int ecode) {
                super.onLoginFailed(ecode);
                System.out.println("onLoginFailed========" + ecode);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "loginFailed");
                map.putString("ecode", "" + ecode);
                sendMessage(map);
            }

            @Override
            public void onChannelJoined(String channelID) {
                super.onChannelJoined(channelID);
                System.out.println("onChannelJoined========" + channelID);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "channelJoined");
                map.putString("channel", channelID);
                sendMessage(map);
            }

            @Override
            public void onChannelJoinFailed(String channelID, int ecode) {
                super.onChannelJoined(channelID);
                System.out.println("onChannelJoined========" + channelID);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "channelJoinFailed");
                map.putString("channel", channelID);
                map.putString("ecode", "" + ecode);
                sendMessage(map);
            }

            @Override
            public void onChannelLeaved(String channelID, int ecode) {
                super.onChannelJoined(channelID);
                System.out.println("onChannelLeaved========" + channelID);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "channelLeaved");
                map.putString("channel", channelID);
                map.putString("ecode", "" + ecode);
                sendMessage(map);
            }

            @Override//收到呼叫邀请回调
            public void onInviteReceived(String channelID, String account, int uid, String extra) {
                super.onInviteReceived(channelID, account, uid, extra);
                System.out.println("onInviteReceived========" + channelID);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "inviteReceived");
                map.putString("channel", channelID);
                map.putString("account", account);
                map.putString("uid", "" + uid);
                map.putString("extra", extra);
                sendMessage(map);
            }

            @Override//远端已收到呼叫回调
            public void onInviteReceivedByPeer(String channelID, String account, int uid) {
                super.onInviteReceivedByPeer(channelID, account, uid);
                System.out.println("onInviteReceivedByPeer========" + channelID);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "inviteReceivedByPeer");
                map.putString("channel", channelID);
                map.putString("account", account);
                map.putString("uid", "" + uid);
                sendMessage(map);
            }

            @Override
            public void onInviteAcceptedByPeer(String channelID, String account, int uid) {
                super.onInviteAcceptedByPeer(channelID, account, uid);
                System.out.println("onInviteAcceptedByPeer========" + channelID);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "inviteAcceptedByPeer");
                map.putString("channel", channelID);
                map.putString("account", account);
                map.putString("uid", "" + uid);
                sendMessage(map);
            }

            @Override
            public void onInviteRefusedByPeer(String channelID, String account, int uid) {
                super.onInviteAcceptedByPeer(channelID, account, uid);
                System.out.println("onInviteAcceptedByPeer========" + channelID);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "inviteRefusedByPeer");
                map.putString("channel", channelID);
                map.putString("account", account);
                map.putString("uid", "" + uid);
                sendMessage(map);
            }

            @Override
            public void onInviteEndByPeer(String channelID, String account, int uid) {
                super.onInviteEndByPeer(channelID, account, uid);
                System.out.println("onInviteEndByPeer========" + channelID);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "inviteEndByPeer");
                map.putString("channel", channelID);
                map.putString("account", account);
                map.putString("uid", "" + uid);
                sendMessage(map);
            }

            @Override
            public void onInviteEndByMyself(String channelID, String account, int uid) {
                super.onInviteEndByMyself(channelID, account, uid);
                System.out.println("onInviteEndByMyself========" + channelID);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "inviteEndByMyself");
                map.putString("channel", channelID);
                map.putString("account", account);
                map.putString("uid", "" + uid);
                sendMessage(map);
            }

            @Override
            public void onInviteFailed(String channelID, String account, int uid, int ecode) {
                super.onInviteFailed(channelID, account, uid, ecode);
                System.out.println("onInviteFailed========" + channelID);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "inviteFailed");
                map.putString("channel", channelID);
                map.putString("account", account);
                map.putString("uid", "" + uid);
                map.putString("ecode", "" + ecode);
                sendMessage(map);
            }

            @Override
            public void onError(String name, int ecode, String desc) {
                super.onError(name, ecode, desc);
                System.out.println("onError========" + name + "===" + ecode + "===" + desc);
                WritableMap map = Arguments.createMap();
                map.putString("actionType", "error");
                map.putString("name", name);
                map.putString("ecode", "" + ecode);
                map.putString("desc", desc);
                sendMessage(map);
            }
        });
    }

    @ReactMethod
    public void login(String signalKey, String account) {
        if(agoraAPI.isOnline() == 1) {
            agoraAPI.logout();
        }
        agoraAPI.login(AgoraViewManager.getAppId(), account, signalKey, 0, null);
    }

    @ReactMethod
    public void call(String channel, String account) {
        agoraAPI.channelInviteUser(channel, account, 0);
    }

    @ReactMethod
    public void accept(String channel, String account) {
        agoraAPI.channelInviteAccept(channel, account, 0);
    }

    @ReactMethod
    public void refuse(String channel, String account) {
        agoraAPI.channelInviteRefuse(channel, account, 0);
    }

    @ReactMethod
    public void end(String channel, String account) {
        agoraAPI.channelInviteEnd(channel, account, 0);
    }

    @ReactMethod
    public void joinChannel(String channelKey, String channelName) {
        AgoraView.getRtcEngine().joinChannel(channelKey, channelName, "", 0);
    }

    @ReactMethod
    public void leaveChannel(String channel) {
        this.leaveWithChannel(channel);
    }

    @ReactMethod
    public void logout() {
        agoraAPI.logout();
    }

    private void leaveWithChannel(String channel) {
        AgoraView.getRtcEngine().setupLocalVideo(null);
        AgoraView.getRtcEngine().setupRemoteVideo(null);
        AgoraView.getRtcEngine().leaveChannel();
    }

    public static AgoraAPIOnlySignal getAgoraAPI() {
        return agoraAPI;
    }
}
