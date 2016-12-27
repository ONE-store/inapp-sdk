using UnityEngine;
using System.Collections;
using IapResponse;
using IapVerifyReceipt;
using IapError;

public class IapSample : MonoBehaviour {

	#if UNITY_ANDROID

	//------------------------------------------------
	//
	// Variables
	//
	//------------------------------------------------

	private AndroidJavaClass unityPlayerClass = null;
	private AndroidJavaObject currentActivity = null;
	private AndroidJavaObject iapRequestAdapter = null;

	/// <summary>
	/// One Store에 등록된 앱 아이디.
	/// 반드시 자신의 앱 아이디로 사용권장.
	/// </summary>
	private const string appId = "OA00679020";

	void Start () 
	{
		//-----------------
		// 예외처리
		//-----------------
		//클래스 명과 게임 오브젝트 명이 다르면 Android에서 보내는 콜백을 받을 수 없습니다.
		string className = this.GetType().Name;
		string gameObjectName = gameObject.name;
		if(className != gameObjectName) 
			Debug.LogError("클래스 명과 게임 오브젝트 명이 다릅니다. 반드시 동일하게 입력하세요.");

		//-----------------
		// Initialize
		//-----------------
		unityPlayerClass = new AndroidJavaClass ("com.unity3d.player.UnityPlayer");
		currentActivity = unityPlayerClass.GetStatic<AndroidJavaObject> ("currentActivity");

		if (currentActivity != null) {
			// RequestAdapter를 초기화
			// ---------------------------------
			// 함수 parameter 정리
			// ---------------------------------
			// (1) 콜백을 받을 클래스 이름
			// (2) Activity Context
			// (3) debug 여부
			iapRequestAdapter = new AndroidJavaObject("com.onestore.iap.unity.RequestAdapter", "IapSample", currentActivity, false); //Release
			//iapRequestAdapter = new AndroidJavaObject("com.onestore.iap.unity.RequestAdapter", "IapSample", currentActivity, true); //Debug
		}
	}

	// Update is called once per frame
	void Update () 
	{
		if (Input.GetKeyDown(KeyCode.Escape)) {
			Application.Quit();	
		}
	}

	void Destroy () 
	{
		if (unityPlayerClass != null)
			unityPlayerClass.Dispose ();
		if (currentActivity != null)
			currentActivity.Dispose ();
		if (iapRequestAdapter != null)
			iapRequestAdapter.Dispose ();
	}

	//------------------------------------------------
	//
	// Exit
	//
	//------------------------------------------------
	public void Exit () 
	{
		if (iapRequestAdapter != null) 
		{
			Debug.Log ("Exit called!!!");
			iapRequestAdapter.Call ("exit");
		}
	}

	//------------------------------------------------
	//
	// Command - Request
	//
	//------------------------------------------------

	public void RequestPurchaseHistory() 
	{
		// ---------------------------------
		// 함수 parameter 정리
		// ---------------------------------
		// (0) 메소드이름 : 구매내역 조회
		// ---------------------------------
		// (1) 필요시에는 UI 노출
		// (2) appId
		// (3) productIds
		// ----------------------------------
		string[] productIds = {"0910024112"};
		iapRequestAdapter.Call ("requestPurchaseHistory", false, appId, productIds);
		//iapRequestAdapter.Call ("requestPurchaseHistory", true, appId, productIds); // UI노출 없이 Background로만 진행
	}

	public void RequestProductInfo() 
	{
		// ---------------------------------
		// 함수 parameter 정리
		// ---------------------------------
		// (0) 메소드이름 : 상품정보 조회
		// ---------------------------------
		// (1) 필요시에는 UI 노출
		// (2) appId
		// ----------------------------------
		iapRequestAdapter.Call ("requestProductInfo", false, appId);
		//iapRequestAdapter.Call ("requestProductInfo", true, appId); // UI노출 없이 Background로만 진행
	}

	public void RequestCheckPurchasability() 
	{
		// ---------------------------------
		// 함수 parameter 정리
		// ---------------------------------
		// (0) 메소드이름 : 구매가능여부 조회
		// ---------------------------------
		// (1) 필요시에는 UI 노출
		// (2) appId
		// (3) productIds
		// ----------------------------------
		string[] productIds = {"0910024112"};
		iapRequestAdapter.Call ("requestCheckPurchasability", false, appId, productIds);
		//iapRequestAdapter.Call ("requestCheckPurchasability", true, appId, productIds); // UI노출 없이 Background로만 진행
	}

	public void RequestSubtractPoints() 
	{
		// ---------------------------------
		// 함수 parameter 정리
		// ---------------------------------
		// (0) 메소드이름 : 상품 속성변경 요청 
		// ---------------------------------
		// (1) 필요시에는 UI 노출
		// (2) action(아이템차감)
		// (3) appId
		// (4) productIds
		// ----------------------------------
		string[] productIds = {"0910024112"};
		iapRequestAdapter.Call ("requestChangeProductProperties", false, "subtract_points", appId, productIds);
		//iapRequestAdapter.Call ("requestChangeProductProperties", true, "subtract_points", appId, productIds); // UI노출 없이 Background로만 진행
	}

