<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="养殖池ID" prop="poolId">
        <el-input
          v-model="queryParams.poolId"
          placeholder="请输入养殖池ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备ID" prop="deviceId">
        <el-input
          v-model="queryParams.deviceId"
          placeholder="请输入设备ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预警值" prop="alertValue">
        <el-input
          v-model="queryParams.alertValue"
          placeholder="请输入预警值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预警级别" prop="alertLevel">
        <el-input
          v-model="queryParams.alertLevel"
          placeholder="请输入预警级别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预警时间" prop="alertTime">
        <el-date-picker clearable
          v-model="queryParams.alertTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择预警时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="处理时间" prop="handleTime">
        <el-date-picker clearable
          v-model="queryParams.handleTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择处理时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="处理人" prop="handleBy">
        <el-input
          v-model="queryParams.handleBy"
          placeholder="请输入处理人"
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
          v-hasPermi="['creb:alert:add']"
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
          v-hasPermi="['creb:alert:edit']"
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
          v-hasPermi="['creb:alert:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['creb:alert:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="alertList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="预警ID" align="center" prop="alertId" />
      <el-table-column label="养殖池ID" align="center" prop="poolId" />
      <el-table-column label="设备ID" align="center" prop="deviceId" />
      <el-table-column label="预警类型" align="center" prop="alertType" />
      <el-table-column label="预警值" align="center" prop="alertValue" />
      <el-table-column label="预警级别" align="center" prop="alertLevel" />
      <el-table-column label="处理状态" align="center" prop="alertStatus" />
      <el-table-column label="预警时间" align="center" prop="alertTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.alertTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理时间" align="center" prop="handleTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.handleTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理人" align="center" prop="handleBy" />
      <el-table-column label="处理结果" align="center" prop="handleResult" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['creb:alert:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['creb:alert:remove']"
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

    <!-- 添加或修改异常预警记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="养殖池ID" prop="poolId">
          <el-input v-model="form.poolId" placeholder="请输入养殖池ID" />
        </el-form-item>
        <el-form-item label="设备ID" prop="deviceId">
          <el-input v-model="form.deviceId" placeholder="请输入设备ID" />
        </el-form-item>
        <el-form-item label="预警值" prop="alertValue">
          <el-input v-model="form.alertValue" placeholder="请输入预警值" />
        </el-form-item>
        <el-form-item label="预警级别" prop="alertLevel">
          <el-input v-model="form.alertLevel" placeholder="请输入预警级别" />
        </el-form-item>
        <el-form-item label="预警时间" prop="alertTime">
          <el-date-picker clearable
            v-model="form.alertTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择预警时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="处理时间" prop="handleTime">
          <el-date-picker clearable
            v-model="form.handleTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择处理时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="处理人" prop="handleBy">
          <el-input v-model="form.handleBy" placeholder="请输入处理人" />
        </el-form-item>
        <el-form-item label="处理结果" prop="handleResult">
          <el-input v-model="form.handleResult" type="textarea" placeholder="请输入内容" />
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
import { listAlert, getAlert, delAlert, addAlert, updateAlert } from "@/api/creb/alert"

export default {
  name: "Alert",
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
      // 异常预警记录表格数据
      alertList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        poolId: null,
        deviceId: null,
        alertType: null,
        alertValue: null,
        alertLevel: null,
        alertStatus: null,
        alertTime: null,
        handleTime: null,
        handleBy: null,
        handleResult: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        poolId: [
          { required: true, message: "养殖池ID不能为空", trigger: "blur" }
        ],
        deviceId: [
          { required: true, message: "设备ID不能为空", trigger: "blur" }
        ],
        alertType: [
          { required: true, message: "预警类型不能为空", trigger: "change" }
        ],
        alertValue: [
          { required: true, message: "预警值不能为空", trigger: "blur" }
        ],
        alertTime: [
          { required: true, message: "预警时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询异常预警记录列表 */
    getList() {
      this.loading = true
      listAlert(this.queryParams).then(response => {
        this.alertList = response.rows
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
        alertId: null,
        poolId: null,
        deviceId: null,
        alertType: null,
        alertValue: null,
        alertLevel: null,
        alertStatus: null,
        alertTime: null,
        handleTime: null,
        handleBy: null,
        handleResult: null,
        createTime: null
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
      this.ids = selection.map(item => item.alertId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加异常预警记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const alertId = row.alertId || this.ids
      getAlert(alertId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改异常预警记录"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.alertId != null) {
            updateAlert(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAlert(this.form).then(response => {
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
      const alertIds = row.alertId || this.ids
      this.$modal.confirm('是否确认删除异常预警记录编号为"' + alertIds + '"的数据项？').then(function() {
        return delAlert(alertIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('creb/alert/export', {
        ...this.queryParams
      }, `alert_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
