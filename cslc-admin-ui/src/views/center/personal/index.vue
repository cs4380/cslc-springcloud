<template>
  <div class="app-container">
    <el-row :gutter="15">
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>基本信息</span>
          </div>
          <div>
            <div style="text-align:center;margin-bottom:15px">
              <el-image fit="fit" class="active" :src="baseInfo.portrait" />
            </div>
            <div class="base-user-info">
              <div>
                姓名：
                <span>{{baseInfo.name}}</span>
              </div>
              <div>
                性别：
                <span>{{baseInfo.sex}}</span>
              </div>
              <!--
              TODO 暂时未开发部门关系
              <div>
                部门：
                <span>{{baseInfo.depts}}</span>
              </div>-->
              <div>
                电子邮箱：
                <span>{{baseInfo.email}}</span>
              </div>
              <div style="border-bottom: 1px solid #e3e9ed;">
                注册日期：
                <span>{{baseInfo.createTime}}</span>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card class="box-card">
          <el-tabs v-model="activeName">
            <el-tab-pane label="绑定信息" name="first" style="height: 350px;">
              <div class="user-info-bind">
                <div>
                  手机号码
                  <span class="title-bind">{{baseInfo.phone}}</span>
                  <span class="edit-bind" @click="handleUpPhone()">
                    <i class="el-icon-edit-outline" style="color:#409EFF" />
                    <el-button type="text">修改</el-button>
                  </span>
                </div>
                <div>
                  电子邮箱
                  <span class="title-bind">{{baseInfo.email}}</span>
                  <span class="edit-bind" @click="handleUpEmail()">
                    <i class="el-icon-edit-outline" style="color:#409EFF" />
                    <el-button type="text">修改</el-button>
                  </span>
                </div>
                <div>
                  手机微信
                  <span class="title-bind">{{baseInfo.wx}}</span>
                  <span class="edit-bind" @click="handleUpEmail()">
                    <i class="el-icon-edit-outline" style="color:#409EFF" />
                    <el-button type="text">修改</el-button>
                  </span>
                </div>
                <div>
                  其他第三方
                  <span class="title-bind">{{baseInfo.wb}}</span>
                  <span class="edit-bind" @click="handleUpEmail()">
                    <i class="el-icon-edit-outline" style="color:#409EFF" />
                    <el-button type="text">修改</el-button>
                  </span>
                </div>
              </div>
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="password">
              <el-form ref="secret" label-position="top" :model="secret" label-width="100px">
                <el-form-item label="原始密码">
                  <el-input
                    v-model="secret.password"
                    placeholder="请输入原始密码"
                    show-password
                    maxlength="256"
                  />
                </el-form-item>
                <el-form-item label="新密码">
                  <el-input
                    v-model="secret.newPassword"
                    placeholder="请输入新密码"
                    show-password
                    maxlength="256"
                  />
                </el-form-item>
                <el-form-item label="再次输入密码">
                  <el-input
                    v-model="secret.confirmPassWord"
                    placeholder="请再次输入新密码"
                    show-password
                    maxlength="256"
                  />
                </el-form-item>
                <el-button type="primary" @click="updatePassword()">更新密码</el-button>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="其他" name="second">其他</el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog key="email" title="绑定邮箱" :visible.sync="emailDialogVisible" width="45%">
      <div>
        <el-steps :active="1" align-center>
          <el-step title="验证身份" />
          <el-step title="修改邮箱" />
          <el-step title="完成" />
        </el-steps>
      </div>
      <!-- <el-form ref="email" :model="baseInfo" label-width="100px">
        <el-form-item label="新邮箱">
          <el-input v-model="baseInfo.email" placeholder="请输入邮箱" maxlength="256" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="emailDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateEmail()">确 定</el-button>
      </span>-->
    </el-dialog>

    <el-dialog key="phone" title="绑定手机号" :visible.sync="phoneDialogVisible" width="45%">
      <div>
        <el-steps :active="1" align-center>
          <el-step title="验证身份" />
          <el-step title="修改手机号" />
          <el-step title="完成" />
        </el-steps>
      </div>
      <!-- <el-form ref="phone" :model="baseInfo" label-width="100px">
        <el-form-item label="新手机号">
          <el-input v-model="baseInfo.phone" placeholder="请输入手机号" maxlength="12" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="phoneDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updatePhone()">确 定</el-button>
      </span>-->
    </el-dialog>
  </div>
