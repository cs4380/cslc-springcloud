<template>
  <div class="app-container">
    <el-row :gutter="15">
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>菜单目录</span>
          </div>
          <div>
            <el-tree
              :data="menuTree"
              node-key="id"
              default-expand-all
              :expand-on-click-node="false"
              :render-content="renderContent"
            />
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>菜单按钮列表</span>
            <el-button
              v-if="menuIdTemp !== undefined && menuButton.addMenuButton"
              style="float: right; padding: 3px 0"
              type="text"
              @click="handleAddMenuBut"
            >添加按钮</el-button>
          </div>
          <div>
            <el-table :data="buttonList" border style="width: 100%">
              <el-table-column fixed prop="buttonTitle" align="center" label="按钮标题" width="150" />
              <el-table-column prop="buttonCode" align="center" label="按钮编码" />
              <el-table-column prop="url" align="center" label="请求地址" />
              <el-table-column prop="method" align="center" label="请求方式" />
              <el-table-column fixed="right" align="center" label="操作" width="100">
                <template slot-scope="scope">
                  <el-button
                    v-if="menuButton.upMenuButton"
                    type="text"
                    size="small"
                    @click="handleUpButton(scope.row)"
                  >编辑</el-button>
                  <el-button
                    v-if="menuButton.delMenuButton"
                    type="text"
                    size="small"
                    style="color: #F56C6C;"
                    @click="handleDelButton(scope.row)"
                  >删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog
      key="menuForm"
      :title="menuFormTitle[dialogStatus]"
      :visible.sync="menuDialogVisible"
      width="45%"
      :close-on-click-modal="false"
    >
      <el-form ref="menuForm" :rules="rules" :model="menuForm" label-width="120px">
        <el-form-item v-show="menuForm.parentTitle !== undefined" label="父级菜单" prop="parentTitle">
          <el-input disabled v-model="menuForm.parentTitle" />
        </el-form-item>
        <el-form-item label="菜单标题" prop="menuTitle">
          <el-input v-model="menuForm.menuTitle" placeholder="请输入菜单标题" maxlength="16" />
        </el-form-item>
        <el-form-item label="菜单编码" prop="menuCode">
          <el-input v-model="menuForm.menuCode" placeholder="请输入菜单编码" maxlength="128" />
        </el-form-item>
        <el-form-item label="菜单类型" prop="menuType">
          <el-select v-model="menuForm.menuType" placeholder="请选择菜单类型">
            <el-option
              v-for="item in menuTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="菜单图标" prop="menuIcon">
          <el-select v-model="menuForm.menuIcon" placeholder="请选择菜单图标">
            <el-option
              v-for="item in iconOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
              <span style="float: left">
                <svg-icon :icon-class="item.value" />
              </span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{item.label}}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单路由" prop="menuPath">
          <el-input v-model="menuForm.menuPath" placeholder="请输入菜单路由路径" maxlength="128" />
        </el-form-item>
        <el-form-item v-show="menuForm.parentTitle == undefined" label="重定向路由" prop="redirect">
          <el-input v-model="menuForm.redirect" placeholder="请输入重定向路由地址" maxlength="256" />
        </el-form-item>
        <el-form-item label="菜单资源路径" prop="redirect">
          <el-input v-model="menuForm.component" placeholder="请输入菜单资源地址" />
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model.number="menuForm.orderNum" placeholder="请输入排序" maxlength="3" />
        </el-form-item>
        <el-form-item label="菜单描述" prop="description">
          <el-input
            v-model="menuForm.description"
            type="textarea"
            placeholder="请输入菜单描述"
            maxlength="500"
            show-word-limit
            rows="4"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-popover placement="bottom" title="菜单说明" width="400" trigger="click">
          <ul>
            <li>菜单路由：router中path属性.类型为目录前缀必须添加"/",为菜单则不必添加.</li>
            <li>重定向路由：router中redirect属性.类型为目录则需要设置默认打开页面.</li>
            <li>菜单资源路径：router中component属性.路由对应的vue文件.</li>
          </ul>
          <el-button slot="reference">帮助</el-button>
        </el-popover>
        <el-button @click="menuDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateMenuForm()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      key="buttonForm"
      title="菜单按钮"
      :visible.sync="buttonDialogVisible"
      width="45%"
      :close-on-click-modal="false"
    >
      <el-form ref="rulesMenuButton" :rules="rules" :model="buttonForm" label-width="100px">
        <el-form-item label="按钮标题" prop="buttonTitle">
          <el-input v-model="buttonForm.buttonTitle" placeholder="请输入按钮标题" />
        </el-form-item>
        <el-form-item label="按钮编码" prop="buttonCode">
          <el-input v-model="buttonForm.buttonCode" placeholder="请输入按钮编码" />
        </el-form-item>
        <el-form-item label="请求方式" prop="method">
          <el-select v-model="buttonForm.method" placeholder="请选择请求方式">
            <el-option
              v-for="item in methodOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="请求地址" prop="url">
          <el-input v-model="buttonForm.url" placeholder="请输入请求地址" />
        </el-form-item>
        <el-form-item label="菜单描述" prop="description">
          <el-input
            type="textarea"
            v-model="buttonForm.description"
            placeholder="请输入菜单描述"
            maxlength="500"
            show-word-limit
            rows="4"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-popover placement="bottom" title="匹配说明" width="400" trigger="click">
          <ul>
            <li>? 匹配一个字符</li>
            <li>* 匹配0个或多个字符</li>
            <li>** 匹配0个或多个目录</li>
          </ul>
          <el-button slot="reference">帮助</el-button>
        </el-popover>
        <el-button @click="buttonDialogVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus ==='create'" type="primary" @click="addMenuBut()">确 定</el-button>
        <el-button v-else type="primary" @click="upMenuBut()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  addSysMenu,
  delSysMenu,
  getSysMenu,
  putSysMenu,
  getMenuTrees
} from '@/api/system/sys/menu'

