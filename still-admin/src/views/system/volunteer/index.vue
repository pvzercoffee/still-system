<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关联sys_user(审核通过后生成并绑定)" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入关联sys_user(审核通过后生成并绑定)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生姓名" prop="studentName">
        <el-input
          v-model="queryParams.studentName"
          placeholder="请输入学生姓名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学号" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属学院(如: 财经学院)" prop="college">
        <el-input
          v-model="queryParams.college"
          placeholder="请输入所属学院(如: 财经学院)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入联系电话"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="累计巡查次数(用于评优)" prop="patrolCount">
        <el-input
          v-model="queryParams.patrolCount"
          placeholder="请输入累计巡查次数(用于评优)"
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
          v-hasPermi="['system:volunteer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:volunteer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:volunteer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:volunteer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="volunteerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="志愿者ID" align="center" prop="volunteerId" />
      <el-table-column label="关联sys_user(审核通过后生成并绑定)" align="center" prop="userId" />
      <el-table-column label="学生姓名" align="center" prop="studentName" />
      <el-table-column label="学号" align="center" prop="studentId" />
      <el-table-column label="所属学院(如: 财经学院)" align="center" prop="college" />
      <el-table-column label="联系电话" align="center" prop="phone" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="累计巡查次数(用于评优)" align="center" prop="patrolCount" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:volunteer:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:volunteer:remove']">删除</el-button>
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

    <!-- 添加或修改志愿者申请与档案对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="volunteerRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="关联sys_user(审核通过后生成并绑定)" prop="userId">
              <el-input v-model="form.userId" placeholder="请输入关联sys_user(审核通过后生成并绑定)" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="学生姓名" prop="studentName">
              <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="学号" prop="studentId">
              <el-input v-model="form.studentId" placeholder="请输入学号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所属学院(如: 财经学院)" prop="college">
              <el-input v-model="form.college" placeholder="请输入所属学院(如: 财经学院)" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="累计巡查次数(用于评优)" prop="patrolCount">
              <el-input v-model="form.patrolCount" placeholder="请输入累计巡查次数(用于评优)" />
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

<script setup name="Volunteer">
import { listVolunteer, getVolunteer, delVolunteer, addVolunteer, updateVolunteer } from "@/api/system/volunteer"

const { proxy } = getCurrentInstance()

const volunteerList = ref([])
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
    userId: undefined,
    studentName: undefined,
    studentId: undefined,
    college: undefined,
    phone: undefined,
    status: undefined,
    patrolCount: undefined,
  },
  rules: {
    studentName: [
      { required: true, message: "学生姓名不能为空", trigger: "blur" }
    ],
    studentId: [
      { required: true, message: "学号不能为空", trigger: "blur" }
    ],
    phone: [
      { required: true, message: "联系电话不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询志愿者申请与档案列表 */
function getList() {
  loading.value = true
  listVolunteer(queryParams.value).then(response => {
    volunteerList.value = response.rows
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
    volunteerId: null,
    userId: null,
    studentName: null,
    studentId: null,
    college: null,
    phone: null,
    status: null,
    patrolCount: null,
    createTime: null,
    remark: null
  }
  proxy.resetForm("volunteerRef")
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
  ids.value = selection.map(item => item.volunteerId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加志愿者申请与档案"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _volunteerId = row.volunteerId || ids.value
  getVolunteer(_volunteerId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改志愿者申请与档案"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["volunteerRef"].validate(valid => {
    if (valid) {
      if (form.value.volunteerId != null) {
        updateVolunteer(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addVolunteer(form.value).then(() => {
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
  const _volunteerIds = row.volunteerId || ids.value
  proxy.$modal.confirm('是否确认删除志愿者申请与档案编号为"' + _volunteerIds + '"的数据项？').then(function() {
    return delVolunteer(_volunteerIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/volunteer/export', {
    ...queryParams.value
  }, `volunteer_${new Date().getTime()}.xlsx`)
}

getList()
</script>
