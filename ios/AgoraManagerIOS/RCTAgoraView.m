//
//  RCTAgoraView.m
//  agora
//
//  Created by jessica on 16/12/8.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RCTAgoraView.h"

static AgoraRtcEngineKit* _agoraKit;

@implementation RCTAgoraView {
  UIView* _localView;
  UIView* _remoteView;
  
  CGFloat _localLeft;
  CGFloat _localTop;
  CGFloat _localWidth;
  CGFloat _localHeight;
}

-(void) setLocalLeft: (int) localLeft {
  _localLeft = localLeft;
  NSLog(@"setLocalLeft===, %d, %f", localLeft, _localLeft);
}
-(void) setLocalTop: (int) localTop {
  _localTop = localTop;
  NSLog(@"setLocalTop===, %d, %f", localTop, _localTop);
}
-(void) setLocalWidth: (int) localWidth {
  _localWidth = localWidth;
  NSLog(@"setLocalWidth===, %d, %f", localWidth, _localWidth);
}
-(void) setLocalHeight: (int) localHeight {
  _localHeight = localHeight;
  NSLog(@"setlocalHeight===, %d, %f", localHeight, _localHeight);
}

- (instancetype)initWithFrame:(CGRect)frame
{
  self = [super initWithFrame:[UIScreen mainScreen].bounds];
  if (self) {
    _localView = [[UIView alloc]  initWithFrame:[UIScreen mainScreen].bounds];
    
    [self addSubview:_localView];
  }
  return self;
}

-(void) changeView {
  
  NSLog(@"changeView===, %f, %f, %f, %f", _localLeft, _localTop, _localWidth, _localHeight);
  
  _remoteView = [[UIView alloc] initWithFrame: [UIScreen mainScreen].bounds];
  [self addSubview:_remoteView];
  [self bringSubviewToFront:_localView];
  
  [UIView animateWithDuration:0.3 animations:^{
    _localView.frame = CGRectMake(_localLeft, _localTop, _localWidth, _localHeight);
    [self setNeedsLayout];
  }];
  
  [self layoutIfNeeded];
}

-(UIView*) localView {
  return _localView;
}

-(UIView*) remoteView {
  return _remoteView;
}

+(AgoraRtcEngineKit*) agoraKit {
  return _agoraKit;
}

+(void) setAgoraKit: (AgoraRtcEngineKit*) agoraKit {
  _agoraKit = agoraKit;
}

- (void)dealloc
{
  _localView = nil;
  _remoteView = nil;
}
@end
