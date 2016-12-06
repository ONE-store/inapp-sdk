// Fill out your copyright notice in the Description page of Project Settings.

#include "IapSampleApp.h"
#include "IapAdaptor.h"

#if PLATFORM_ANDROID
#endif

DECLARE_DELEGATE_OneParam(FAndroidOnestoreIapSetupServiceDelegate, bool);
CORE_API FAndroidOnestoreIapSetupServiceDelegate OnAndroidOnestoreIapSetupService;

DECLARE_DELEGATE_FiveParams(FAndroidOnestoreIapRequestPurchaseDelegate, const FString&, const FString&, const FString&, const FString&, const FString&);
CORE_API FAndroidOnestoreIapRequestPurchaseDelegate OnAndroidOnestoreIapRequestPurchase;

DECLARE_DELEGATE_ThreeParams(FAndroidOnestoreIapReceiptVerifyDelegate, const FString&, const FString&, const FString&);
CORE_API FAndroidOnestoreIapReceiptVerifyDelegate OnAndroidOnestoreIapReceiptVerify;

DECLARE_DELEGATE_TwoParams(FAndroidOnestoreIapCmdPurchaseHistoryDelegate, const FString&, const FString&);
CORE_API FAndroidOnestoreIapCmdPurchaseHistoryDelegate OnAndroidOnestoreIapCmdPurchaseHistory;

DECLARE_DELEGATE_OneParam(FAndroidOnestoreIapCmdProductInfoDelegate, const FString&);
CORE_API FAndroidOnestoreIapCmdProductInfoDelegate OnAndroidOnestoreIapCmdProductInfo;

DECLARE_DELEGATE_TwoParams(FAndroidOnestoreIapCmdCheckPurchasabilityDelegate, const FString&, const FString&);
CORE_API FAndroidOnestoreIapCmdCheckPurchasabilityDelegate OnAndroidOnestoreIapCmdCheckPurchasability;

DECLARE_DELEGATE_TwoParams(FAndroidOnestoreIapCmdChangeProduct_withDrawSubscriptionDelegate, const FString&, const FString&);
CORE_API FAndroidOnestoreIapCmdChangeProduct_withDrawSubscriptionDelegate OnAndroidOnestoreIapCmdChangeProduct_withDrawSubscription;

DECLARE_DELEGATE_TwoParams(FAndroidOnestoreIapCmdChangeProduct_descentPointsDelegate, const FString&, const FString&);
CORE_API FAndroidOnestoreIapCmdChangeProduct_descentPointsDelegate OnAndroidOnestoreIapCmdChangeProduct_descentPoints;

DECLARE_DELEGATE_TwoParams(FAndroidOnestoreIapCmdPurchaseHistoryDelegate_bg, const FString&, const FString&);
CORE_API FAndroidOnestoreIapCmdPurchaseHistoryDelegate_bg OnAndroidOnestoreIapCmdPurchaseHistory_bg;

DECLARE_DELEGATE_OneParam(FAndroidOnestoreIapCmdProductInfoDelegate_bg, const FString&);
CORE_API FAndroidOnestoreIapCmdProductInfoDelegate_bg OnAndroidOnestoreIapCmdProductInfo_bg;

DECLARE_DELEGATE_TwoParams(FAndroidOnestoreIapCmdCheckPurchasabilityDelegate_bg, const FString&, const FString&);
CORE_API FAndroidOnestoreIapCmdCheckPurchasabilityDelegate_bg OnAndroidOnestoreIapCmdCheckPurchasability_bg;

DECLARE_DELEGATE_TwoParams(FAndroidOnestoreIapCmdChangeProduct_withDrawSubscriptionDelegate_bg, const FString&, const FString&);
CORE_API FAndroidOnestoreIapCmdChangeProduct_withDrawSubscriptionDelegate_bg OnAndroidOnestoreIapCmdChangeProduct_withDrawSubscription_bg;

DECLARE_DELEGATE_TwoParams(FAndroidOnestoreIapCmdChangeProduct_descentPointsDelegate_bg, const FString&, const FString&);
CORE_API FAndroidOnestoreIapCmdChangeProduct_descentPointsDelegate_bg OnAndroidOnestoreIapCmdChangeProduct_descentPoints_bg;



