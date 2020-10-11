<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input
        @keyup.enter.native="handleFilter"
        style="width: 200px;"
        class="filter-item"
        placeholder="部门名称"
        v-model="listQuery.deptName"
      />
      <el-button class="filter-item" type="primary" icon="search" @click="handleFilter">搜索</el-button>
      <el-button
        v-if="menuButton.addDept"
        class="filter-item"
        style="margin-left: 10px;"
        @click="handleCreate"
        type="primary"
        icon="edit"
      >添加部门</el-button>
    </div>
    <el-table
      :data.sync="deptTree"
      v-loading="listLoading"
      row-key="id"
      border
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column align="center" label="部门名称" prop="deptName" />
      <el-table-column align="center" label="部门编码" prop="deptCode" />
      <el-table-column align="center" label="创建日期" prop="createTime" />
      <el-table-column align="center" label="最后更新时间" prop="updateTime" />
      <el-table-column fixed="right" align="center" label="操作" width="250">
        <template slot-scope="scope">
          <el-button v-if="menuButton.addDept" size="small" type="primary" @click="handleCreate(scope.row)">添加</el-button>
          <el-button v-if="menuButton.upDept" size="small" type="success" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-if="menuButton.delDept" size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog width="35%" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="deptForm" :rules="rules" :model="deptForm" label-width="100px">
        <el-form-item v-show="deptForm.parentName" label="父级部门">
          <el-input v-model="deptForm.parentName" :disabled="true" />
        </el-form-item>
        <el-form-item label="部门名称" prop="deptName">
          <el-input v-model="deptForm.deptName" placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="部门编码" prop="deptCode">
          <el-input v-model="deptForm.deptCode" placeholder="请输入部门编码" />
        </el-form-item>
        <el-form-item label="部门说明" prop="description">
          <el-input
            type="textarea"
            v-model="deptForm.description"
            :rows="5"
            maxlength="500"
            show-word-limit
            placeholder="请输入部门说明"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel()">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="create()">确 定</el-button>
        <el-button v-else type="primary" @click="update()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addBaseDept,
  delBaseDept,
  getBaseDept,
  baseDeptTree,
  putBaseDept
} from '@/api/system/base/dept'

export default {
  data() {
    return {
      deptForm: {
        id: undefined,
        parentId: undefined,
        parentName: undefined,
        deptName: undefined,
        deptCode: undefined,
        description: undefined
      },
      rules: {
        deptName: [
          {
            required: true,
            message: '请输入部门名称',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 256,
            message: '长度在256以内个字符',
            trigger: 'blur'
          }
        ],
        deptCode: [
          {
            required: true,
            message: '请输入部门编码',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 128,
            message: '长度在128以内个字符',
            trigger: 'blur'
          }
        ],
        description: [
          {
            message: '请输入部门说明',
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
      deptTree: [],
      // 等待加载
      listLoading: true,
      // 列表查询条件
      listQuery: {
        page: 1,
        limit: 20,
        deptName: undefined
      },
      // 是否弹出（创建|更新）
      dialogFormVisible: false,
      // update|create
      dialogStatus: '',
      textMap: {
        // 表单title
        update: '更新部门',
        create: '添加部门'
      },
      menuButton: {
        addDept: undefined,
        upDept: undefined,
        delDept: undefined
      }
    }
  },
  // 初始化
  created() {
    const baseDept = this.$store.state.user.menuButton.baseDept
    if (baseDept) {
      this.menuButton.addDept = baseDept.addDept
      this.menuButton.upDept = baseDept.upDept
      this.menuButton.delDept = baseDept.delDept
    }
    this.getList()
  },
  methods: {
    // 实体列表数据
    getList() {
      this.listLoading = true
      baseDeptTree(this.listQuery).then(response => {
        this.deptTree = response.data
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
      // 判断是否存在父级，存在则设置父级id
      if (row) {
        this.deptForm.parentId = row.id
        this.deptForm.parentName = row.deptName
      }
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    // 获取详情，并打开更新表单弹窗
    handleUpdate(row) {
      getBaseDept(row.id).then(response => {
        this.deptForm = response.data
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
        delBaseDept(row.id).then(res => {
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
    // 创建新实体
    create() {
      addBaseDept(this.deptForm).then(res => {
        if (res) {
          this.dialogFormVisible = false
          this.getList()
          this.$notify({
            title: '成功',
            message: '创建成功',
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
        putBaseDept(this.deptForm.id, this.deptForm).then(res => {
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
      this.deptForm.id = undefined
      this.deptForm.parentId = undefined
      this.deptForm.parentName = undefined
      this.deptForm.deptName = undefined
      this.deptForm.deptCode = undefined
      this.deptForm.description = undefined
    }
  }
}
</script>

<style scoped>
.filter-container {
  margin-bottom: 15px;
}
</style>
