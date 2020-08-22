<template>
  <div class="app-container">
    <el-row :gutter="15">
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>字典目录</span>
          </div>
          <div>
            <el-tree
              :data.sync="dictTree"
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
            <span>字典值</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              v-show="dictTypeId !== undefined && menuButton.addDictValue"
              @click="handleAddDictValue"
            >操作按钮</el-button>
          </div>
          <div>
            <el-table :data="dictValueList" border fit style="width: 100%">
              <el-table-column fixed prop="dictCode" align="center" label="字典编码" />
              <el-table-column prop="dictTitle" align="center" label="字典值" />
              <el-table-column prop="orderNum" align="center" label="排序" />
              <el-table-column prop="description" align="center" label="描述" />
              <el-table-column prop="updateTime" align="center" label="更新日期" />
              <el-table-column fixed="right" align="center" label="操作" width="100">
                <template slot-scope="scope">
                  <el-button v-if="menuButton.upDictValue" @click="handleUpDictValue(scope.row)" type="text" size="small">编辑</el-button>
                  <el-button v-if="menuButton.delDictValue" @click="handleDelDictValue(scope.row)" type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog key="dictTypeForm" title="字典类型" :visible.sync="dictTypeDialogVisible" width="45%">
      <el-form ref="dictTypeForm" :model="dictType" :rules="rules" label-width="100px">
        <el-form-item label="类型名称" prop="dictTypeName">
          <el-input v-model="dictType.dictTypeName" placeholder="请输入类型名称" maxlength="256" />
        </el-form-item>
        <el-form-item label="类型编码" prop="dictTypeCode">
          <el-input v-model="dictType.dictTypeCode" placeholder="请输入类型编码" maxlength="128" />
        </el-form-item>
        <el-form-item label="类型排序" prop="orderNum">
          <el-input
            v-model.number="dictType.orderNum"
            placeholder="请输入类型排序"
            style="width: 35%"
            maxlength="3"
          />
        </el-form-item>
        <el-form-item label="类型说明" prop="description">
          <el-input
            v-model="dictType.description"
            type="textarea"
            rows="4"
            placeholder="请输入类型说明"
            maxlength="500"
            show-word-limit
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dictTypeDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="update()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog key="dictValueForm" title="字典值" :visible.sync="dictValueDialogVisible" width="45%">
      <el-form ref="dictValueForm" :model="dictValue" :rules="rulesDictValue" label-width="100px">
        <el-form-item label="字典值" prop="dictTitle">
          <el-input v-model="dictValue.dictTitle" placeholder="请输入字典值" maxlength="256" />
        </el-form-item>
        <el-form-item label="字典编码" prop="dictCode">
          <el-input v-model="dictValue.dictCode" placeholder="请输入字典编码" maxlength="128" />
        </el-form-item>
        <el-form-item label="字典排序" prop="orderNum">
          <el-input
            v-model.number="dictValue.orderNum"
            placeholder="请输入字典排序"
            style="width: 35%"
            maxlength="3"
          />
        </el-form-item>
        <el-form-item label="字典说明" prop="description">
          <el-input
            v-model="dictValue.description"
            type="textarea"
            rows="4"
            placeholder="请输入字典说明"
            maxlength="500"
            show-word-limit
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dictValueDialogVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus ==='create'" type="primary" @click="addDictValue()">确 定</el-button>
        <el-button v-else type="primary" @click="upDictValue()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getDictTypeTree,
  getDictTypeById,
  putSysDictType,
  delSysDictType,
  addSysDictType
} from '@/api/system/sys/dictType'

import {
  sysDictValueList,
  addSysDictValue,
  getSysDictValue,
  delSysDictValue,
  putSysDictValue
} from '@/api/system/sys/dictValue'

