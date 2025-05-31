<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="养殖池名称" prop="poolName">
        <el-input
          v-model="queryParams.poolName"
          placeholder="请输入养殖池名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="养殖池面积(平方米)" prop="poolArea">
        <el-input
          v-model="queryParams.poolArea"
          placeholder="请输入养殖池面积(平方米)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="养殖池深度(米)" prop="poolDepth">
        <el-input
          v-model="queryParams.poolDepth"
          placeholder="请输入养殖池深度(米)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['creb:pool:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['creb:pool:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['creb:pool:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['creb:pool:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="poolList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="养殖池ID" align="center" prop="poolId" />
      <el-table-column label="养殖池名称" align="center" prop="poolName" />
      <el-table-column label="养殖池面积(平方米)" align="center" prop="poolArea" />
      <el-table-column label="养殖池深度(米)" align="center" prop="poolDepth" />
      <el-table-column label="养殖池类型" align="center" prop="poolType" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['creb:pool:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['creb:pool:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改螃蟹养殖池信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="养殖池名称" prop="poolName">
          <el-input v-model="form.poolName" placeholder="请输入养殖池名称" />
        </el-form-item>
        <el-form-item label="养殖池面积(平方米)" prop="poolArea">
          <el-input v-model="form.poolArea" placeholder="请输入养殖池面积(平方米)" />
        </el-form-item>
        <el-form-item label="养殖池深度(米)" prop="poolDepth">
          <el-input v-model="form.poolDepth" placeholder="请输入养殖池深度(米)" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPool, getPool, delPool, addPool, updatePool } from "@/api/creb/pool"

export default {
  name: "Pool",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 螃蟹养殖池信息表格数据
      poolList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        poolName: null,
        poolArea: null,
        poolDepth: null,
        poolType: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        poolName: [
          { required: true, message: "养殖池名称不能为空", trigger: "blur" }
        ],
        poolArea: [
          { required: true, message: "养殖池面积(平方米)不能为空", trigger: "blur" }
        ],
        poolDepth: [
          { required: true, message: "养殖池深度(米)不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询螃蟹养殖池信息列表 */
    getList() {
      this.loading = true
      listPool(this.queryParams).then(response => {
        this.poolList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        poolId: null,
        poolName: null,
        poolArea: null,
        poolDepth: null,
        poolType: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.poolId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加螃蟹养殖池信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const poolId = row.poolId || this.ids
      getPool(poolId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改螃蟹养殖池信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.poolId != null) {
            updatePool(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addPool(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const poolIds = row.poolId || this.ids
      this.$modal.confirm('是否确认删除螃蟹养殖池信息编号为"' + poolIds + '"的数据项？').then(function() {
        return delPool(poolIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('creb/pool/export', {
        ...this.queryParams
      }, `pool_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
