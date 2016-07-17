
> 인앱 결제 `github` 업로드
>> 1. 원스토어 인앱결제SDK와 가이드를 깃허브에 동시 업데이트 합니다. 
>> 1. [원스토어 개발자센터](http://dev.onestore.co.kr/devpoc/reference/view/Tools_IAP)를 통해서도 동일한 내용을 확인할 수 있습니다. 


# ONE스토어 인앱결제란?

ONE스토어 인앱결제(이하 IAP)는 ONE스토어 이용자들이 앱 내에서 구입할 수 있는 별도 인앱상품을 구매하기 위한 인증 및 결제 시스템이다. 개발사 앱에 IAP 모듈을 적용하면 인앱상품 이용 권한 및 결제 요청 시 ONEstore service 앱에서 해당 상품을 확인하여 결제를 수행한다. 또한 ONE스토어 앱를 통해 인앱상품을 관리하고 결제 내역을 확인할 수 있다.

다음은 원스토어 인앱결제의 서비스 구조를 나타낸 것이다.

![enter image description here](https://lh3.googleusercontent.com/-3cXqpbmAXrI/V4Sxzil98qI/AAAAAAAAe5A/E7b5TIV55WIKkduLAP8qWBYTtnBmie_4gCKgB/s0/iapOverview.png "iapOverview.png")

IAP 모듈은 IAP SDK(In-App Purchase Software Development Kit)라는 java 개발 라이브러리 형태로 제공된다. 개발사 앱에 SDK를 적용한 후 인앱결제 관련 함수를 호출하면 IAP서버로 요청이 전달된다. IAP 서버는 구매 요청에 대한 결과를 JSON 형태의 응답 데이터를 생성하여 개발자 앱으로 전송한다. 


# 개발자 가이드 

- [위키 페이지](https://github.com/ONE-store/inapp-sdk/wiki)
- [개발자센터](http://dev.onestore.co.kr/devpoc/reference/view/Tools_IAP)


# 권장 개발 환경

안드로이드용 애플리케이션에 IAP SDK를 적용하려면 다음과 같은 개발 환경이 필요하다.

* Android 2.3 이상 버전(API 버전 9 이상)
* [Java SDK 1.6 버전](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Android studio 2.0 이상 버전](https://developer.android.com/studio/index.html)

>* Eclipse 에서는 아래 개발환경을 권장합니다.
	* [Eclipse IDE for Java Developers](http://www.eclipse.org/downloads)
	* [ADT Plug-in for Eclipse 15 버전 이상](http://developer.android.com/sdk/eclipse-adt.html)


