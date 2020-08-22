<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input
        @keyup.enter.native="handleFilter"
        style="width: 200px;"
        class="filter-item"
        placeholder="用户姓名"
        v-model="listQuery.name"
      />
      <el-button class="filter-item" type="primary" icon="search" @click="handleFilter">搜索</el-button>
      <el-button
        v-if="menuButton.addUser"
        class="filter-item"
        style="margin-left: 10px;"
        @click="handleCreate"
        type="primary"
        icon="edit"
      >添加</el-button>
    </div>
    <el-table
      :data="listObj"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column align="center" label="序号" type="index" width="65" />
      <el-table-column align="center" label="用户账户">
        <template slot-scope="scope">
          <span>{{scope.row.username}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户名称">
        <template slot-scope="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="是否禁用">
        <template slot-scope="scope">
          <span v-if="scope.row.isDisabled ===0">正常</span>
          <span v-else>禁用</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="添加日期">
        <template slot-scope="scope">
          <span>{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="最后更新日期">
        <template slot-scope="scope">
          <span>{{scope.row.updateTime}}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="center" label="操作" width="250">
        <template slot-scope="scope">
          <el-button
            v-if="menuButton.upUserRole"
            size="small"
            type="primary"
            @click="upUserRole(scope.row)"
          >角色</el-button>
          <el-button
            v-if="menuButton.upUser"
            size="small"
            type="success"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-if="menuButton.delUser"
            size="small"
            type="danger"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div v-show="showPagination" class="pagination-container">
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
    <el-dialog width="45%" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="formTemp" :rules="rules" :model="formTemp" label-width="100px">
        <el-form-item label="用户账户" prop="username">
          <el-input v-model="formTemp.username" placeholder="请输入用户账户" />
        </el-form-item>
        <el-form-item v-if="dialogStatus === 'create'" label="用户密码" prop="password">
          <el-input v-model="formTemp.password" placeholder="请输入用户密码" show-password />
        </el-form-item>
        <el-form-item label="用户姓名" prop="name">
          <el-input v-model="formTemp.name" placeholder="请输入用户姓名" />
        </el-form-item>
        <el-form-item label="用户性别" prop="userSex">
          <el-select v-model="formTemp.userSex" placeholder="请选择用户性别">
            <el-option
              v-for="item in sexOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="用户手机号" prop="mobilePhone">
          <el-input v-model="formTemp.mobilePhone" placeholder="请输入用户手机号" />
        </el-form-item>
        <el-form-item label="用户邮箱" prop="userEmail">
          <el-input v-model="formTemp.userEmail" placeholder="请输入用户邮箱" />
        </el-form-item>
        <el-form-item label="是否禁用" prop="isDisabled">
          <el-select v-model="formTemp.isDisabled" placeholder="请选择是否禁用">
            <el-option
              v-for="item in disabledOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="用户说明" prop="description">
          <el-input
            type="textarea"
            v-model="formTemp.description"
            :rows="5"
            maxlength="500"
            show-word-limit
            placeholder="请输入用户说明"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel()">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="create()">确 定</el-button>
        <el-button v-else type="primary" @click="update()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog width="35%" title="用户角色分配" :visible.sync="roleFormVisible">
      <el-table
        :data.sync="roleTree"
        row-key="id"
        border
        ref="roleTree"
        @select="handleUserRole"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <el-table-column type="selection" width="55" prop="label" />
        <el-table-column align="center" label="角色名称" prop="label" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {
  addBaseUser,
  delBaseUser,
  getBaseUserByUserId,
  baseUserList,
  putBaseUser,
  getUserRolesByUserId,
  setUserRole
} from '@/api/system/base/user'
import { getSysRoleTree } from '@/api/system/sys/role'
const Base64 = require('js-base64').Base64

export default {
  name: 'BaseUser',
  data() {
    return {
      formTemp: {
        username: undefined,
        password: undefined,
        name: undefined,
        userSex: 0,
        mobilePhone: undefined,
        description: undefined,
        isDisabled: 0,
        userEmail: undefined
      },
      rules: {
        username: [
          {
            required: true,
            message: '请输入用户账户',
            trigger: 'blur'
          },
          {
            min: 3,
            max: 64,
            message: '长度在 3 至 64 以内个字符',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '请输入用户密码',
            trigger: 'blur'
          },
          {
            min: 7,
            max: 32,
            message: '长度在 7 至 32以内个字符',
            trigger: 'blur'
          }
        ],
        name: [
          {
            required: true,
            message: '请输入用户姓名',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 128,
            message: '长度在64以内个字符',
            trigger: 'blur'
          }
        ],
        userSex: [
          {
            required: false,
            message: '请输入用户性别',
            trigger: 'blur'
          }
        ],
        mobilePhone: [
          {
            required: false,
            message: '请输入用户手机号',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 16,
            message: '长度在16以内个字符',
            trigger: 'blur'
          }
        ],
        userEmail: [
          {
            required: false,
            message: '请输入用户邮箱',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 64,
            message: '长度在64以内个字符',
            trigger: 'blur'
          }
        ],
        description: [
          {
            required: false,
            message: '请输入用户说明',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 500,
            message: '长度在500以内个字符',
            trigger: 'blur'
          }
        ]
      },
      // 列表对象集
      listObj: null,
      // 列表总数
      total: null,
      // 等待加载
      listLoading: true,
      // 列表查询条件
      listQuery: {
        page: 1,
        limit: 20,
        name: undefined
      },
      // 是否弹出（添加|更新）
      dialogFormVisible: false,
      // update|create
      dialogStatus: '',
      textMap: {
        // 表单title
        update: '更新',
        create: '添加'
      },
      sexOptions: [
        {
          value: 0,
          label: '男'
        },
        {
          value: 1,
          label: '女'
        }
      ],
      disabledOptions: [
        {
          value: 0,
          label: '正常'
        },
        {
          value: 1,
          label: '禁用'
        }
      ],
      menuButton: {
        addUser: undefined,
        upUser: undefined,
        delUser: undefined,
        upUserRole: undefined
      },
      // 角色树
      roleTree: [],
      roleFormVisible: false,
      // Map对象数据格式：roleCode:roleTree[xx]
      selectRoleTemp: [],
      userIdTemp: undefined,
      showPagination: false
    }
  },
  // 初始化
  created() {
    this.getList()
    this.initRoleTree()
    const baseUser = this.$store.state.user.menuButton.baseUser
    if (baseUser) {
      this.menuButton.addUser = baseUser.addUser
      this.menuButton.upUser = baseUser.upUser
      this.menuButton.delUser = baseUser.delUser
      this.menuButton.upUserRole = baseUser.upUserRole
    }
  },
  methods: {
    // 实体列表数据
    getList() {
      this.listLoading = true
      baseUserList(this.listQuery).then(response => {
        this.listObj = response.data.rows
        this.total = response.data.total
        this.listLoading = false
        if (this.total >= 20) {
          this.showPagination = true
        }
      })
    },
    // 列表查询
    handleFilter() {
      this.getList()
    },
    // pageSize(页码)改变时会触发
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    // currentPage(当前页) 改变时会触发
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    // 打开添加表单弹窗
    handleCreate() {
      this.resetFormTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    // 获取详情，并打开更新表单弹窗
    handleUpdate(row) {
      getBaseUserByUserId(row.id).then(response => {
        this.formTemp = response.data
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
      })
    },
    // 打开删除确认弹窗
    handleDelete(row) {
      this.$confirm('此操作将数据删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delBaseUser(row.id).then(res => {
          if (res) {
            this.getList()
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              duration: 2000
            })
          }
        })
      })
    },
    // 添加新实体
    create() {
      // 密码base64编码
      this.formTemp.password = Base64.encode(this.formTemp.password)

      addBaseUser(this.formTemp).then(res => {
        if (res) {
          this.dialogFormVisible = false
          this.getList()
          this.$notify({
            title: '成功',
            message: '添加成功',
            type: 'success',
            duration: 2000
          })
        }
      })
    },
    // 取消编辑表单
    cancel(formName) {
      this.dialogFormVisible = false
    },
    // 更新实体
    update(formName) {
      this.$confirm('此操作将数据更新, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dialogFormVisible = false
        putBaseUser(this.formTemp.id, this.formTemp).then(res => {
          if (res) {
            this.dialogFormVisible = false
            this.getList()
            this.$notify({
              title: '更新',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
          }
        })
      })
    },
    // 初始化表单字段
    resetFormTemp() {
      this.formTemp.id = undefined
      this.formTemp.username = undefined
      this.formTemp.password = undefined
      this.formTemp.name = undefined
      this.formTemp.userSex = 0
      this.formTemp.isDisabled = 0
      this.formTemp.mobilePhone = undefined
      this.formTemp.userEmail = undefined
      this.formTemp.description = undefined
    },
    // 打开分配角色弹窗,并获取当前用户的角色
    upUserRole(row) {
      this.userIdTemp = row.id
      this.roleFormVisible = true
      // 获取当前用户角色列表，并添加选中状态
      getUserRolesByUserId(this.userIdTemp).then(res => {
        const roleList = res.data
        // 清空角色选中状态
        this.$refs.roleTree.clearSelection()
        if (roleList) {
          roleList.forEach(row => {
            const role = this.selectRoleTemp.get(row)
            if (role) {
              this.$refs.roleTree.toggleRowSelection(role)
            }
          })
        }
      })
    },
    // 初始化角色树
    initRoleTree() {
      getSysRoleTree().then(response => {
        // 页面中缓存角色树
        this.roleTree = response.data
        // 转换为map存储
        this.selectRoleTemp = new Map()
        this.generateRoleMap(this.roleTree)
      })
    },
    // 生成角色map对象，格式roleCode:roleTree[xx]
    generateRoleMap(roleCodes) {
      roleCodes.forEach(row => {
        if (row.children && row.children.length > 0) {
          this.selectRoleTemp.set(row.roleCode, row)
          this.getRoleTree(row.children)
        } else {
          this.selectRoleTemp.set(row.roleCode, row)
        }
      })
    },
    // 递归循环体
    getRoleTree(rows) {
      rows.forEach(row => {
        if (row.children && row.children.length > 0) {
          this.selectRoleTemp.set(row.roleCode, row)
          this.getRoleTree(row.children)
        } else {
          this.selectRoleTemp.set(row.roleCode, row)
        }
      })
    },
    // 提交更新用户角色
    handleUserRole(selection) {
      const roles = []
      for (const role of selection) {
        roles.push(role.roleCode)
      }
      setUserRole(this.userIdTemp, { roleCodes: roles }).then(res => {
        if (res) {
          this.$notify({
            title: '成功',
            message: '用户角色更新成功',
            type: 'success'
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.filter-container {
  margin-bottom: 15px;
}
</style>
