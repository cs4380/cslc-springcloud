<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input
        @keyup.enter.native="handleFilter"
        style="width: 200px;"
        class="filter-item"
        placeholder="角色名称"
        v-model="listQuery.roleName"
      />
      <el-button class="filter-item" type="primary" icon="search" @click="handleFilter">搜索</el-button>
      <el-button
        v-if="menuButton.addRole"
        class="filter-item"
        style="margin-left: 10px;"
        @click="handleCreate"
        type="primary"
        icon="edit"
      >添加角色</el-button>
    </div>
    <el-table
      :data.sync="roleTree"
      v-loading="listLoading"
      row-key="id"
      border
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column align="center" label="角色名称" prop="label" />
      <el-table-column align="center" label="角色编码" prop="roleCode" />
      <el-table-column align="center" label="角色描述" prop="description" />
      <el-table-column fixed="right" align="center" label="操作" width="300">
        <template slot-scope="scope">
          <el-button
            v-if="menuButton.addRoleAuth"
            size="small"
            type="success"
            @click="handleAuthorize(scope.row)"
          >授权</el-button>
          <el-button
            v-if="menuButton.addRole"
            size="small"
            type="primary"
            @click="handleCreate(scope.row)"
          >添加</el-button>
          <el-button
            v-if="menuButton.upRole"
            size="small"
            type="success"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-if="menuButton.delRole"
            size="small"
            type="danger"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog width="35%" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="formTemp" :rules="rules" :model="formTemp" label-width="100px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="formTemp.roleName" placeholder="请输入角色名称" maxlength="128" />
        </el-form-item>
        <el-form-item label="角色编码" prop="roleCode">
          <el-input v-model="formTemp.roleCode" placeholder="请输入角色编码" maxlength="64" />
        </el-form-item>
        <el-form-item label="角色描述" prop="description">
          <el-input
            type="textarea"
            v-model="formTemp.description"
            placeholder="请输入角色描述"
            rows="4"
            maxlength="500"
            show-word-limit
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel()">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="create()">确 定</el-button>
        <el-button v-else type="primary" @click="update()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      width="80%"
      title="角色授权"
      :visible.sync="isAuthorizeDialog"
      @close="clearMenusTree()"
      :close-on-click-modal="false"
    >
      <el-row :gutter="15">
        <el-col :span="10">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>菜单树</span>
            </div>
            <div>
              <el-tree
                :data="menusTree"
                ref="menusTree"
                node-key="id"
                @node-click="checkboxMenuTree"
                show-checkbox
                :props="defaultProps"
              />
            </div>
          </el-card>
        </el-col>
        <el-col :span="14">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>菜单按钮列表</span>
            </div>
            <div>
              <el-table
                :data="buttonList"
                ref="buttonTabale"
                border
                style="width: 100%"
                @select="authMenuBtn"
              >
                <el-table-column type="selection" prop="id" align="center" width="55" />
                <el-table-column prop="buttonTitle" align="center" label="按钮标题" />
                <el-table-column prop="buttonCode" align="center" label="按钮编码" />
                <el-table-column prop="method" align="center" label="请求方式" />
              </el-table>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click=" isAuthorizeDialog = false">取 消</el-button>
        <el-button type="primary" @click="authorizeMenus()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addSysRole,
  delSysRole,
  getSysRole,
  putSysRole,
  getSysRoleTree
} from '@/api/system/sys/role'
import { getMenuTrees } from '@/api/system/sys/menu'
import { sysMenuButtonList } from '@/api/system/sys/menuButton'
import {
  setRoleAuth,
  getAuthByRoleCode,
  getMenuAuthByRoleCode
} from '@/api/system/sys/roleAuth'

