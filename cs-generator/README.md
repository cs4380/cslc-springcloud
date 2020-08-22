# generator服务
代码生成器，启动后通过可视化页面，操作数据库表生成前后端代码包

## 1、配置数据库
修改application.yml中mysql配置 ,直接修改以下配置项的默认值即可
```
    url: jdbc:mysql://${MYSQL_HOST:127.0.0.1}:${MYSQL_PORT:3306}/base_db?useUnicode=true&characterEncoding=UTF8
    username: ${MYSQL_USER_NAME:root}
    password: ${MYSQL_PASS:123456}
```
## 2、可视化页面
启动GeneratorBootstrap.java程序，访问地址：http://localhost:8080/index