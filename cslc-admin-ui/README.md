# cslc-admin-ui

权限系统前端项目
项目基于vue、vuex、element-ui、axios等主流后端技术开发

## 一、目录模块说明
TODO
## 二、开发环境搭建
### 2.1 环境配置
- nodejs 推荐安装最新版本
- VSCode(推荐,不限制)

编辑器打开项目，执行以下命令做项目初始化
```bash
# 进入项目目录
# cd cslc-admin-ui

# 配置淘宝源，提高下载速度（可选）
npm config set registry https://registry.npm.taobao.org

# 安装项目依赖
npm install

# 启动服务
npm run dev
```
启动后浏览器访问 [http://localhost:9528/](http://localhost:9528)

## 三、项目发布说明
通过执行打包命令后，在当前项目根目录生成dist的文件夹，部署到云服务器的nginx中
```
# 构建生产环境
npm run build:prod
```

## 四、VSCode环境配置（可选）
#### 4.1 Vetur格式化配置
```
{
    "vetur.format.defaultFormatterOptions": {
        "prettier": {
            // 不加分号
            "semi": false,
            // 用单引号
            "singleQuote": true,
            // 禁止随时添加逗号
            "trailingComma": "none"
        }
    }
}
```
#### 4.2 editorConfig
不同的编辑器和 IDE 中保持一致的编码样式，有些编辑器默认支持editorConfig，如webstorm；而有些编辑器则需要安装editorConfig插件，如ATOM、Sublime、VS Code等

