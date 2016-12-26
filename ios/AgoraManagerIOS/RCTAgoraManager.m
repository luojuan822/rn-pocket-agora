//
//  RCTAgoraManager.m
//  agora
//
//  Created by wangshi on 2016/12/1.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "RCTAgoraView.h"
#import "RCTAgoraManager.h"
#import "AgoraModel.h"

static RCTAgoraView* agoraView;
static NSString* _appId;

@implementation RCTAgoraManager

@synthesize bridge = _bridge;
RCT_EXPORT_MODULE(RCTAgoraManager)

RCT_EXPORT_VIEW_PROPERTY(localLeft, int)
RCT_EXPORT_VIEW_PROPERTY(localTop, int)
RCT_EXPORT_VIEW_PROPERTY(localWidth, int)
RCT_EXPORT_VIEW_PROPERTY(localHeight, int)

+(void)initSDK:(NSString*)appId {
  _appId = appId;
}
+(NSString*) appId {
  return _appId;
}

- (UIView *)view
{
    NSLog(@"view===");
    agoraView = [[RCTAgoraView alloc] initWithFrame:[UIScreen mainScreen].bounds];

  
    if([RCTAgoraView agoraKit] == nil) {
      AgoraRtcEngineKit* _agoraKit = [AgoraRtcEngineKit sharedEngineWithAppId:_appId delegate:self];
      [RCTAgoraView setAgoraKit: _agoraKit];
    }
  
    [[RCTAgoraView agoraKit] enableVideo];
  
    AgoraRtcVideoCanvas* canvas = [AgoraRtcVideoCanvas alloc];
    canvas.uid = 0;
    canvas.view = agoraView.localView;
    canvas.renderMode = AgoraRtc_Render_Fit;
    [[RCTAgoraView agoraKit] setupLocalVideo:canvas];
    [[RCTAgoraView agoraKit] startPreview];
    NSLog(@"%@", agoraView);
    return agoraView;
}

- (void)rtcEngine:(AgoraRtcEngineKit *)engine firstRemoteVideoDecodedOfUid:(NSUInteger)uid size:(CGSize)size elapsed:(NSInteger)elapsed {
  
    NSLog(@"firstRemoteVideoDecodedOfUid===");
  
  [agoraView changeView];
  AgoraRtcVideoCanvas* canvas = [AgoraRtcVideoCanvas alloc];
  canvas.uid = uid;
  canvas.view = agoraView.remoteView;
  canvas.renderMode = AgoraRtc_Render_Fit;
  
  [[RCTAgoraView agoraKit] setupRemoteVideo:canvas];
}

- (void)rtcEngine:(AgoraRtcEngineKit *)engine didOccurWarning:(AgoraRtcWarningCode)warningCode {
  NSLog(@"didOccurWarning===, %lu", warningCode);
}

- (void)rtcEngine:(AgoraRtcEngineKit *)engine didOccurError:(AgoraRtcErrorCode)errorCode {
  NSLog(@"didOccurError===, %lu", errorCode);
}

- (void)rtcEngine:(AgoraRtcEngineKit *)engine didLeaveChannelWithStats:(AgoraRtcStats*)stats {
  NSLog(@"didLeaveChannelWithStats===, %@", stats);
}

@end
