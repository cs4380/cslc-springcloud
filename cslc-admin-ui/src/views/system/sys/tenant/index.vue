<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input
        @keyup.enter.native="handleFilter"
        style="width: 200px;"
        class="filter-item"
        placeholder="租户名称"
        v-model="listQuery.tenantName"
      />
      <el-button class="filter-item" type="primary" icon="search" @click="handleFilter">搜索</el-button>
      <el-button
        v-if="menuButton.addTenant"
        class="filter-item"
        style="margin-left: 10px;"
        @click="handleCreate"
        type="primary"
        icon="edit"
      >添加租户</el-button>
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
      <el-table-column align="center" label="租户编码">
        <template slot-scope="scope">
          <span>{{scope.row.tenantCode}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="租户名称">
        <template slot-scope="scope">
          <span>{{scope.row.tenantName}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="租户说明">
        <template slot-scope="scope">
          <span>{{scope.row.description}}</span>
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
      <el-table-column fixed="right" align="center" label="操作" width="150">
        <template slot-scope="scope">
          <el-button
            v-if="menuButton.upTenant"
            size="mini"
            type="success"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-if="menuButton.delTenant"
            size="mini"
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
    <el-dialog width="35%" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="formTenat" :rules="rules" :model="formTenat" label-width="100px">
        <el-form-item label="租户编码" prop="tenantCode">
          <el-input v-model="formTenat.tenantCode" placeholder="请输入租户编码" maxlength="128" />
        </el-form-item>
        <el-form-item label="租户名称" prop="tenantName">
          <el-input v-model="formTenat.tenantName" placeholder="请输入租户名称" maxlength="256" />
        </el-form-item>
        <el-form-item label="租户说明" prop="description">
          <el-input
            v-model="formTenat.description"
            type="textarea"
            rows="4"
            placeholder="请输入租户说明"
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
  </div>
</template>

<script>
import {
  addSysTenant,
  delSysTenant,
  getSysTenant,
  page,
  putSysTenant
} from '@/api/system/sys/tenant'

export default {
  data() {
    return {
      // 对象表单
      formTenat: {
        tenantCode: undefined,
        tenantName: undefined,
        description: undefined
      },
      rules: {
        tenantCode: [
          {
            required: true,
            message: '请输入租户编码',
            trigger: 'blur'
          },
          {
            max: 128,
            message: '请输入128以内个字符',
            trigger: 'blur'
          }
        ],
        tenantName: [
          {
            required: true,
            message: '请输入租户名称',
            trigger: 'blur'
          },
          {
            max: 256,
            message: '请输入256以内个字符',
            trigger: 'blur'
          }
        ],
        description: [
          {
            message: '请输入租户说明',
            trigger: 'blur'
          },
          {
            max: 500,
            message: '请输入500以内个字符',
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
        tenantName: undefined
      },
      // 是否弹出（添加|更新）
      dialogFormVisible: false,
      // update|create
      dialogStatus: '',
      // 表单title
      textMap: {
        update: '更新租户',
        create: '添加租户'
      },
      showPagination: false,
      menuButton: {
        addTenant: undefined,
        delTenant: undefined,
        upTenant: undefined
      }
    }
  },
  // 初始化
  created() {
    const sysTenant = this.$store.state.user.menuButton.sysTenant
    if (sysTenant) {
      this.menuButton.addTenant = sysTenant.addTenant
      this.menuButton.upTenant = sysTenant.upTenant
      this.menuButton.delTenant = sysTenant.delTenant
    }
    this.getList()
  },
  methods: {
    // 实体列表数据
    getList() {
      this.listLoading = true
      page(this.listQuery).then(response => {
        this.listObj = response.data.rows
        this.total = response.data.total
        this.listLoading = false
        if (this.total > 20) {
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
      getSysTenant(row.id).then(response => {
        this.formTenat = response.data
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
        delSysTenant(row.id).then(res => {
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
      addSysTenant(this.formTenat).then(res => {
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
    // 取消表单
    cancel() {
      this.dialogFormVisible = false
    },
    // 更新实体
    update() {
      this.$confirm('此操作将数据更新, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dialogFormVisible = false
        putSysTenant(this.formTenat.id, this.formTenat).then(res => {
          if (res) {
            this.dialogFormVisible = false
            this.getList()
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
    // 重置表单字段
    resetFormTemp() {
      this.formTenat.tenantCode = undefined
      this.formTenat.tenantName = undefined
      this.formTenat.description = undefined
    }
  }
}
</script>
<style scoped>
.filter-container {
  margin-bottom: 15px;
}
</style>