export default {
  data() {
    return {
      name: 'SysDict',
      // 字典类型
      dictType: {
        dictTypeName: undefined,
        dictTypeCode: undefined,
        orderNum: undefined,
        description: undefined
      },
      // 字典值
      dictValue: {
        dictCode: undefined,
        dictTitle: undefined,
        orderNum: undefined,
        description: undefined
      },
      // 字典列表
      dictValueList: [],
      rules: {
        dictTypeName: [
          {
            required: true,
            message: '请输入类型名称',
            trigger: 'blur'
          },
          {
            max: 128,
            message: '请输入128以内个字符',
            trigger: 'blur'
          }
        ],
        dictTypeCode: [
          {
            required: true,
            message: '请输入类型编码',
            trigger: 'blur'
          },
          {
            max: 128,
            message: '请输入128以内个字符',
            trigger: 'blur'
          }
        ],
        orderNum: [
          {
            required: true,
            message: '请输入类型排序',
            trigger: 'blur'
          },
          {
            type: 'number',
            message: '类型排序必须为数字值',
            trigger: 'blur'
          }
        ]
      },
      rulesDictValue: {
        dictTitle: [
          {
            required: true,
            message: '请输入字典值',
            trigger: 'blur'
          },
          {
            max: 128,
            message: '请输入128以内个字符',
            trigger: 'blur'
          }
        ],
        dictCode: [
          {
            required: true,
            message: '请输入字典编码',
            trigger: 'blur'
          },
          {
            max: 128,
            message: '请输入128以内个字符',
            trigger: 'blur'
          }
        ],
        orderNum: [
          {
            required: true,
            message: '请输入字典排序',
            trigger: 'blur'
          },
          {
            type: 'number',
            message: '类型排序必须为数字值',
            trigger: 'blur'
          }
        ]
      },
      // 字典类型树
      dictTree: [],
      // 字典类型
      dictTypeDialogVisible: false,
      // 字典
      dictValueDialogVisible: false,
      // 缓存分类id
      dictTypeId: undefined,
      // 缓存分类code
      tempDictTypeCode: undefined,
      // update|create
      dialogStatus: '',
      menuButton: {
        addDictValue: undefined,
        upDictValue: undefined,
        delDictValue: undefined
      }
    }
  },

  created: function() {
    const sysDict = this.$store.state.user.menuButton.sysDict
    if (sysDict) {
      this.menuButton.addDictValue = sysDict.addDictValue
      this.menuButton.upDictValue = sysDict.upDictValue
      this.menuButton.delDictValue = sysDict.delDictValue
    }
    getDictTypeTree()
      .then(response => {
        this.dictTree = response.data
      })
      .catch(function(e) {
        console.log(e)
      })
  },

  methods: {
    // 获取详情，并打开更新表单弹窗
    editDictType(data) {
      getDictTypeById(data.id).then(res => {
        this.dictTypeDialogVisible = true
        this.dictType = res.data
      })
    },
    // 添加数据字段类型
    appendDictType(data) {
      this.dictType.parentId = data.id
      this.dictType.dictTypeName = '子节点'
      this.dictType.dictTypeCode = data.dictTypeCode + '_'
      this.dictType.orderNum = undefined
      this.dictType.description = undefined
      addSysDictType(this.dictType).then(res => {
        if (res) {
          // 更新类型树
          this.getDictTypeTrees()
        }
      })
    },
    // 删除数据字段类型
    removeDictType(node, data) {
      this.$confirm('此操作将数据删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 判断当前节点是否存在子节点
        if (data.children && data.children.length === 0) {
          delSysDictType(data.id).then(() => {
            const parent = node.parent
            const children = parent.data.children || parent.data
            // 获取当前节点index
            const index = children.findIndex(d => d.id === data.id)
            // 删除指定节点
            children.splice(index, 1)
          })
        } else {
          this.$notify({
            title: '失败',
            message: '请删除当前节点的子节点',
            type: 'warning',
            duration: 2000
          })
        }
      })
    },
    // 获取字典类型对应的值列表
    getDictValueList(dictTypeId) {
      sysDictValueList({ typeId: dictTypeId }).then(res => {
        if (res) {
          this.dictValueList = res.data.rows
        }
      })
    },
    // 打开字典类型对应的字典列表数据
    handleDictValueList(dictType) {
      this.dictTypeId = dictType.id
      // 字典code由字典类型code作为前缀
      this.tempDictTypeCode = dictType.dictTypeCode + '_'
      this.getDictValueList(dictType.id)
    },
    // 树编辑按钮
    renderContent(h, { node, data, store }) {
      return (
        <span class="custom-tree-node">
          <span
            on-click={() => this.handleDictValueList(data)}
            class="custom-tree-node-title"
          >
            {node.label}
          </span>
          <span>
            <el-button
              size="mini"
              type="text"
              on-click={() => this.appendDictType(data)}
            >
              <i class="el-icon-circle-plus-outline" />
            </el-button>
            <el-button
              size="mini"
              type="text"
              on-click={() => this.editDictType(data)}
            >
              <i class="el-icon-edit" />
            </el-button>
            <el-button
              size="mini"
              type="text"
              on-click={() => this.removeDictType(node, data)}
            >
              <i class="el-icon-delete" />
            </el-button>
          </span>
        </span>
      )
    },
    // 获取详情，并打开更新表单弹窗
    update() {
      this.$confirm('此操作将数据更新, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dictTypeDialogVisible = false
        putSysDictType(this.dictType.id, this.dictType).then(res => {
          if (res) {
            this.dictTypeDialogVisible = false
            this.getDictTypeTrees()
            this.$notify({
              title: '成功',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
          }
        })
      })
    },
    // 获取字典类型树
    getDictTypeTrees() {
      getDictTypeTree().then(response => {
        this.dictTree = response.data
      })
    },
    // 重置表单字段
    resetDictValue() {
      this.dictValue.dictTitle = undefined
      this.dictValue.dictCode = undefined
      this.dictValue.orderNum = undefined
      this.dictValue.description = undefined
    },
    // 打开添加字典值弹窗
    handleAddDictValue() {
      this.resetDictValue()
      this.dictValueDialogVisible = true
      this.dialogStatus = 'create'
      this.dictValue.typeId = this.dictTypeId
      // 临时字典类型设置给字典
      this.dictValue.dictCode = this.tempDictTypeCode
      this.dictValue.orderNum = 1
    },
    // 添加字典
    addDictValue() {
      addSysDictValue(this.dictValue).then(() => {
        this.dictValueDialogVisible = false
        this.getDictValueList(this.dictValue.typeId)
        this.$notify({
          title: '成功',
          message: '添加成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    // 打开字典编辑表单弹窗
    handleUpDictValue(row) {
      this.resetDictValue()
      this.dictValueDialogVisible = true
      this.dialogStatus = 'update'
      getSysDictValue(row.id).then(res => {
        if (res) {
          this.dictValue = res.data
        }
      })
    },
    // 更新字典值
    upDictValue() {
      this.$confirm('此操作将数据更新, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        putSysDictValue(this.dictValue.id, this.dictValue).then(res => {
          if (res) {
            this.dictValueDialogVisible = false
            this.getDictValueList(this.dictValue.typeId)
            this.$notify({
              title: '成功',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
          }
        })
      })
    },
    // 打开字典删除弹窗
    handleDelDictValue(row) {
      this.$confirm('此操作将数据删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delSysDictValue(row.id).then(res => {
          if (res) {
            this.getDictValueList(row.typeId)
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              duration: 2000
            })
          }
        })
      })
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

