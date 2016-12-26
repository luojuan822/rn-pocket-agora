//
//  RCTAgoraModel.h
//  agora
//
//  Created by jessica on 16/12/8.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#ifndef AgoraModel_h
#define AgoraModel_h
#import "RCTBridgeModule.h"
#import "RCTEventEmitter.h"
#import "agorasdk.h"

@interface AgoraModel : RCTEventEmitter <RCTBridgeModule>

+(AgoraAPI*) agoraAPI;

@end
#endif /* RCTAgoraModel_h */
