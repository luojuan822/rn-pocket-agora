//
//  RCTAgoraManager.h
//  agora
//
//  Created by jessica on 16/12/8.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#ifndef RCTAgoraManager_h
#define RCTAgoraManager_h
#import <React/RCTViewManager.h>
#import <AgoraRtcEngineKit/AgoraRtcEngineKit.h>

@interface RCTAgoraManager : RCTViewManager<AgoraRtcEngineDelegate>

+(void)initSDK:(NSString*)appId;

+(NSString*) appId;

@end

#endif /* RCTAgoraManager_h */
