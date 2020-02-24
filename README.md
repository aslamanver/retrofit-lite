# Retrofit-Lite

Retrofit-Lite for Android - Lightweight HTTP client based on OKhttp and Retrofit

### Initialization

Once uploaded to JCenter

### Simple Usage

##### 1. GET Request Data
```java
APITask.from(this).sendGET(101, APIClient.API_URL + "/get?leopard=animal", null, this);
```

##### 2. GET Request Headers
```java
Map<String, String> headers = new HashMap<>();
headers.put("leopard", "animal");

APITask.from(this).sendGET(102, APIClient.API_URL + "/headers", headers, this);
```

##### 3. GET Response Headers
```java
APITask.from(this).sendGET(103, APIClient.API_URL + "/response-headers?leopard=animal", null, this);
```

##### 4. GET Response Status Code
```java
APITask.from(this).sendGET(104, APIClient.API_URL + "/status/400", null, this);
```

##### 5. POST Raw Text
```java
APITask.from(this).sendPOST(201, APIClient.API_URL + "/post", "Leopard is an animal", null, this);
```

##### 6. POST JSON Text
```java
APITask.from(this).sendPOST(201, APIClient.API_URL + "/post", "{ \"leopard\" : \"animal\" }", null, this);
```

##### 7. POST Request Headers
```java
Map<String, String> headers = new HashMap<>();
headers.put("leopard", "animal");

APITask.from(this).sendPOST(203, APIClient.API_URL + "/post", "{}", headers, this);
```

##### 9. DigestAuth Success
```java
Map<String, String> headers = new HashMap<>();
headers.put("Authorization", "Digest username=\"postman\", realm=\"Users\", nonce=\"ni1LiL0O37PRRhofWdCLmwFsnEtH1lew\", uri=\"/digest-auth\", response=\"254679099562cf07df9b6f5d8d15db44\", opaque=\"\"");

APITask.from(this).sendGET(205, APIClient.API_URL + "/digest-auth", headers, this);
```

##### 10. POST JSON Text with Object
```java
SampleReq req = new SampleReq();
req.email = "google@gmail.com";

APITask.from(this).sendPOST(206, APIClient.API_URL + "/post", req, null, this);
```

#### 11. POST Empty Body Method
```java
APITask.from(this).sendPOST(207, APIClient.API_URL + "/post", null, this);
```

#### 12. Timeout
```java
APIClient.ConfigBuilder clientBuilder = new APIClient.ConfigBuilder()
                    .setTimeout(3000);

APITask.from(this, clientBuilder).sendGET(301, APIClient.API_URL + "/delay/5", null, this);
```

#### 13. Host Verification
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

