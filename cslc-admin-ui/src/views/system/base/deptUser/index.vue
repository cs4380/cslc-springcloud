<template>
  <div class="app-container">
    <el-row :gutter="15">
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>部门目录</span>
          </div>
          <div>
            <el-tree
              :data="deptTree"
              node-key="id"
              default-expand-all
              :expand-on-click-node="false"
              :props="{children: 'children',label: 'deptName'}"
              @node-click="handleDeptTree"
            />
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>部门用户列表</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="handleUserRole">
              <span v-show="deptCodeTemp && menuButton.upDeptUser">关联用户</span>
            </el-button>
          </div>
          <div>
            <el-table :data="userList" border style="width: 100%">
              <el-table-column prop="account" align="center" label="用户账号" />
              <el-table-column prop="userName" align="center" label="用户姓名" />
              <el-table-column prop="mobilePhone" align="center" label="联系方式" />
              <el-table-column align="center" label="操作" width="80">
                <template slot-scope="scope">
                  <el-button v-show="menuButton.delDeptUser" type="text" size="small" @click="delDeptUser(scope.row)">删除关系</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div v-show="!listLoading" class="pagination-container">
            <el-pagination
              layout="total, sizes, prev, pager, next, jumper"
              :current-page.sync="listQuery.page"
              :page-sizes="[20,50,100, 150]"
              :page-size="listQuery.limit"
              :total="total"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog title="关联用户" :visible.sync="userAlldialogVisible">
      <el-table row-key="id" :data="userAllList" ref="userAllList" @select="selectDeptUserId">
        <el-table-column type="selection" width="55" />
        <el-table-column property="account" label="用户账户" />
        <el-table-column property="userName" label="用户姓名" />
        <el-table-column property="mobilePhone" label="联系方式" />
      </el-table>
      <div v-show="showPagination" class="pagination-container">
        <el-pagination
          layout="total, sizes, prev, pager, next, jumper"
          :current-page.sync="userAllQuery.page"
          :page-sizes="[10,50,100, 150]"
          :page-size="userAllQuery.limit"
          :total="userAllTotal"
          @size-change="handleUserAllSizeChange"
          @current-change="handleUserAllCurrentChange"
        />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userAlldialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addDeptUserFrom()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  baseDeptTree,
  addDeptUser,
  delDeptUserByUserId
} from '@/api/system/base/dept'
import {
  getDeptUsersByDeptCode,
  getUsersExcludeDept
} from '@/api/system/base/user'

export default {
  name: 'DeptUser',
  data() {
    return {
      // 部门树
      deptTree: [],
      // 用户列表
      userList: [],
      total: 0,
      listQuery: {
        page: 1,
        limit: 20
      },
      // 等待加载
      listLoading: true,
      // 系统所有用户列表
      userAllList: [],
      userAllTotal: 0,
      userAllQuery: {
        page: 1,
        limit: 10,
        // 排除部门编码
        excludeDeptCode: undefined
      },
      showPagination: false,
      // 缓存部门编码
      deptCodeTemp: undefined,
      userAlldialogVisible: false,
      // 缓存
      userIdsTemp: [],
      menuButton: {
        upDeptUser: undefined,
        delDeptUser: undefined
      }
    }
  },

  created: function() {
    const deptUser = this.$store.state.user.menuButton.deptUser
    if (deptUser) {
      this.menuButton.upDeptUser = deptUser.upDeptUser
      this.menuButton.delDeptUser = deptUser.delDeptUser
    }
    baseDeptTree().then(response => {
      this.deptTree = response.data
    })
  },

  methods: {
    getDeptUsers() {
      getDeptUsersByDeptCode(this.deptCodeTemp, this.listQuery).then(res => {
        if (res) {
          this.userList = res.data.rows
          this.total = res.data.total
          if (this.total > 20) {
            this.listLoading = false
          }
        }
      })
    },
    // pageSize(页码)改变时会触发
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getDeptUsers()
    },
    // currentPage(当前页) 改变时会触发
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getDeptUsers()
    },
    handleDeptTree(data) {
      this.deptCodeTemp = data.deptCode
      this.getDeptUsers()
    },
    // 排除当前选中部门的用户
    getDeptUserList() {
      getUsersExcludeDept(this.deptCodeTemp, this.userAllQuery).then(res => {
        if (res) {
          this.userAllList = res.data.rows
          this.userAllTotal = res.data.total
          if (this.userAllTotal > 10) {
            this.showPagination = true
          }
        }
      })
    },
    handleUserAllSizeChange(val) {
      this.userAllQuery.limit = val
      this.getDeptUserList()
    },
    handleUserAllCurrentChange(val) {
      this.userAllQuery.page = val
      this.getDeptUserList()
    },
    handleUserRole() {
      this.getDeptUserList()
      this.userAlldialogVisible = true
    },
    // 选择用户追加到缓存
    selectDeptUserId(selection) {
      this.userIdsTemp = []
      if (selection) {
        selection.forEach(user => {
          this.userIdsTemp.push(user.id)
        })
      }
    },
    addDeptUserFrom() {
      const data = { userIds: this.userIdsTemp }
      if (this.userIdsTemp.length <= 0) {
        this.$message({
          message: '请选择待分配的用户！',
          type: 'warning'
        })
        return
      }
      addDeptUser(this.deptCodeTemp, data).then(res => {
        this.getDeptUsers()
        this.$notify({
          title: '成功',
          message: '部门添加用户关系成功',
          type: 'success'
        })
        this.userAlldialogVisible = false
      })
    },
    delDeptUser(row) {
      this.$confirm('删除用户和部门的关系, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          delDeptUserByUserId(this.deptCodeTemp, row.id).then(() => {
            this.getDeptUsers()
            this.$notify({
              title: '成功',
              message: '部门用户关系删除成功',
              type: 'success'
            })
          })
        })
        .catch(() => {})
    }
  }
}
</script>

<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
.custom-tree-node-title {
  display: inline-block;
  width: 100%;
}
</style>

