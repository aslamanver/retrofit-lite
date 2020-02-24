# Retrofit-Lite

Retrofit-Lite for Android - Lightweight HTTP client based on OKhttp and Retrofit

## Initialization

Once uploaded to JCenter

## Simple Usage

1. GET Request Data

```java
APITask.from(this).sendGET(101, APIClient.API_URL + "/get?leopard=animal", null, this);
```