import {
  addSysMenuButton,
  delSysMenuButton,
  getSysMenuButton,
  sysMenuButtonList,
  putSysMenuButton
} from '@/api/system/sys/menuButton'

import icons from './icons.json'

export default {
  name: 'SysMenus',
  data() {
    return {
      // 菜单表单
      menuForm: {
        parentId: undefined,
        menuType: 1,
        parentTitle: undefined,
        menuTitle: undefined,
        menuCode: undefined,
        menuIcon: undefined,
        menuPath: undefined,
        redirect: undefined,
        orderNum: 0,
        description: undefined
      },
      // 菜单按钮表单
      buttonForm: {
        id: undefined,
        menuId: undefined,
        buttonCode: undefined,
        buttonTitle: undefined,
        url: undefined,
        method: 'GET',
        description: undefined
      },
      // 菜单按钮列表
      buttonList: [],
      rules: {
        menuCode: [
          {
            required: true,
            message: '请输入菜单编码',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 128,
            message: '长度在128以内个字符',
            trigger: 'blur'
          }
        ],
        menuTitle: [
          {
            required: true,
            message: '请输入菜单标题',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 16,
            message: '长度在16以内个字符',
            trigger: 'blur'
          }
        ],
        menuPath: [
          {
            required: true,
            message: '请输入菜单路径',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 128,
            message: '长度在128以内个字符',
            trigger: 'blur'
          }
        ],
        redirect: [
          {
            message: '请输入重定向地址',
            trigger: 'blur'
          },
          {
            max: 128,
            message: '长度在128以内个字符',
            trigger: 'blur'
          }
        ],
        orderNum: [{ type: 'number', message: '排序必须为数字值' }],
        description: [
          {
            message: '请输入菜单描述',
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
      rulesMenuButton: {
        buttonCode: [
          {
            required: true,
            message: '请输入按钮编码',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 128,
            message: '长度在128以内个字符',
            trigger: 'blur'
          }
        ],
        buttonTitle: [
          {
            required: true,
            message: '请输入按钮标题',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 64,
            message: '长度在64以内个字符',
            trigger: 'blur'
          }
        ],
        url: [
          {
            required: true,
            message: '请输入请求地址',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 256,
            message: '长度在256以内个字符',
            trigger: 'blur'
          }
        ],
        method: [
          {
            required: true,
            message: '请选择请求方式（POST|GET|DELETE|PUT）',
            trigger: 'blur'
          },
          {
            message: '请选择请求方式',
            trigger: 'blur'
          }
        ],
        description: [
          {
            required: true,
            message: '请输入菜单描述',
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
      // 菜单树
      menuTree: [],
      // 菜单弹窗
      menuDialogVisible: false,
      // 菜单按钮
      buttonDialogVisible: false,
      // 缓存菜单id
      menuIdTemp: undefined,
      // update|create
      dialogStatus: '',
      // 菜单按钮弹窗标题
      menuFormTitle: {
        create: '添加菜单按钮',
        update: '编辑菜单按钮'
      },
      // 菜单类型
      menuTypeOptions: [
        {
          value: 0,
          label: '目录'
        },
        {
          value: 1,
          label: '菜单'
        }
      ],
      // 请求方式
      methodOptions: [
        {
          value: 'POST',
          label: '添加'
        },
        {
          value: 'PUT',
          label: '更新'
        },
        {
          value: 'DELETE',
          label: '删除'
        },
        {
          value: 'GET',
          label: '查询'
        }
      ],
      // 图标组
      iconOptions: [],
      menuButton: {
        addMenuButton: undefined,
        upMenuButton: undefined,
        delMenuButton: undefined
      }
    }
  },

  created() {
    const sysMenu = this.$store.state.user.menuButton.sysMenu
    if (sysMenu) {
      this.menuButton.addMenuButton = sysMenu.addMenuButton
      this.menuButton.upMenuButton = sysMenu.upMenuButton
      this.menuButton.delMenuButton = sysMenu.delMenuButton
    }
    // 初始化图标
    this.iconOptions = icons
    // 初始化菜单树
    getMenuTrees()
      .then(response => {
        this.menuTree = response.data
      })
      .catch(function(e) {
        console.log(e)
      })
  },

  methods: {
    // 获取详情，并打开更新表单弹窗
    editMenu(node, data) {
      this.resetFormTemp()
      getSysMenu(data.id).then(res => {
        this.menuForm = res.data
        // 不是根菜单，则显示上级菜单名称
        if (this.menuForm.parentId !== 'root') {
          this.menuForm.parentTitle = node.parent.data.menuTitle
        }
        this.menuDialogVisible = true
      })
    },
    // 添加子菜单节点
    appendMenu(node, data) {
      this.resetFormTemp()
      // 初始化子菜单
      this.menuForm.parentId = data.id
      this.menuForm.menuTitle = '子菜单'
      this.menuForm.menuCode = data.menuCode + '_'
      addSysMenu(this.menuForm).then(res => {
        if (res) {
          // 动态新生成新的节点则
          const newNode = res.data
          newNode.children = []
          // 动态添加新节点
          node.data.children.push(newNode)
          this.$notify({
            title: '成功',
            message: '添加成功',
            type: 'success',
            duration: 2000
          })
        }
      })
    },
    // 删除菜单
    removeMenu(node, data) {
      this.$confirm('此操作将数据删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 判断当前节点是否存在子节点
        if (data.children && data.children.length === 0) {
          delSysMenu(data.id).then(res => {
            if (res) {
              // 动态删除当前节点
              const parent = node.parent
              const children = parent.data.children || parent.data
              // 获取当前节点index
              const index = children.findIndex(d => d.id === data.id)
              // 删除指定节点
              children.splice(index, 1)
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 2000
              })
            }
          })
        } else {
          this.$notify({
            title: '失败',
            message: '请删除当前菜单的所有子菜单',
            type: 'warning',
            duration: 2000
          })
        }
      })
    },
    // 获取菜单按钮列表
    getButtonsByMenuId(id) {
      sysMenuButtonList({ menuId: id }).then(res => {
        if (res) {
          this.buttonList = res.data.rows
        }
      })
    },
    // 获取指定菜单的按钮列表
    handleMenuButList(menu) {
      // 缓存菜单id
      this.menuIdTemp = menu.id
      this.getButtonsByMenuId(this.menuIdTemp)
    },
    // 树编辑按钮
    renderContent(h, { node, data, store }) {
      return (
        <span class="custom-tree-node">
          <span
            on-click={() => this.handleMenuButList(data)}
            class="custom-tree-node-title"
          >
            {node.data.menuTitle}
          </span>
          <span>
            <el-button
              size="mini"
              type="text"
              on-click={() => this.appendMenu(node, data)}
            >
              <i class="el-icon-circle-plus-outline" />
            </el-button>
            <el-button
              size="mini"
              type="text"
              on-click={() => this.editMenu(node, data)}
            >
              <i class="el-icon-edit" />
            </el-button>
            <el-button
              size="mini"
              type="text"
              on-click={() => this.removeMenu(node, data)}
            >
              <i class="el-icon-delete" />
            </el-button>
          </span>
        </span>
      )
    },
    // 获取详情，并打开更新表单弹窗
    updateMenuForm() {
      putSysMenu(this.menuForm.id, this.menuForm).then(res => {
        if (res) {
          this.getMenuTrees()
          this.menuDialogVisible = false
          this.$notify({
            title: '更新',
            message: '更新成功',
            type: 'success',
            duration: 2000
          })
        }
      })
    },
    // 获取菜单树
    getMenuTrees() {
      getMenuTrees().then(response => {
        this.menuTree = response.data
      })
    },
    // 打开添加菜单按钮弹窗
    handleAddMenuBut() {
      this.resetButFormTemp()
      this.buttonDialogVisible = true
      this.dialogStatus = 'create'
    },
    // 添加菜单按钮
    addMenuBut() {
      this.buttonForm.menuId = this.menuIdTemp
      addSysMenuButton(this.buttonForm).then(() => {
        this.buttonDialogVisible = false
        this.getButtonsByMenuId(this.menuIdTemp)
        this.$notify({
          title: '成功',
          message: '添加成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    // 打开菜单按钮表单弹窗
    handleUpButton(row) {
      this.resetButFormTemp()
      this.buttonDialogVisible = true
      this.dialogStatus = 'update'
      getSysMenuButton(row.id).then(res => {
        if (res) {
          this.buttonForm = res.data
        }
      })
    },
    // 更新菜单按钮
    upMenuBut() {
      putSysMenuButton(this.buttonForm.id, this.buttonForm).then(res => {
        if (res) {
          this.buttonDialogVisible = false
          this.getButtonsByMenuId(this.menuIdTemp)
          this.$notify({
            title: '成功',
            message: '更新成功',
            type: 'success',
            duration: 2000
          })
        }
      })
    },
    // 打开字典删除弹窗
    handleDelButton(row) {
      this.$confirm('此操作将数据删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delSysMenuButton(row.id).then(res => {
          if (res) {
            this.getButtonsByMenuId(this.menuIdTemp)
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
    // 初始化菜单表单
    resetFormTemp() {
      this.menuForm.id = undefined
      this.menuForm.parentId = undefined
      this.menuForm.parentTitle = undefined
      this.menuForm.parentCode = undefined
      this.menuForm.menuType = 1
      this.menuForm.menuTitle = undefined
      this.menuForm.menuIcon = undefined
      this.menuForm.menuPath = undefined
      this.menuForm.redirect = undefined
      this.menuForm.orderNum = 0
      this.menuForm.description = undefined
    },
    // 初始化菜单按钮表单
    resetButFormTemp() {
      this.buttonForm.id = undefined
      this.buttonForm.menuId = undefined
      this.buttonForm.buttonCode = undefined
      this.buttonForm.buttonTitle = undefined
      this.buttonForm.url = undefined
      this.buttonForm.method = 'GET'
      this.buttonForm.description = undefined
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

