<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="违规摊贩ID" prop="vendorId">
        <el-input
          v-model="queryParams.vendorId"
          placeholder="请输入违规摊贩ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="志愿者ID" prop="inspectorId">
        <el-input
          v-model="queryParams.inspectorId"
          placeholder="请输入志愿者ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="扣除积分值" prop="deductScore">
        <el-input
          v-model="queryParams.deductScore"
          placeholder="请输入扣除积分值"
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
          v-hasPermi="['system:violation:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:violation:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:violation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:violation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="violationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" align="center" prop="violationId" />
      <el-table-column label="违规摊贩ID" align="center" prop="vendorId" />
      <el-table-column label="志愿者ID" align="center" prop="inspectorId" />
      <el-table-column label="违规类型" align="center" prop="violationType" />
      <el-table-column label="现场照片" align="center" prop="photoUrl" />
      <el-table-column label="扣除积分值" align="center" prop="deductScore" />
      <el-table-column label="处理状态" align="center" prop="status" />
      <el-table-column label="违规详细描述" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:violation:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:violation:remove']">删除</el-button>
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

    <!-- 添加或修改巡查与违规记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="violationRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="违规摊贩ID" prop="vendorId">
              <el-input v-model="form.vendorId" placeholder="请输入违规摊贩ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="志愿者ID" prop="inspectorId">
              <el-input v-model="form.inspectorId" placeholder="请输入志愿者ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="现场照片" prop="photoUrl">
              <el-input v-model="form.photoUrl" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="扣除积分值" prop="deductScore">
              <el-input v-model="form.deductScore" placeholder="请输入扣除积分值" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="违规详细描述" prop="remark">
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

<script setup name="Violation">
import { listViolation, getViolation, delViolation, addViolation, updateViolation } from "@/api/system/violation"

const { proxy } = getCurrentInstance()

const violationList = ref([])
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
    inspectorId: undefined,
    violationType: undefined,
    photoUrl: undefined,
    deductScore: undefined,
    status: undefined,
  },
  rules: {
    vendorId: [
      { required: true, message: "违规摊贩ID不能为空", trigger: "blur" }
    ],
    inspectorId: [
      { required: true, message: "志愿者ID不能为空", trigger: "blur" }
    ],
    violationType: [
      { required: true, message: "违规类型不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询巡查与违规记录列表 */
function getList() {
  loading.value = true
  listViolation(queryParams.value).then(response => {
    violationList.value = response.rows
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
    violationId: null,
    vendorId: null,
    inspectorId: null,
    violationType: null,
    photoUrl: null,
    deductScore: null,
    status: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  }
  proxy.resetForm("violationRef")
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
  ids.value = selection.map(item => item.violationId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加巡查与违规记录"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _violationId = row.violationId || ids.value
  getViolation(_violationId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改巡查与违规记录"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["violationRef"].validate(valid => {
    if (valid) {
      if (form.value.violationId != null) {
        updateViolation(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addViolation(form.value).then(() => {
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
  const _violationIds = row.violationId || ids.value
  proxy.$modal.confirm('是否确认删除巡查与违规记录编号为"' + _violationIds + '"的数据项？').then(function() {
    return delViolation(_violationIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/violation/export', {
    ...queryParams.value
  }, `violation_${new Date().getTime()}.xlsx`)
}

getList()
</script>
