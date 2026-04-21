<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="围栏名称(如: 正门左侧50米A区)" prop="fenceName">
        <el-input
          v-model="queryParams.fenceName"
          placeholder="请输入围栏名称(如: 正门左侧50米A区)"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['system:fence:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:fence:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:fence:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:fence:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fenceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="围栏ID" align="center" prop="fenceId" />
      <el-table-column label="围栏名称(如: 正门左侧50米A区)" align="center" prop="fenceName" />
      <el-table-column label="多边形坐标集(JSON数组格式存经纬度)" align="center" prop="polygonPoints" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:fence:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:fence:remove']">删除</el-button>
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

    <!-- 添加或修改电子围栏区域对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="fenceRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="围栏名称(如: 正门左侧50米A区)" prop="fenceName">
              <el-input v-model="form.fenceName" placeholder="请输入围栏名称(如: 正门左侧50米A区)" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="多边形坐标集(JSON数组格式存经纬度)" prop="polygonPoints">
              <el-input v-model="form.polygonPoints" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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

<script setup name="Fence">
import { listFence, getFence, delFence, addFence, updateFence } from "@/api/system/fence"

const { proxy } = getCurrentInstance()

const fenceList = ref([])
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
    fenceName: undefined,
    polygonPoints: undefined,
    status: undefined,
  },
  rules: {
    fenceName: [
      { required: true, message: "围栏名称(如: 正门左侧50米A区)不能为空", trigger: "blur" }
    ],
    polygonPoints: [
      { required: true, message: "多边形坐标集(JSON数组格式存经纬度)不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询电子围栏区域列表 */
function getList() {
  loading.value = true
  listFence(queryParams.value).then(response => {
    fenceList.value = response.rows
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
    fenceId: null,
    fenceName: null,
    polygonPoints: null,
    status: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  }
  proxy.resetForm("fenceRef")
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
  ids.value = selection.map(item => item.fenceId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加电子围栏区域"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _fenceId = row.fenceId || ids.value
  getFence(_fenceId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改电子围栏区域"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["fenceRef"].validate(valid => {
    if (valid) {
      if (form.value.fenceId != null) {
        updateFence(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addFence(form.value).then(() => {
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
  const _fenceIds = row.fenceId || ids.value
  proxy.$modal.confirm('是否确认删除电子围栏区域编号为"' + _fenceIds + '"的数据项？').then(function() {
    return delFence(_fenceIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/fence/export', {
    ...queryParams.value
  }, `fence_${new Date().getTime()}.xlsx`)
}

getList()
</script>
