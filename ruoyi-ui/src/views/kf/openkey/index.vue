<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['kf:openkey:add']">新建密钥</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList">
      <el-table-column label="ID" align="center" prop="keyId" width="70" />
      <el-table-column label="标识(api_key)" align="center" prop="apiKey" min-width="140" />
      <el-table-column label="密钥(脱敏)" align="center" prop="apiSecret" min-width="120" />
      <el-table-column label="名称" align="center" prop="name" width="120" />
      <el-table-column label="状态" align="center" prop="enabled" width="80">
        <template slot-scope="scope">{{ scope.row.enabled === '0' ? '启用' : '停用' }}</template>
      </el-table-column>
      <el-table-column label="过期时间" align="center" prop="expireTime" width="160">
        <template slot-scope="scope"><span>{{ parseTime(scope.row.expireTime) || '不限' }}</span></template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['kf:openkey:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['kf:openkey:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item v-if="!form.keyId" label="名称" prop="name">
          <el-input v-model="form.name" placeholder="便于识别" />
        </el-form-item>
        <el-form-item v-if="form.keyId" label="名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item v-if="form.keyId" label="状态" prop="enabled">
          <el-radio-group v-model="form.enabled">
            <el-radio label="0">启用</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="form.keyId" label="过期时间" prop="expireTime">
          <el-date-picker v-model="form.expireTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="留空表示不过期" style="width:100%" clearable />
        </el-form-item>
        <el-form-item v-if="form.keyId" label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOpenkey, addOpenkey, updateOpenkey, delOpenkey, getOpenkey } from '@/api/kf/openkey'

export default {
  name: 'KfOpenkey',
  data() {
    return {
      loading: true,
      total: 0,
      dataList: [],
      open: false,
      title: '',
      queryParams: { pageNum: 1, pageSize: 10 },
      form: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listOpenkey(this.queryParams).then(res => {
        this.dataList = res.rows
        this.total = res.total
        this.loading = false
      })
    },
    handleAdd() {
      this.form = { name: '', remark: '' }
      this.title = '新建开放API密钥'
      this.open = true
    },
    handleUpdate(row) {
      getOpenkey(row.keyId).then(res => {
        this.form = { ...res.data }
        this.title = '修改密钥'
        this.open = true
      })
    },
    submitForm() {
      if (this.form.keyId) {
        updateOpenkey(this.form).then(() => {
          this.$modal.msgSuccess('保存成功')
          this.open = false
          this.getList()
        })
      } else {
        addOpenkey(this.form).then(res => {
          const plain = res.plainSecret || res.data?.plainSecret
          this.$alert(
            plain ? `请立即保存密钥（仅显示一次）：\n${plain}\n\n请求头：Authorization: Bearer ${plain}` : '已创建',
            '密钥已生成',
            { confirmButtonText: '确定' }
          )
          this.open = false
          this.getList()
        })
      }
    },
    handleDelete(row) {
      this.$modal.confirm('确认删除该密钥？').then(() => delOpenkey(row.keyId)).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    }
  }
}
</script>