</template>

<script>
const Base64 = require('js-base64').Base64
import { getBaseUser, changePassword } from '@/api/system/base/user'
export default {
  name: 'Personal',
  data() {
    return {
      activeName: 'first',
      // 邮箱弹窗
      emailDialogVisible: false,
      // 手机弹窗
      phoneDialogVisible: false,
      // 用户基本信息
      baseInfo: {
        name: undefined,
        sex: undefined,
        depts: undefined,
        portrait: undefined,
        phone: undefined,
        email: undefined,
        createTime: undefined
      },
      secret: {
        // 原始密码
        password: undefined,
        // 新密码
        newPassword: undefined,
        // 确认密码
        confirmPassWord: undefined
      }
    }
  },
  created() {
    // 初始化用户信息
    getBaseUser().then(res => {
      if (res) {
        const user = res.data.baseUser

        this.baseInfo.name = user.userName

        if (user.userSex === 1) {
          this.baseInfo.sex = '女'
        } else {
          this.baseInfo.sex = '男'
        }

        this.baseInfo.depts = undefined
        // 设置头像
        let portrait = user.portrait
        if (!portrait) {
          const userPortrait = 'user_portrait.png'
          portrait = require(`../../../../static/default/` + userPortrait)
        }
        this.baseInfo.portrait = portrait

        this.baseInfo.phone = user.mobilePhone
        this.baseInfo.email = user.userEmail
        // 注册日期截取
        let createTime = user.createTime
        if (createTime) {
          createTime = createTime.substring(0, 11)
        }
        this.baseInfo.createTime = createTime
      }
    })
  },
  methods: {
    // 修改密码
    updatePassword() {
      // 判断是否输入原始密码
      if (this.secret.password === undefined) {
        this.$message({
          type: 'error',
          message: '请输入原始密码'
        })
        return
      }
      // 新密码是否一致
      if (this.secret.confirmPassWord !== this.secret.newPassword) {
        this.$message({
          type: 'error',
          message: '新密码不一致，请检查新密码！'
        })
        return
      }
      // base64编码
      const password = Base64.encode(this.secret.password)
      const newPassword = Base64.encode(this.secret.newPassword)
      // 设置密码
      changePassword({ password: password, newPassword: newPassword }).then(
        res => {
          if (res) {
            this.secret.password = undefined
            this.secret.newPassword = undefined
            this.secret.confirmPassWord = undefined
            this.$message({
              type: 'success',
              message: '密码修改成功，请重新登陆！'
            })
          }
        }
      )
    },
    // 打开修改邮箱弹窗
    handleUpEmail() {
      this.emailDialogVisible = true
    },
    // 修改邮箱
    updateEmail() {
      this.emailDialogVisible = false
    },
    // 打开修改手机号弹窗
    handleUpPhone() {
      this.phoneDialogVisible = true
    },
    // 修改手机号
    updatePhone() {
      // todo 调用后台接口
      this.phoneDialogVisible = false
    }
  }
}
</script>

<style lang="scss" scoped>
// 左侧个人信息布局开始
.active {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}
.base-user-info {
  font-size: 14px;
  color: #999;
}
.base-user-info div {
  border-top: 1px solid #e3e9ed;
  height: 40px;
  line-height: 40px;
}
.base-user-info div span {
  float: right;
}
// 左侧个人信息布局结束

// 右侧绑定布局开始
.user-info-bind {
  font-size: 15px;
  color: #999;
  text-decoration: none;
}
.user-info-bind div {
  border-bottom: 1px solid #e3e9ed;
  height: 66px;
  line-height: 65px;
}
.title-bind {
  margin-left: 50px;
}
.edit-bind {
  float: right;
  cursor: pointer;
}
// 右侧绑定布局结束
</style>
