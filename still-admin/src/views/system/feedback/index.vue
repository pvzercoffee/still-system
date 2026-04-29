<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属摊贩ID" prop="vendorId">
        <el-input
          v-model="queryParams.vendorId"
          placeholder="请输入所属摊贩ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="留言学生ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入留言学生ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分(1-5星)" prop="rating">
        <el-input
          v-model="queryParams.rating"
          placeholder="请输入评分(1-5星)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回复时间" prop="replyTime">
        <el-date-picker clearable
          v-model="queryParams.replyTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择回复时间">
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
          v-hasPermi="['system:feedback:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:feedback:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:feedback:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:feedback:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="feedbackList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="留言ID" align="center" prop="feedbackId" />
      <el-table-column label="所属摊贩ID" align="center" prop="vendorId" />
      <el-table-column label="留言学生ID" align="center" prop="userId" />
      <el-table-column label="评分(1-5星)" align="center" prop="rating" />
      <el-table-column label="留言内容" align="center" prop="content" />
      <el-table-column label="摊主回复内容" align="center" prop="replyContent" />
      <el-table-column label="回复时间" align="center" prop="replyTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.replyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:feedback:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:feedback:remove']">删除</el-button>
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

    <!-- 添加或修改留言与反馈对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="feedbackRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="所属摊贩ID" prop="vendorId">
              <el-input v-model="form.vendorId" placeholder="请输入所属摊贩ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="留言学生ID" prop="userId">
              <el-input v-model="form.userId" placeholder="请输入留言学生ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="评分(1-5星)" prop="rating">
              <el-input v-model="form.rating" placeholder="请输入评分(1-5星)" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="留言内容">
              <editor v-model="form.content" :min-height="192"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="摊主回复内容">
              <editor v-model="form.replyContent" :min-height="192"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="回复时间" prop="replyTime">
              <el-date-picker clearable
                v-model="form.replyTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择回复时间">
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

<script setup name="Feedback">
import { listFeedback, getFeedback, delFeedback, addFeedback, updateFeedback } from "@/api/system/feedback"

const { proxy } = getCurrentInstance()

const feedbackList = ref([])
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
    userId: undefined,
    rating: undefined,
    content: undefined,
    replyContent: undefined,
    replyTime: undefined,
  },
  rules: {
    vendorId: [
      { required: true, message: "所属摊贩ID不能为空", trigger: "blur" }
    ],
    userId: [
      { required: true, message: "留言学生ID不能为空", trigger: "blur" }
    ],
    rating: [
      { required: true, message: "评分(1-5星)不能为空", trigger: "blur" }
    ],
    content: [
      { required: true, message: "留言内容不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询留言与反馈列表 */
function getList() {
  loading.value = true
  listFeedback(queryParams.value).then(response => {
    feedbackList.value = response.rows
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
    feedbackId: null,
    vendorId: null,
    userId: null,
    rating: null,
    content: null,
    replyContent: null,
    replyTime: null,
    createTime: null
  }
  proxy.resetForm("feedbackRef")
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
  ids.value = selection.map(item => item.feedbackId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加留言与反馈"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _feedbackId = row.feedbackId || ids.value
  getFeedback(_feedbackId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改留言与反馈"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["feedbackRef"].validate(valid => {
    if (valid) {
      if (form.value.feedbackId != null) {
        updateFeedback(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addFeedback(form.value).then(() => {
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
  const _feedbackIds = row.feedbackId || ids.value
  proxy.$modal.confirm('是否确认删除留言与反馈编号为"' + _feedbackIds + '"的数据项？').then(function() {
    return delFeedback(_feedbackIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/feedback/export', {
    ...queryParams.value
  }, `feedback_${new Date().getTime()}.xlsx`)
}

getList()
</script>
