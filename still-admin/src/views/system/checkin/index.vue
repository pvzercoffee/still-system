<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="摊贩ID" prop="vendorId">
        <el-input
          v-model="queryParams.vendorId"
          placeholder="请输入摊贩ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所在围栏ID" prop="fenceId">
        <el-input
          v-model="queryParams.fenceId"
          placeholder="请输入所在围栏ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="打卡纬度" prop="latitude">
        <el-input
          v-model="queryParams.latitude"
          placeholder="请输入打卡纬度"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="打卡经度" prop="longitude">
        <el-input
          v-model="queryParams.longitude"
          placeholder="请输入打卡经度"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收摊时间" prop="checkoutTime">
        <el-date-picker clearable
          v-model="queryParams.checkoutTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择收摊时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['system:checkin:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:checkin:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:checkin:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:checkin:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkinList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="打卡ID" align="center" prop="checkinId" />
      <el-table-column label="摊贩ID" align="center" prop="vendorId" />
      <el-table-column label="所在围栏ID" align="center" prop="fenceId" />
      <el-table-column label="打卡纬度" align="center" prop="latitude" />
      <el-table-column label="打卡经度" align="center" prop="longitude" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="收摊时间" align="center" prop="checkoutTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.checkoutTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:checkin:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:checkin:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改每日出摊打卡对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="checkinRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="摊贩ID" prop="vendorId">
              <el-input v-model="form.vendorId" placeholder="请输入摊贩ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所在围栏ID" prop="fenceId">
              <el-input v-model="form.fenceId" placeholder="请输入所在围栏ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="打卡纬度" prop="latitude">
              <el-input v-model="form.latitude" placeholder="请输入打卡纬度" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="打卡经度" prop="longitude">
              <el-input v-model="form.longitude" placeholder="请输入打卡经度" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="收摊时间" prop="checkoutTime">
              <el-date-picker clearable
                v-model="form.checkoutTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择收摊时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Checkin">
import { listCheckin, getCheckin, delCheckin, addCheckin, updateCheckin } from "@/api/system/checkin"

const { proxy } = getCurrentInstance()

const checkinList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    vendorId: undefined,
    fenceId: undefined,
    latitude: undefined,
    longitude: undefined,
    status: undefined,
    checkoutTime: undefined
  },
  rules: {
    vendorId: [
      { required: true, message: "摊贩ID不能为空", trigger: "blur" }
    ],
    latitude: [
      { required: true, message: "打卡纬度不能为空", trigger: "blur" }
    ],
    longitude: [
      { required: true, message: "打卡经度不能为空", trigger: "blur" }
    ],
    createTime: [
      { required: true, message: "打卡出摊时间不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询每日出摊打卡列表 */
function getList() {
  loading.value = true
  listCheckin(queryParams.value).then(response => {
    checkinList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 取消按钮 */
function cancel() {
  open.value = false
  reset()
}

/** 表单重置 */
function reset() {
  form.value = {
    checkinId: null,
    vendorId: null,
    fenceId: null,
    latitude: null,
    longitude: null,
    status: null,
    createTime: null,
    checkoutTime: null
  }
  proxy.resetForm("checkinRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.checkinId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加每日出摊打卡"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _checkinId = row.checkinId || ids.value
  getCheckin(_checkinId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改每日出摊打卡"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["checkinRef"].validate(valid => {
    if (valid) {
      if (form.value.checkinId != null) {
        updateCheckin(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCheckin(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _checkinIds = row.checkinId || ids.value
  proxy.$modal.confirm('是否确认删除每日出摊打卡编号为"' + _checkinIds + '"的数据项？').then(function() {
    return delCheckin(_checkinIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/checkin/export', {
    ...queryParams.value
  }, `checkin_${new Date().getTime()}.xlsx`)
}

getList()
</script>
