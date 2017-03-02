//
//  RCTAgoraModel.h
//  agora
//
//  Created by jessica on 16/12/8.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#ifndef AgoraModel_h
#define AgoraModel_h
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import "agorasdk.h"

@interface AgoraModel : RCTEventEmitter <RCTBridgeModule>

+(AgoraAPI*) agoraAPI;

@end
#endif /* RCTAgoraModel_h */
