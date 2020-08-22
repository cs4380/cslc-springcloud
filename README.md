# cslc-springcloud

#### 介绍
基础的RBAC权限微服务管理系统，欢迎感兴趣的小伙伴参与交流学习。

已发布2.0测试版，预计今年国庆发布正式版，感谢大家关注。   

交流沟通群：78780245

## 一.系统组织架构
### 1.1 目录模块说明
- 注册中心Ncaos  
推荐使用1.2.0版本，本项目采用mysql8.0，nacos默认为mysql5.7，可通过官网源码修改依赖并编译，或者加群（78780245）下载编译好的包

- admin  
  提供后台管理基础功能的服务
    - 用户管理
    - 组织机构
    - 角色权限管理
    - 数据字典
    - 菜单管理
  
- auth  
认证授权服务，登陆验证等功能  
  
- base  
提供系统公共基础相关工具的包
  
- gateway  
网关服务，对外统一网关

- generator
代码生成器，独立项目，可视化页面操作数据库表生成前后端代码包

- doc目录  
项目相关资料


### 1.2 技术架构说明
后端项目技术栈
- 基础技术栈 Spring Cloud、OAuth2、Nacos、Spring Boot、Spring MVC、MyBatis等
- 插件 Lombok、Swagger、Mybatis-plus、PageHelper等
- 数据库 MySql8.0.13、Redis6.0等

前端项目技术栈   
vue、vuex、element-ui、axios等主流技术开发

### 1.3 代码规范
本项目采用 <a href="https://github.com/alibaba/p3c/" target="_blank">阿里代码规范</a>

## 二、开发环境搭建
环境搭建此处只做整体思路的说明，具体细节有问题可加群交流沟通

### 2.1 后端项目环境搭建
#### 2.1.1 编辑器插件
- Lombok插件，需要手动下载并安装到编辑器中
- maven插件，一般编辑器都自带，只需要和本地maven关联配置即可   

#### 2.1.2 maven打包
在当前项目根目录执行，目的下载项目相关依赖，打基础包
```
# 打包当前项目
mvn clean install
```

#### 2.1.3 项目配置   
- 执行目录中的建表sql脚本（/doc/db/*）、初始化数据sql脚本（/doc/db/init/*）
- 配置数据库（mysql、redis），调整Ncaos数据库中的配置
- 启动基础服务顺序:
1.Ncaos服务、2.admin服务、3.auth服务、4.gateway服务。默认账号admin，密码123456

### 2.2 前端项目环境搭建
[前端项目admin-ui说明](https://gitee.com/cslc/cslc-springcloud/blob/master/cslc-admin-ui/README.md)

## 三、项目部署
服务器环境jdk11、nginx最新版
### 3.1 后端部署
通过maven打包业务项目，生成3个基础服务jar包部署到云服务器。
- 启动后端项目，在jar包同级目录创建启动start.sh脚本，通过linux命令执行
- 关闭后端项目，直接通过linux杀进程命令：kill -9 xxx（后期优化关闭方式）
```
#!/bin/bash
nohup java -server -Xms256m -Xmx256m -jar ./cs-admin.jar >./logs/nohup.out 2>&1 &
```
### 3.2 前端部署
在nginx的配置文件中nginx.conf，添加以下配置
```
    server {
        listen       80;
        # 公网域名或者ip地址
        server_name  www.xxxxx.cn;
        location ^~ /api/ {
           # 后端程序地址
           proxy_pass http://localhost:4380;
           proxy_connect_timeout 500s;
           proxy_read_timeout 500s;
           proxy_send_timeout 500s;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
        
        location / {
            # 前端项目部署目录
            root   /html/cslc-admin-ui/;
            index  index.html index.htm;
        }
    }
```

## 四、项目预览图
<table>
    <tr>
        <td><img src="http://images.cslc-admin.1024wz.cn/index.png"/></td>
        <td><img src="http://images.cslc-admin.1024wz.cn/user_info.png"/></td>
    </tr>
    <tr>
        <td><img src="http://images.cslc-admin.1024wz.cn/role.png"/></td>
        <td><img src="http://images.cslc-admin.1024wz.cn/menus.png"/></td>
    </tr>
    <tr>
        <td><img src="http://images.cslc-admin.1024wz.cn/api.png"/></td>
        <td><img src="http://images.cslc-admin.1024wz.cn/user_list.png"/></td>
    </tr>
</table>