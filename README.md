# Retrofit-Lite

[ ![Download](https://api.bintray.com/packages/aslam/android/retrofit-lite/images/download.svg?version=1.0.4) ](https://bintray.com/aslam/android/retrofit-lite/1.0.4/link) [![](https://jitpack.io/v/aslamanver/retrofit-lite.svg)](https://jitpack.io/#aslamanver/retrofit-lite) [![Build Status](https://travis-ci.org/aslamanver/retrofit-lite.svg?branch=master)](https://travis-ci.org/aslamanver/retrofit-lite)

Retrofit-Lite for Android - Lightweight HTTP client based on OKhttp and Retrofit.

### Initialization

Add the below dependency into your module level `build.gradle` file

```gradle
implementation 'com.aslam:retrofit-lite:1.0.4'
```

### Simple Usage

For the testing purpose we used Postman-Echo URL as `APIClient.API_URL = "https://postman-echo.com"`, it can be replaced with your server address once tested.

#### GET method with parameter
```java
APITask.from(context).sendGET(101, "https://your-url.com/?anyparam=anything", null, new APITask.Listener() {
    @Override
    public void onSuccess(int pid, int status, Map<String, String> headers, String body) {

    }

    @Override
    public void onFailed(int pid, Exception ex) {

    }
});
```

#### POST method with parameter
```java
APITask.from(this).sendPOST(201, "https://your-url.com", "{ \"leopard\" : \"animal\" }", null, new APITask.Listener() {
    @Override
    public void onSuccess(int pid, int status, Map<String, String> headers, String body) {
    
    }

    @Override
    public void onFailed(int pid, Exception ex) {

    }
});
```

The object can also be sent in the `sendPOST` as serialized, please refer the section `10. POST JSON Text with Object`

#### Response callbacks
These methods are called with the process id which is given when initiated and the status code of the response.
```java
void onSuccess(int pid, int status, Map<String, String> headers, String body);
void onFailed(int pid, Exception ex);
```

<hr>

### Advanced usage
Implement the `APITask.Listener` with the activity class or you can directly pass into the method.

##### 1. GET Request Data

Simple GET request with parameters

```java
APITask.from(this).sendGET(101, APIClient.API_URL + "/get?leopard=animal", null, this);
```

##### 2. GET Request Headers

Simple GET request with request headers

```java
Map<String, String> headers = new HashMap<>();
headers.put("leopard", "animal");

APITask.from(this).sendGET(102, APIClient.API_URL + "/headers", headers, this);
```

##### 3. GET Response Headers

Simple test of response headers

```java
APITask.from(this).sendGET(103, APIClient.API_URL + "/response-headers?leopard=animal", null, this);
```

##### 4. GET Response Status Code

400 Status code

```java
APITask.from(this).sendGET(104, APIClient.API_URL + "/status/400", null, this);
```

##### 5. POST Raw Text

Plain text on POST method

```java
APITask.from(this).sendPOST(201, APIClient.API_URL + "/post", "Leopard is an animal", null, this);
```

##### 6. POST JSON Text

JSON text on POST method

```java
APITask.from(this).sendPOST(201, APIClient.API_URL + "/post", "{ \"leopard\" : \"animal\" }", null, this);
```

##### 7. POST Request Headers

POST request with headers

```java
Map<String, String> headers = new HashMap<>();
headers.put("leopard", "animal");

APITask.from(this).sendPOST(203, APIClient.API_URL + "/post", "{}", headers, this);
```

##### 9. DigestAuth Success

Simple autentication method with header

```java
Map<String, String> headers = new HashMap<>();
headers.put("Authorization", "Digest username=\"postman\", realm=\"Users\", nonce=\"ni1LiL0O37PRRhofWdCLmwFsnEtH1lew\", uri=\"/digest-auth\", response=\"254679099562cf07df9b6f5d8d15db44\", opaque=\"\"");

APITask.from(this).sendGET(205, APIClient.API_URL + "/digest-auth", headers, this);
```

##### 10. POST JSON Text with Object

Serialize the object using `GSON` and implement the `APITask.Request` with the class.

```java
public class SampleReq implements APITask.Request {

    @Expose
    String email;

    @Expose
    String password;
}
```

```java
SampleReq req = new SampleReq();
req.email = "google@gmail.com";

APITask.from(this).sendPOST(206, APIClient.API_URL + "/post", req, null, this);
```

##### 11. POST Empty Body Method

POST method without any parameters

```java
APITask.from(this).sendPOST(207, APIClient.API_URL + "/post", null, this);
```

##### 12. Timeout

Custom timeout, default is 1 minute 

```java
APIClient.ConfigBuilder clientBuilder = new APIClient.ConfigBuilder()
                    .setTimeout(3000);

APITask.from(this, clientBuilder).sendGET(301, APIClient.API_URL + "/delay/5", null, this);
```

##### 13. Host Verification

Custom host name verification

```java
APIClient.ConfigBuilder clientBuilder = new APIClient.ConfigBuilder()
                    .setHostnameVerifier(new APIClient.ConfigBuilder.HostnameVerifier() {
                        @Override
                        public boolean onVerify(String hostname, SSLSession session) {
                            return false;
                        }
                    });

APITask.from(this, clientBuilder).sendGET(302, APIClient.API_URL + "/get", null, this);
```

##### 14. No Callback

Set the listener to null to ignore callbacks

```java
APITask.from(this).sendGET(500, APIClient.API_URL + "/get", null, null);
```
