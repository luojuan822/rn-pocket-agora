//
//  RCTAgoraView.h
//  agora
//
//  Created by jessica on 16/12/8.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#ifndef RCTAgoraView_h
#define RCTAgoraView_h
#import <UIKit/UIKit.h>
#import <AgoraRtcEngineKit/AgoraRtcEngineKit.h>

@interface RCTAgoraView : UIView

-(void) setLocalLeft: (int) localLeft;
-(void) setLocalTop: (int) localTop;
-(void) setLocalWidth: (int) localWidth;
-(void) setLocalHeight: (int) localHeight;

-(UIView*) localView;
-(UIView*) remoteView;
-(void) changeView;
+(AgoraRtcEngineKit*) agoraKit;
+(void) setAgoraKit: (AgoraRtcEngineKit*) agoraKit;

@end

#endif /* RCTAgoraView_h */
