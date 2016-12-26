import {
    requireNativeComponent,
    NativeModules,
    Platform,
    DeviceEventEmitter,
    NativeAppEventEmitter
} from 'react-native';
var invariant = require('invariant');

const _module = NativeModules.AgoraModel;

export default {
    _module,
    login(signalKey, account) {
        _module.login(signalKey, account);
    },
    call(channel, account) {
        _module.call(channel, account);
    },
    accept(channel, account) {
        _module.accept(channel, account);
    },
    refuse(channel, account) {
        _module.ref(channel, account)
    },
    end(channel, account) {
        _module.end(channel, account)
    },
    joinChannel(key, channelName) {
        _module.joinChannel(key, channelName);
    },
    leaveChannel(channel) {
        _module.leaveChannel(channel);
    },
    logout() {
        _module.logout();
    }
}