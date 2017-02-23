//
//  RCTAgoraModel.m
//  agora
//
//  Created by jessica on 16/12/8.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "AgoraModel.h"
#import "RCTConvert.h"
#import "RCTAgoraView.h"
#import "agorasdk.h"
#import "RCTAgoraManager.h"

static AgoraAPI* agoraApi;

@implementation AgoraModel
//  NSString* _signalKey;
//  NSString* _channelKey;
//  NSString* _channel;
//  NSString* _account;

@synthesize bridge = _bridge;
RCT_EXPORT_MODULE()

-(NSArray<NSString *> *) supportedEvents {
  return @[@"Agora_Resp"];
}

+(AgoraAPI*) agoraAPI {
  return agoraApi;
}

- (instancetype)init
{
  self = [super init];
  NSLog(@"AgoraModel===, init");
  if(agoraApi == nil) {
    agoraApi = [AgoraAPI getInstanceWithoutMedia:[RCTAgoraManager appId]];
    agoraApi.onLoginSuccess         = ^(uint32_t uid, int fd) {
      NSLog(@"onLoginSuccess===登陆成功, %u, %d", uid, fd);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"loginSuccess";
      body[@"uid"] = [NSString stringWithFormat:@"%u", uid];
      body[@"fd"] = [NSString stringWithFormat:@"%d", fd];
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
    agoraApi.onLogout               = ^(AgoraEcode ecode) {
      NSLog(@"onLogout===登出, %lu", ecode);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"logout";
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
    agoraApi.onLoginFailed          = ^(AgoraEcode ecode) {
      NSLog(@"onLoginFailed===登录失败, %lu", ecode);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"loginFailed";
      body[@"ecode"] = [NSString stringWithFormat:@"%lu", ecode];
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
    agoraApi.onChannelJoined        = ^(NSString* channel) {
      NSLog(@"onChannelJoined===加入频道, %@", channel);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"channelJoined";
      body[@"channel"] = channel;
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
    agoraApi.onChannelJoinFailed    = ^(NSString* channel,AgoraEcode ecode) {
      NSLog(@"onChannelJoined===加入频道失败, %@, %lu", channel, ecode);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"channelJoinFailed";
      body[@"channel"] = channel;
      body[@"ecode"] = [NSString stringWithFormat:@"%lu", ecode];
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
    agoraApi.onChannelLeaved        = ^(NSString* name, AgoraEcode ecode) {
      NSLog(@"onChannelLeaved===离开频道, %@ %lu", name, ecode);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"channelLeaved";
      body[@"name"] = name;
      body[@"ecode"] = [NSString stringWithFormat:@"%lu", ecode];
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
    agoraApi.onInviteReceived       = ^(NSString* channel, NSString *name, uint32_t uid) {
      NSLog(@"onInviteReceived===收到呼叫,%@ %@ %u",channel, name, uid);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"inviteReceived";
      body[@"channel"] = channel;
      body[@"name"] = name;
      body[@"uid"] = [NSString stringWithFormat:@"%u", uid];
      [self sendEventWithName:@"Agora_Resp" body:body];
      
    };
    agoraApi.onInviteReceivedByPeer = ^(NSString* channel, NSString *name, uint32_t uid) {
      NSLog(@"onInviteReceivedByPeer===对方收到呼叫,%@ %@ %u",channel, name, uid);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"inviteReceivedByPeer";
      body[@"channel"] = channel;
      body[@"name"] = name;
      body[@"uid"] = [NSString stringWithFormat:@"%u", uid];
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
    agoraApi.onInviteAcceptedByPeer = ^(NSString* channel, NSString *name, uint32_t uid) {
      NSLog(@"onInviteAcceptedByPeer===对方接受呼叫,%@ %@ %u",channel, name, uid);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"inviteAcceptedByPeer";
      body[@"channel"] = channel;
      body[@"name"] = name;
      body[@"uid"] = [NSString stringWithFormat:@"%u", uid];
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
    agoraApi.onInviteRefusedByPeer  = ^(NSString* channel, NSString *name, uint32_t uid) {
      NSLog(@"onInviteRefusedByPeer===对方拒绝呼叫,%@ %@ %u",channel, name, uid);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"inviteRefusedByPeer";
      body[@"channel"] = channel;
      body[@"name"] = name;
      body[@"uid"] = [NSString stringWithFormat:@"%u", uid];
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
    agoraApi.onInviteEndByPeer      = ^(NSString* channel, NSString *name, uint32_t uid) {
      NSLog(@"onInviteEndByPeer===对方接通后结束呼叫,%@ %@ %u",channel, name, uid);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"inviteEndByPeer";
      body[@"channel"] = channel;
      body[@"name"] = name;
      body[@"uid"] = [NSString stringWithFormat:@"%u", uid];
      [self sendEventWithName:@"Agora_Resp" body:body];

      //      [self leaveChannel];
    };
    agoraApi.onInviteEndByMyself    = ^(NSString* channel, NSString *name, uint32_t uid) {
      //      [self log:[NSString stringWithFormat:@"我方接通后关闭呼叫 %@ %@ %u", channel, name, uid]];
      NSLog(@"onInviteEndByMyself===我方接通后关闭呼叫,%@ %@ %u", channel, name, uid);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"inviteEndByMyself";
      body[@"channel"] = channel;
      body[@"name"] = name;
      body[@"uid"] = [NSString stringWithFormat:@"%u", uid];
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
    agoraApi.onInviteFailed         = ^(NSString* channel, NSString* account, uint32_t uid, AgoraEcode ecode) {
      NSLog(@"onInviteFailed===呼叫失败,%@ %@ %u %lu", channel, account, uid, ecode);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"inviteFailed";
      body[@"channel"] = channel;
      body[@"account"] = account;
      body[@"uid"] = [NSString stringWithFormat:@"%u", uid];
      body[@"ecode"] = [NSString stringWithFormat:@"%lu", ecode];
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
    agoraApi.onChannelUserLeaved    = ^(NSString* name, uint32_t uid){
//      [self log:[NSString stringWithFormat:@"用户离开频道 %@ %u", name, uid]];
    };
    agoraApi.onLog                  = ^(NSString *txt) {
//      [self log:[NSString stringWithFormat:@"onLog %@", txt]];
    };
    agoraApi.onError                = ^(NSString* name,AgoraEcode ecode,NSString* desc) {
      NSLog(@"onError===出现错误,%@ %lu %@", name, ecode, desc);
      NSMutableDictionary *body = @{}.mutableCopy;
      body[@"actionType"] = @"error";
      body[@"name"] = name;
      body[@"ecode"] = [NSString stringWithFormat:@"%lu", ecode];
      body[@"desc"] = desc;
      [self sendEventWithName:@"Agora_Resp" body:body];
    };
;
  }
  return self;
}

-(void) leaveWithChannel: (NSString*) channel {
    NSLog(@"leaveChannel===, %@", channel);
  
  [[RCTAgoraView agoraKit] setupLocalVideo:nil];
  [[RCTAgoraView agoraKit] setupRemoteVideo:nil];
  [[RCTAgoraView agoraKit] leaveChannel:^(AgoraRtcStats *stat) {
    NSLog(@"leaveChannel===completed, %@", stat);
    [agoraApi logout];
    [[RCTAgoraView agoraKit] stopPreview];
  }];
}

RCT_EXPORT_METHOD(login: (NSString*) signalKey account:(NSString*) account) {
  NSLog(@"login===, %@, %@", signalKey, account);
  if([agoraApi isOnline]) {
    [agoraApi logout];
  }
  [agoraApi login:[RCTAgoraManager appId] account:account token:signalKey uid:0 deviceID:nil];
}

RCT_EXPORT_METHOD(call:(NSString*) channel account:(NSString*) account) {
  NSLog(@"call=== %@, %@", channel, account);
  [agoraApi channelInviteUser:channel account:account uid:0];
}

RCT_EXPORT_METHOD(accept:(NSString*) channel account:(NSString*) account) {
  NSLog(@"accept===, %@, %@", channel, account);
  [agoraApi channelInviteAccept:channel account:account uid:0];
}

RCT_EXPORT_METHOD(refuse:(NSString *) channel account:(NSString*) account) {
  NSLog(@"refuse===, %@, %@", channel, account);
  [agoraApi channelInviteRefuse:channel account:account uid:0];
}

RCT_EXPORT_METHOD(end:(NSString *) channel account:(NSString*) account) {
  NSLog(@"end===, %@, %@", channel, account);
  [agoraApi channelInviteEnd:channel account:account uid:0];
//  [self leaveWithChannel:channel];
}

RCT_EXPORT_METHOD(joinChannel:(NSString*)key channelName:(NSString*) channelName) {
  NSLog(@"joinChannel===, %@,%@", key, channelName);
  [[RCTAgoraView agoraKit] joinChannelByKey:key channelName:channelName info:nil uid:0 joinSuccess:^(NSString *channel, NSUInteger uid, NSInteger elapsed) {
    NSLog(@"joinChannel=== %@ sucess", channelName);
  }];
}

RCT_EXPORT_METHOD(leaveChannel:(NSString*) channel) {
  [self leaveWithChannel:channel];
}

RCT_EXPORT_METHOD(logout) {
  NSLog(@"logout===");
  [agoraApi logout];
}

//RCT_EXPORT_METHOD(isOnline: (RCTResponseSenderBlock)callback) {
//  callback(@[@([agoraApi isOnline])]);
//}

@end
