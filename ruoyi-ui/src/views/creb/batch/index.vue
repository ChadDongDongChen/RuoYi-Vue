<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="批次名称" prop="batchName">
        <el-input
          v-model="queryParams.batchName"
          placeholder="请输入批次名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="养殖池ID" prop="poolId">
        <el-input
          v-model="queryParams.poolId"
          placeholder="请输入养殖池ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="初始数量" prop="initialCount">
        <el-input
          v-model="queryParams.initialCount"
          placeholder="请输入初始数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="初始平均重量(g)" prop="initialWeight">
        <el-input
          v-model="queryParams.initialWeight"
          placeholder="请输入初始平均重量(g)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始养殖日期" prop="startDate">
        <el-date-picker clearable
          v-model="queryParams.startDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择开始养殖日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="预计收获日期" prop="expectedDate">
        <el-date-picker clearable
          v-model="queryParams.expectedDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择预计收获日期">
        </el-date-picker>
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
          v-hasPermi="['creb:batch:add']"
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
          v-hasPermi="['creb:batch:edit']"
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
          v-hasPermi="['creb:batch:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['creb:batch:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="batchList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="批次ID" align="center" prop="batchId" />
      <el-table-column label="批次名称" align="center" prop="batchName" />
      <el-table-column label="养殖池ID" align="center" prop="poolId" />
      <el-table-column label="螃蟹品种" align="center" prop="crabType" />
      <el-table-column label="初始数量" align="center" prop="initialCount" />
      <el-table-column label="初始平均重量(g)" align="center" prop="initialWeight" />
      <el-table-column label="开始养殖日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预计收获日期" align="center" prop="expectedDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expectedDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['creb:batch:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['creb:batch:remove']"
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

    <!-- 添加或修改螃蟹批次信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="批次名称" prop="batchName">
          <el-input v-model="form.batchName" placeholder="请输入批次名称" />
        </el-form-item>
        <el-form-item label="养殖池ID" prop="poolId">
          <el-input v-model="form.poolId" placeholder="请输入养殖池ID" />
        </el-form-item>
        <el-form-item label="初始数量" prop="initialCount">
          <el-input v-model="form.initialCount" placeholder="请输入初始数量" />
        </el-form-item>
        <el-form-item label="初始平均重量(g)" prop="initialWeight">
          <el-input v-model="form.initialWeight" placeholder="请输入初始平均重量(g)" />
        </el-form-item>
        <el-form-item label="开始养殖日期" prop="startDate">
          <el-date-picker clearable
            v-model="form.startDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开始养殖日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预计收获日期" prop="expectedDate">
          <el-date-picker clearable
            v-model="form.expectedDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择预计收获日期">
          </el-date-picker>
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
import { listBatch, getBatch, delBatch, addBatch, updateBatch } from "@/api/creb/batch"

export default {
  name: "Batch",
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
      // 螃蟹批次信息表格数据
      batchList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        batchName: null,
        poolId: null,
        crabType: null,
        initialCount: null,
        initialWeight: null,
        startDate: null,
        expectedDate: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        batchName: [
          { required: true, message: "批次名称不能为空", trigger: "blur" }
        ],
        poolId: [
          { required: true, message: "养殖池ID不能为空", trigger: "blur" }
        ],
        crabType: [
          { required: true, message: "螃蟹品种不能为空", trigger: "change" }
        ],
        initialCount: [
          { required: true, message: "初始数量不能为空", trigger: "blur" }
        ],
        initialWeight: [
          { required: true, message: "初始平均重量(g)不能为空", trigger: "blur" }
        ],
        startDate: [
          { required: true, message: "开始养殖日期不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询螃蟹批次信息列表 */
    getList() {
      this.loading = true
      listBatch(this.queryParams).then(response => {
        this.batchList = response.rows
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
        batchId: null,
        batchName: null,
        poolId: null,
        crabType: null,
        initialCount: null,
        initialWeight: null,
        startDate: null,
        expectedDate: null,
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
      this.ids = selection.map(item => item.batchId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加螃蟹批次信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const batchId = row.batchId || this.ids
      getBatch(batchId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改螃蟹批次信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.batchId != null) {
            updateBatch(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addBatch(this.form).then(response => {
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
      const batchIds = row.batchId || this.ids
      this.$modal.confirm('是否确认删除螃蟹批次信息编号为"' + batchIds + '"的数据项？').then(function() {
        return delBatch(batchIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('creb/batch/export', {
        ...this.queryParams
      }, `batch_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
