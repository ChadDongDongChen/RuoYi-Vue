<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="120px">
      <el-form-item label="external_userid" prop="externalUserid">
        <el-input v-model="queryParams.externalUserid" placeholder="精确" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="方向" prop="direction">
        <el-select v-model="queryParams.direction" placeholder="全部" clearable>
          <el-option label="客户" value="1" />
          <el-option label="企业" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="dataList">
      <el-table-column label="msgid" align="center" prop="msgid" min-width="140" show-overflow-tooltip />
      <el-table-column label="客户ID" align="center" prop="externalUserid" min-width="140" show-overflow-tooltip />
      <el-table-column label="方向" align="center" prop="direction" width="70">
        <template slot-scope="scope">{{ scope.row.direction === '1' ? '客户' : '企业' }}</template>
      </el-table-column>
      <el-table-column label="内容" align="center" prop="content" min-width="200" show-overflow-tooltip />
      <el-table-column label="时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope"><span>{{ parseTime(scope.row.createTime) }}</span></template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listMessage } from '@/api/kf/message'

export default {
  name: 'KfMessage',
  data() {
    return {
      loading: true,
      total: 0,
      dataList: [],
      queryParams: { pageNum: 1, pageSize: 10, externalUserid: null, direction: null }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listMessage(this.queryParams).then(res => {
        this.dataList = res.rows
        this.total = res.total
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    }
  }
}
</script>