	public void RequestCancelSubscription() 
	{
		// ---------------------------------
		// 함수 parameter 정리
		// ---------------------------------
		// (0) 메소드이름 : 상품 속성변경 요청 
		// ---------------------------------
		// (1) 필요시에는 UI 노출
		// (2) action(자동결제 취소)
		// (3) appId
		// (4) productIds
		// ----------------------------------
		string[] productIds = {"0910042744"};
		iapRequestAdapter.Call ("requestChangeProductProperties", false, "cancel_subscription", appId, productIds);
		//iapRequestAdapter.Call ("requestChangeProductProperties", true, "cancel_subscription", appId, productIds); // UI노출 없이 Background로만 진행
	}


	//------------------------------------------------
	//
	// Command - Callback
	//
	//------------------------------------------------

	public void CommandResponse(string response) 
	{
		Debug.Log ("--------------------------------------------------------");
		Debug.Log ("[UNITY] CommandResponse >>> " + response);
		Debug.Log ("--------------------------------------------------------");

		// Parsing Json string to "Reponse" class
		Response data = JsonUtility.FromJson<Response> (response);
		Debug.Log (">>> " + data.ToString());
		Debug.Log ("--------------------------------------------------------");
	}

	public void CommandError(string message) 
	{
		Debug.Log ("--------------------------------------------------------");
		Debug.Log ("[UNITY] CommandError >>> " + message);
		Debug.Log ("--------------------------------------------------------");

		// Parsing Json string to "Error" class
		Error data = JsonUtility.FromJson<Error> (message);
		Debug.Log (">>> " + data.ToString());
		Debug.Log ("--------------------------------------------------------");
	}


	//------------------------------------------------
	//
	// Payment - Request
	//
	//------------------------------------------------

	public void RequestPaymenet()
	{
		// ---------------------------------
		// 함수 parameter 정리
		// ---------------------------------
		// (0) 메소드이름 : 구매요청
		// ---------------------------------
		// (1) appId
		// (2) productId
		// (3) proudtName
		// (4) tId
		// (5) bpInfo
		// ----------------------------------
		iapRequestAdapter.Call ("requestPayment", appId, "0910024112", "UNITY결제", "TID_0123", "BPINFO_0123");
	}

	public void VerifyReceipt() 
	{
		// ---------------------------------
		// 함수 parameter 정리
		// ---------------------------------
		// (0) 메소드이름 : 구매요청
		// ---------------------------------
		// (1) appId
		// (2) txId
		// (3) signData
		// ----------------------------------
		//iapRequestAdapter.Call ("verifyReceipt", appId, txId, signData);
	}

	//------------------------------------------------
	//
	// Payment - Callback
	//
	//------------------------------------------------

	public void PaymentResponse(string response) 
	{
		Debug.Log ("--------------------------------------------------------");
		Debug.Log ("[UNITY] PaymentResponse >>> " + response);
		Debug.Log ("--------------------------------------------------------");

		// Parsing Json string to "Reponse" class
		Response data = JsonUtility.FromJson<Response> (response);
		Debug.Log (">>> " + data.ToString());
		Debug.Log ("--------------------------------------------------------");

		// Try ReceiptVerification
		iapRequestAdapter.Call ("verifyReceipt", appId, data.result.txid, data.result.receipt);
	}

	public void PaymentError(string message) 
	{
		Debug.Log ("--------------------------------------------------------");
		Debug.Log ("[UNITY] PaymentError >>> " + message);
		Debug.Log ("--------------------------------------------------------");

		// Parsing Json string to "Error" class
		Error data = JsonUtility.FromJson<Error> (message);
		Debug.Log (">>> " + data.ToString());
		Debug.Log ("--------------------------------------------------------");
	}

	public void ReceiptVerificationResponse(string result) 
	{
		Debug.Log ("--------------------------------------------------------");
		Debug.Log ("[UNITY] ReceiptVerificationResponse >>> " + result);
		Debug.Log ("--------------------------------------------------------");

		// Parsing Json string to "VerifyReceipt" class
		VerifyReceipt data = JsonUtility.FromJson<VerifyReceipt> (result);
		Debug.Log (">>> " + data.ToString());
		Debug.Log ("--------------------------------------------------------");
	}

	public void ReceiptVerificationError(string message) 
	{
		Debug.Log ("--------------------------------------------------------");
		Debug.Log ("[UNITY] ReceiptVerificationError >>> " + message);
		Debug.Log ("--------------------------------------------------------");

		// Parsing Json string to "Error" class
		Error data = JsonUtility.FromJson<Error> (message);
		Debug.Log (">>> " + data.ToString());
		Debug.Log ("--------------------------------------------------------");
	}

	// ------------------------------------------------------

	#endif
}
