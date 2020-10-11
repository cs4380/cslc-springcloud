# 授权认证服务
## 公钥私钥生成
java环境中通过命令行执行
```
 keytool -genkeypair -alias authkey -keyalg RSA -keypass authpass -keystore authkey.jks -storepass authpass
```
Keytool 是一个java提供的证书管理工具
-alias：密钥的别名
-keyalg：使用的hash算法
-keypass：密钥的访问密码
-keystore：密钥库文件名，xc.keystore保存了生成的证书
-storepass：密钥库的访问密码