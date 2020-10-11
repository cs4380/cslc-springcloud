# cslc-springcloud

#### 介绍
基础的RBAC权限微服务管理系统，欢迎感兴趣的小伙伴参与交流学习。

版本：1.2.1正式版   
交流沟通群：78780245

## 一.系统组织架构
### 1.1 目录模块说明
- 注册中心Ncaos  
推荐使用官网稳定版，本项目采用nacos1.3.1

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

- doc目录  
项目相关资料


### 1.2 技术架构说明
项目中技术会定时更新到最新的稳定版本

后端项目技术栈
- 基础技术栈 Spring Cloud、OAuth2、Nacos、Spring Boot、Spring MVC、MyBatis等
- 插件 Lombok、Swagger、Mybatis-plus、PageHelper等
- 数据库 MySql8、Redis6等

前端项目技术栈   
vue、vuex、element-ui、axios等主流技术开发

### 1.3 代码规范
本项目采用 <a href="https://github.com/alibaba/p3c/" target="_blank">阿里代码规范</a>

## 二、开发环境搭建
环境搭建此处只做整体思路说和提供参考文档，如有具体细节有问题可加群交流沟通。

本项目环境推荐，openjdk11、IDEA、虚拟机（Linux）、nginx、docker

### 2.1 后端项目开发环境搭建
#### 2.1.1 maven打包
在当前项目根目录执行，目的下载项目相关依赖，打基础包
```
# 打包当前项目
mvn clean install
```

#### 2.1.2 插件配置（IDEA）
需要的插件可以在编辑器的插件市场下载安装
- 配置Lombok插件， 简化实体的get和set方法等
- 配置maven插件，项目采用maven管理构建

#### 2.1.3 其他配置   
- 安装数据库（mysql8.0.13、redis6.0），推荐采用docker安装

- 执行目录中的建表sql脚本（/doc/db/*）、初始化数据sql脚本（/doc/db/init/*）

- 启动ncaos后，调整cs_ncaos数据库中各个服务的数据库配置，通过web页面手动修改

- 启动其他服务时修改bootstrap.yml中的配置：两种方式任选（1.直接调整NACOS_SERVER_ADDR 和 NACOS_SERVER_PORT为自己ncaos服务的ip地址、端口。
2.参考4.3 IDEA配置外部依赖文档说明）

- 启动基础服务顺序: 1.ncaos服务、2.admin服务、3.auth服务、4.gateway服务

- 系统中的账号密码： ncaos服务（默认账号ncaos，密码ncaos）、业务服务（默认账号admin，密码123456）

### 2.2 前端项目环境搭建
[前端项目环境搭建](https://gitee.com/cslc/cslc-springcloud/blob/master/cslc-admin-ui/README.md)

## 三、项目部署
服务器环境openjdk11、nginx

### 3.1 后端部署
通过maven打包业务项目，生成3个基础服务jar包部署到云服务器，目录结构按自己需求存放即可

- 启动后端项目，在jar包同级目录创建启动start.sh脚本，通过linux命令执行
```
#!/bin/bash
nohup java -jar ./cs-admin.jar >./logs/nohup.out 2>&1 &
```
- 关闭后端项目，直接通过linux杀进程命令：kill -9 xxx（后期优化关闭方式）

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

## 四、项目环境推荐文档
### 4.1 本地开发推荐
- [VSCode官网下载地址](https://code.visualstudio.com/)
- [IDEA官网下载地址](https://www.jetbrains.com/idea/download/#section=windows)
- [Nodejs官网下载地址](https://nodejs.org/en/)
- [Maven安装](https://code.visualstudio.com/)

### 4.2 服务器环境推荐
- [Nacos部署](https://blog.csdn.net/cs4380/article/details/106694604)
- [Cenos7安装docker](https://blog.csdn.net/cs4380/article/details/85777416)
- [Docker中安装mysql](https://blog.csdn.net/cs4380/article/details/89928514)
- [Docker中安装redis](https://blog.csdn.net/cs4380/article/details/86582569)
- [Cenos7安装Nginx](https://blog.csdn.net/cs4380/article/details/104551530)

### 4.3 其他推荐
- [OpenJDK11安装](https://blog.csdn.net/cs4380/article/details/97497455) 服务器和本地推荐安装一致的jdk版本
- [IDEA配置外部依赖](https://blog.csdn.net/cs4380/article/details/108937132) SpringBoot支持依赖外部文件配置，直接在idea中配置环境变量，不需要手动修改bootstrap.yml

## 五、项目预览图
<table>
    <tr>
        <td><img src="http://cslc.gitee.io/cslc-documentation/images/preview/index.png"/></td>
        <td><img src="http://cslc.gitee.io/cslc-documentation/images/preview/user_info.png"/></td>
    </tr>
    <tr>
        <td><img src="http://cslc.gitee.io/cslc-documentation/images/preview/role.png"/></td>
        <td><img src="http://cslc.gitee.io/cslc-documentation/images/preview/menus.png"/></td>
    </tr>
    <tr>
        <td><img src="http://cslc.gitee.io/cslc-documentation/images/preview/api.png"/></td>
        <td><img src="http://cslc.gitee.io/cslc-documentation/images/preview/user_list.png"/></td>
    </tr>
</table>