export default {
  name: 'SysRole',
  data() {
    return {
      formTemp: {
        id: undefined,
        parentId: undefined,
        roleCode: undefined,
        roleName: undefined,
        description: undefined
      },
      rules: {
        roleCode: [
          {
            required: true,
            message: '请输入角色编码',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 128,
            message: '长度在128以内个字符',
            trigger: 'blur'
          }
        ],
        roleName: [
          {
            required: true,
            message: '请输入角色名称',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 128,
            message: '长度在256以内个字符',
            trigger: 'blur'
          }
        ],
        description: [
          {
            message: '请输入角色描述',
            trigger: 'blur'
          },
          {
            max: 500,
            message: '长度在500以内个字符',
            trigger: 'blur'
          }
        ]
      },
      // 角色树
      roleTree: [],
      // 等待加载
      listLoading: true,
      // 列表查询条件
      listQuery: {
        page: 1,
        limit: 20,
        roleName: undefined
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
      // 授权弹窗
      isAuthorizeDialog: false,
      // 菜单树
      menusTree: [],
      // 菜单树属性
      defaultProps: {
        children: 'children',
        label: 'menuTitle'
      },
      // 按钮列表
      buttonList: [],
      // 资源类型
      resourceType: {
        menu: 0,
        button: 1
      },
      // 角色权限表单
      roleAuthForm: {
        roleCode: undefined,
        resourceIds: [],
        // 0菜单|1按钮
        resourceType: undefined,
        menuCode: undefined
      },
      menuButton: {
        addRole: undefined,
        delRole: undefined,
        upRole: undefined,
        addRoleAuth: undefined
      }
    }
  },
  // 初始化
  created() {
    const sysRole = this.$store.state.user.menuButton.sysRole
    if (sysRole) {
      this.menuButton.addRole = sysRole.addRole
      this.menuButton.upRole = sysRole.upRole
      this.menuButton.delRole = sysRole.delRole
      this.menuButton.addRoleAuth = sysRole.addRoleAuth
    }
    this.getList()
  },
  methods: {
    // 实体列表数据
    getList() {
      this.listLoading = true
      getSysRoleTree(this.listQuery).then(response => {
        this.roleTree = response.data
        this.listLoading = false
      })
    },
    // 列表查询
    handleFilter() {
      this.getList()
    },
    // 打开添加表单弹窗
    handleCreate(row) {
      this.resetFormTemp()
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      // 判断是否添加子角色
      if (row) {
        this.formTemp.parentId = row.id
      }
    },
    // 获取详情，并打开更新表单弹窗
    handleUpdate(row) {
      getSysRole(row.id).then(response => {
        this.formTemp = response.data
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
      })
    },
    // 获取菜单树，并打开授权弹窗
    handleAuthorize(row) {
      this.roleAuthForm.menuCode = undefined
      this.roleAuthForm.roleCode = row.roleCode
      this.resetAuthForm()
      getMenuTrees().then(res => {
        if (res) {
          this.menusTree = res.data
          this.isAuthorizeDialog = true
          // 获取角色菜单树
          getMenuAuthByRoleCode(this.roleAuthForm.roleCode).then(res => {
            if (res) {
              this.$refs.menusTree.setCheckedKeys(res.data)
            }
          })
        }
      })
    },
    // 清空菜单选择数据
    clearMenusTree() {
      this.$refs.menusTree.setCheckedKeys([])
    },
    // 打开删除确认弹窗
    handleDelete(row) {
      this.$confirm('此操作将数据删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delSysRole(row.id).then(res => {
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
      addSysRole(this.formTemp).then(res => {
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
        putSysRole(this.formTemp.id, this.formTemp).then(res => {
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
      this.formTemp.parentId = undefined
      this.formTemp.roleCode = undefined
      this.formTemp.roleName = undefined
      this.formTemp.description = undefined
    },
    // 获取菜单按钮列表
    checkboxMenuTree(data) {
      this.roleAuthForm.menuCode = data.menuCode
      sysMenuButtonList({ menuId: data.id }).then(res => {
        if (res) {
          this.buttonList = res.data.rows
          this.getButAuthByRoleCode()
        }
      })
    },
    // 获取角色菜单的按钮列表
    getButAuthByRoleCode() {
      getAuthByRoleCode(
        this.roleAuthForm.roleCode,
        this.resourceType.button
      ).then(res => {
        if (res) {
          // 获取按钮权限对应的按钮列表
          const buttons = this.getButAuthByButId(res.data)
          if (buttons) {
            // 设置按钮列表的权限选中效果
            buttons.forEach(row => {
              this.$refs.buttonTabale.toggleRowSelection(row)
            })
          } else {
            // 没有授权的按钮，则清空列表多选框选择效果
            this.$refs.multipleTable.clearSelection()
          }
        }
      })
    },
    // 获取按钮权限对应的按钮列表
    getButAuthByButId(butIds) {
      if (butIds) {
        const buttons = []
        // 后台获取的按钮ids和当前页面按钮列表匹配，成功则返回数据
        butIds.forEach(butId => {
          for (const but of this.buttonList) {
            if (parseInt(butId) === but.id) {
              buttons.push(but)
              break
            }
          }
        })
        return buttons
      } else {
        return undefined
      }
    },
    // 授权菜单确认
    authorizeMenus() {
      // 获取选中的子集菜单keys数组
      this.roleAuthForm.resourceIds = this.$refs.menusTree.getCheckedKeys()
      // 获取半选中的菜单keys数组
      const keys = this.$refs.menusTree.getHalfCheckedKeys()
      this.roleAuthForm.resourceIds.push(...keys)
      // 设置授权资源类型：菜单
      this.roleAuthForm.resourceType = this.resourceType.menu
      setRoleAuth(this.roleAuthForm.roleCode, this.roleAuthForm).then(res => {
        if (res) {
          this.$notify({
            title: '菜单授权',
            message: '授权成功',
            type: 'success',
            duration: 2000
          })
          this.isAuthorizeDialog = false
          this.clearMenusTree()
        }
      })
    },
    // 授权菜单按钮
    authMenuBtn(selection) {
      this.roleAuthForm.resourceIds = []
      for (const row of selection) {
        this.roleAuthForm.resourceIds.push(row.id)
      }
      this.roleAuthForm.resourceType = this.resourceType.button
      setRoleAuth(this.roleAuthForm.roleCode, this.roleAuthForm).then(res => {
        if (res) {
          this.$notify({
            title: '按钮授权',
            message: '授权成功',
            type: 'success',
            duration: 2000
          })
        }
      })
    },
    // 初始化授权页面表单
    resetAuthForm() {
      this.roleAuthForm.resourceIds = []
      this.roleAuthForm.resourceType = undefined
      this.buttonList = []
    }
  }
}
</script>

<style scoped>
.filter-container {
  margin-bottom: 15px;
}
</style>
