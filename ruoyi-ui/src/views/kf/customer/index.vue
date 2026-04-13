<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="120px">
      <el-form-item label="external_userid" prop="externalUserid">
        <el-input v-model="queryParams.externalUserid" placeholder="模糊搜索" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="open_kfid" prop="openKfid">
        <el-input v-model="queryParams.openKfid" placeholder="客服账号ID" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="dataList">
      <el-table-column label="ID" align="center" prop="customerId" width="80" />
      <el-table-column label="external_userid" align="center" prop="externalUserid" min-width="160" show-overflow-tooltip />
      <el-table-column label="open_kfid" align="center" prop="openKfid" min-width="160" show-overflow-tooltip />
      <el-table-column label="昵称" align="center" prop="nickname" width="120" />
      <el-table-column label="首次联系" align="center" prop="firstContactTime" width="160">
        <template slot-scope="scope"><span>{{ parseTime(scope.row.firstContactTime) }}</span></template>
      </el-table-column>
      <el-table-column label="最近活跃" align="center" prop="lastActiveTime" width="160">
        <template slot-scope="scope"><span>{{ parseTime(scope.row.lastActiveTime) }}</span></template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listCustomer } from '@/api/kf/customer'

export default {
  name: 'KfCustomer',
  data() {
    return {
      loading: true,
      total: 0,
      dataList: [],
      queryParams: { pageNum: 1, pageSize: 10, externalUserid: null, openKfid: null }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listCustomer(this.queryParams).then(res => {
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
