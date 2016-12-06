// Fill out your copyright notice in the Description page of Project Settings.

#pragma once

#include "Kismet/BlueprintFunctionLibrary.h"
#include "IapAdaptor.generated.h"

/**
 * Call IAP API
 */
UCLASS()
class IAPSAMPLEAPP_API UIapAdaptor : public UBlueprintFunctionLibrary
{
    GENERATED_BODY()
    
    public:
    static void init();
    
    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestPayment();
    
    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestCmdReceiptVerification();
    
    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestCmdPurchaseHistory();
    
    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestCmdProductInfo();
    
    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestCmdCheckPurchasability();
    
    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestCmdChangeProduct_withDrawSubscription();
    
    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestCmdChangeProduct_descentPoints();

    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestCmdPurchaseHistory_bg();
    
    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestCmdProductInfo_bg();
    
    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestCmdCheckPurchasability_bg();
    
    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestCmdChangeProduct_withDrawSubscription_bg();
    
    UFUNCTION(BlueprintCallable, Category=IapAdaptor)
    static void requestCmdChangeProduct_descentPoints_bg();
};

static bool debugMode = false;
static FString appid = "OA00679020";
static FString pid = "0910024112";
static FString pname = "TEST";
static FString tid = "123456";
static FString bpinfo = "Onestore bp Information";