void UIapAdaptor::init(){
    if(!OnAndroidOnestoreIapSetupService.ExecuteIfBound(debugMode)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP Init() : Fail"));
    }
}

void UIapAdaptor::requestPayment(){
    init();
    
    if(!OnAndroidOnestoreIapRequestPurchase.ExecuteIfBound(appid, pid, pname, tid, bpinfo)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP OnAndroidOnestoreIapRequestPurchase() : Fail"));
    }
}

void UIapAdaptor::requestCmdPurchaseHistory(){
    init();
    
    if(!OnAndroidOnestoreIapCmdPurchaseHistory.ExecuteIfBound(appid, pid)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP OnAndroidOnestoreIapCmdPurchaseHistory() : Fail"));
    }
}

void UIapAdaptor::requestCmdProductInfo(){
    init();
    
    if(!OnAndroidOnestoreIapCmdProductInfo.ExecuteIfBound(appid)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP OnAndroidOnestoreIapCmdProductInfo() : Fail"));
    }
}

void UIapAdaptor::requestCmdCheckPurchasability(){
    init();
    
    if(!OnAndroidOnestoreIapCmdCheckPurchasability.ExecuteIfBound(appid, pid)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP OnAndroidOnestoreIapCmdCheckPurchasability() : Fail"));
    }
}

void UIapAdaptor::requestCmdChangeProduct_withDrawSubscription(){
    init();
    
    if(!OnAndroidOnestoreIapCmdChangeProduct_withDrawSubscription.ExecuteIfBound(appid, pid)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP OnAndroidOnestoreIapCmdChangeProduct_withDrawSubscription() : Fail"));
    }
}

void UIapAdaptor::requestCmdChangeProduct_descentPoints(){
    init();
    
    if(!OnAndroidOnestoreIapCmdChangeProduct_descentPoints.ExecuteIfBound(appid, pid)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP OnAndroidOnestoreIapCmdChangeProduct_descentPoints() : Fail"));
    }
}


void UIapAdaptor::requestCmdPurchaseHistory_bg(){
    init();
    
    if(!OnAndroidOnestoreIapCmdPurchaseHistory_bg.ExecuteIfBound(appid, pid)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP OnAndroidOnestoreIapCmdPurchaseHistory_bg() : Fail"));
    }
}

void UIapAdaptor::requestCmdProductInfo_bg(){
    init();
    
    if(!OnAndroidOnestoreIapCmdProductInfo_bg.ExecuteIfBound(appid)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP OnAndroidOnestoreIapCmdProductInfo_bg() : Fail"));
    }
}

void UIapAdaptor::requestCmdCheckPurchasability_bg(){
    init();
    
    if(!OnAndroidOnestoreIapCmdCheckPurchasability_bg.ExecuteIfBound(appid, pid)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP OnAndroidOnestoreIapCmdCheckPurchasability_bg() : Fail"));
    }
}

void UIapAdaptor::requestCmdChangeProduct_withDrawSubscription_bg(){
    init();
    
    if(!OnAndroidOnestoreIapCmdChangeProduct_withDrawSubscription_bg.ExecuteIfBound(appid, pid)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP OnAndroidOnestoreIapCmdChangeProduct_withDrawSubscription_bg() : Fail"));
    }
}

void UIapAdaptor::requestCmdChangeProduct_descentPoints_bg(){
    init();
    
    if(!OnAndroidOnestoreIapCmdChangeProduct_descentPoints_bg.ExecuteIfBound(appid, pid)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP OnAndroidOnestoreIapCmdChangeProduct_descentPoints_bg() : Fail"));
    }
}

void UIapAdaptor::requestCmdReceiptVerification(){
    init();
    
    FString txid = "TSTORE0004_20160930172121928803137279998";
    FString receiptSignData =  "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";
    
    if(!OnAndroidOnestoreIapReceiptVerify.ExecuteIfBound(appid, txid, receiptSignData)){
        UE_LOG(LogTemp, Warning, TEXT("++IAP requestCmdReceiptVerification() : Fail"));
    }
}
