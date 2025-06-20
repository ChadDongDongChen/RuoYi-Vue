<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="批次ID" prop="batchId">
        <el-input
          v-model="queryParams.batchId"
          placeholder="请输入批次ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收获日期" prop="harvestDate">
        <el-date-picker clearable
          v-model="queryParams.harvestDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择收获日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="总重量(kg)" prop="totalWeight">
        <el-input
          v-model="queryParams.totalWeight"
          placeholder="请输入总重量(kg)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="平均重量(g)" prop="avgWeight">
        <el-input
          v-model="queryParams.avgWeight"
          placeholder="请输入平均重量(g)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="存活率(%)" prop="survivalRate">
        <el-input
          v-model="queryParams.survivalRate"
          placeholder="请输入存活率(%)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作人" prop="operator">
        <el-input
          v-model="queryParams.operator"
          placeholder="请输入操作人"
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
          v-hasPermi="['creb:harvest:add']"
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
          v-hasPermi="['creb:harvest:edit']"
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
          v-hasPermi="['creb:harvest:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['creb:harvest:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="harvestList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" align="center" prop="recordId" />
      <el-table-column label="批次ID" align="center" prop="batchId" />
      <el-table-column label="收获日期" align="center" prop="harvestDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.harvestDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="总重量(kg)" align="center" prop="totalWeight" />
      <el-table-column label="平均重量(g)" align="center" prop="avgWeight" />
      <el-table-column label="存活率(%)" align="center" prop="survivalRate" />
      <el-table-column label="操作人" align="center" prop="operator" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['creb:harvest:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['creb:harvest:remove']"
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

    <!-- 添加或修改收获记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="批次ID" prop="batchId">
          <el-input v-model="form.batchId" placeholder="请输入批次ID" />
        </el-form-item>
        <el-form-item label="收获日期" prop="harvestDate">
          <el-date-picker clearable
            v-model="form.harvestDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择收获日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="总重量(kg)" prop="totalWeight">
          <el-input v-model="form.totalWeight" placeholder="请输入总重量(kg)" />
        </el-form-item>
        <el-form-item label="平均重量(g)" prop="avgWeight">
          <el-input v-model="form.avgWeight" placeholder="请输入平均重量(g)" />
        </el-form-item>
        <el-form-item label="存活率(%)" prop="survivalRate">
          <el-input v-model="form.survivalRate" placeholder="请输入存活率(%)" />
        </el-form-item>
        <el-form-item label="操作人" prop="operator">
          <el-input v-model="form.operator" placeholder="请输入操作人" />
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
import { listHarvest, getHarvest, delHarvest, addHarvest, updateHarvest } from "@/api/creb/harvest"

export default {
  name: "Harvest",
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
      // 收获记录表格数据
      harvestList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        batchId: null,
        harvestDate: null,
        totalWeight: null,
        avgWeight: null,
        survivalRate: null,
        operator: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        batchId: [
          { required: true, message: "批次ID不能为空", trigger: "blur" }
        ],
        harvestDate: [
          { required: true, message: "收获日期不能为空", trigger: "blur" }
        ],
        totalWeight: [
          { required: true, message: "总重量(kg)不能为空", trigger: "blur" }
        ],
        avgWeight: [
          { required: true, message: "平均重量(g)不能为空", trigger: "blur" }
        ],
        survivalRate: [
          { required: true, message: "存活率(%)不能为空", trigger: "blur" }
        ],
        operator: [
          { required: true, message: "操作人不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询收获记录列表 */
    getList() {
      this.loading = true
      listHarvest(this.queryParams).then(response => {
        this.harvestList = response.rows
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
        recordId: null,
        batchId: null,
        harvestDate: null,
        totalWeight: null,
        avgWeight: null,
        survivalRate: null,
        operator: null,
        createTime: null,
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
      this.ids = selection.map(item => item.recordId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加收获记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const recordId = row.recordId || this.ids
      getHarvest(recordId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改收获记录"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            updateHarvest(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addHarvest(this.form).then(response => {
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
      const recordIds = row.recordId || this.ids
      this.$modal.confirm('是否确认删除收获记录编号为"' + recordIds + '"的数据项？').then(function() {
        return delHarvest(recordIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('creb/harvest/export', {
        ...this.queryParams
      }, `harvest_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
