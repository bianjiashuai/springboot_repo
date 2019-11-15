# springboot_oauth2
### 1.基于SpringBoot的简单Oauth2

>  获取 token

curl方式：

```shell
curl service-hi:123456@localhost:5000/uaa/oauth/token -d grant_type=password -d username=bjs -d password=123456
```

使用ajax方式：

```javascript
$.ajax({
    url: 'localhost:5000/uaa/oauth/token',
    datatype: 'json',
    type: 'post',
    headers: {'Authorization': 'Basic c2VydmljZS1oaToxMjM0NTY='},
    async: false,
    data: {
        username: 'bjs',
        password: '123456',
        grant_type: 'password'
    },
    success: function(data) {
        // do something
    },
    error: function(data) {
        // do something
    }
});
```

> 使用 token

在请求的Header中加上参数名为：Authorization， 参数值为：Bearer {Token}的参数，如下：

```shell
curl -l -H "Authorization:Bearer eeb2eaee-ad6f-4e7a-a0b7-e099b50eea59" -X GET "localhost:8762/hi"
```

### 2.整合JWT的Oauth2

> 获取 token

```shell
curl user-service:123456@localhost:9999/oauth/token -d grant_type=password -d username=bjs -d password=123456
```

返回信息如下：

```json
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjkyMzcxMDQsInVzZXJfbmFtZSI6InN4eCIsImp0aSI6IjZiMTVkZjQ2LTEzY2ItNGZmMi1iYjA5LWZmZGE2Mjk3ODYxYSIsImNsaWVudF9pZCI6InVzZXItc2VydmljZSIsInNjb3BlIjpbInNlcnZpY2UiXX0.aT0YiouF2U3gFEnVmnW0M3-v5v_Xykzg4NHAw-QLjOfvSO83_VXb6JgH1hDJrsYo_IU1BbfBqjpViPvWHRdMC6UdMP9CIqHlOk8D9DwOS3vTnPwScWHr2rvQWJ9nsArmZYjxTWfCwrikwo57yV_Yeyc0HQoJrI90-kqlyUiynlffxh7RowJHs-k4zVeYNUI03hx6xJLYfEqTaN7njyHKZESzNjBLEMLZPQmKmW4zQZ1Zy0IypEOqIPeFjjN14ZKT4IpAE8Y3MYXRmLJ1GwIbTxOfCUYXokq-KEOkYfB729YDhzpYVbT-52NbB0s_B5FtKo3c8n3IhRhbO6Y46iTC7A",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzE4MjU1MDQsInVzZXJfbmFtZSI6InN4eCIsImp0aSI6IjlkNWNhMjYyLTIzMTMtNGVkYy1iODQ3LWIxN2Q5YTA1NDk3MCIsImNsaWVudF9pZCI6InVzZXItc2VydmljZSIsInNjb3BlIjpbInNlcnZpY2UiXSwiYXRpIjoiNmIxNWRmNDYtMTNjYi00ZmYyLWJiMDktZmZkYTYyOTc4NjFhIn0.Kh_wjZAVPXYPfPpEaLgNRToilm0NEn-EZercsYjETqYJ6OqR5jqrVJl4m_7WutWQBCKRRgRcsWU0_tJ6SFD_h-1MOiExK_cbKJ1LvmwyM0QUIo2UE5AUWUHHXvcXC9J6wO5G24MT4lrpWmZd2GBA7Iw_CFHDQj_WM9p2DlrGcVMcUUzQ9-j3sHvCupsp_koxkv94fX6DP_4hfL8L8Vj9amHVHUdITG4TReaS3GVsqqqluMcjQmwXMozC_oJEX48b06jDNNTscP3QShwW5l9O4nl7DQrLTBP4DPBoSZ17f8BWcKiTFCm1Cq7MC52ogzMqWfLIRILqaTHMdYS5vNjJfg",
    "expires_in": 3599,
    "scope": "service",
    "jti": "6b15df46-13cb-4ff2-bb09-ffda6297861a"
}
```

> 使用 token 同理