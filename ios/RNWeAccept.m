
#import "RNWeAccept.h"
@import AcceptSDK;


@interface RNWeAccept () {
    payload _params;
    UIViewController *_rootViewController;
    RCTResponseSenderBlock _successCallback;
    RCTResponseErrorBlock _errorCallback;
}
@end

@implementation RNWeAccept

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}


RCT_EXPORT_METHOD(payWithNoToken:(payload)json successCallback:(RCTResponseSenderBlock)successCallback errorCallback:(RCTResponseErrorBlock)errorCallback) {
    RNWeAccept *sdk = [[RNWeAccept alloc] initWithParams:json successCallback:successCallback errorCallback:errorCallback];
    [sdk startPayWithoutToken];
}

- (payload)initWithParams:(payload)params successCallback:(RCTResponseSenderBlock)successCallback errorCallback:(RCTResponseErrorBlock)errorCallback {
    self = [super init];

    if (self) {
        _accept  = AcceptSDK();
        _successCallback = successCallback;
        _errorCallback = errorCallback;
        _params = params;
    }

    return self;
}

/**
 Runs We Accept pay with token flow
 */
- (void) startPayWithoutToken {

    dispatch_async(dispatch_get_main_queue(), ^{

        // Get view controller on which to present the flow
        UIWindow *window = [[UIApplication sharedApplication] keyWindow];
        self->_rootViewController = (UIViewController *)window.rootViewController;
        
        NSDictionary *dictionary = [RCTConvert NSDictionary:self->_params];
        // @try {
        //     NSArray *documentTypes = dictionary[@"documentTypes"];
        //     if (documentTypes && documentTypes.count > 1) {
        //         CustomFlowViewController *customFlowVC = [[CustomFlowViewController alloc] initWithParams:self->_params];
        //         UINavigationController *navController = [[UINavigationController alloc] initWithRootViewController: customFlowVC];
        //         [navController.navigationBar setBarTintColor:UIColor.whiteColor];
        //         [navController.navigationBar setValue:@(YES) forKeyPath:@"hidesShadow"];
        //         [self->_rootViewController presentViewController:navController animated:true completion:nil];
        //     } else {
        //         [ONFlowConfigBuilder create:self->_params successCallback:^(ONFlowConfig *config) {
        //             [self runSDKFlowWithConfig:config];
        //         } errorCallback:^(NSError *error) {
        //             UIAlertController *popup = [self createErrorPopupWithMessage:[error localizedDescription]];
        //             [self->_rootViewController presentViewController:popup animated:YES completion:NULL];
        //         }];
        //     }
        // } @catch (NSException *e) {
            self->_errorCallback([NSError errorWithDomain:@"invalid_params" code:100 userInfo:@{                                                                                               NSLocalizedDescriptionKey: @"Invalid document types"
                                                                                               }]);
        }
    });
}

@end
  