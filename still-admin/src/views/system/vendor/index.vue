<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关联sys_user表的账号ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入关联sys_user表的账号ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="摊主真实姓名" prop="vendorName">
        <el-input
          v-model="queryParams.vendorName"
          placeholder="请输入摊主真实姓名"
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
      <el-form-item label="主营品类(如: 烧烤, 水果)" prop="goodsCategory">
        <el-input
          v-model="queryParams.goodsCategory"
          placeholder="请输入主营品类(如: 烧烤, 水果)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="健康证图片地址" prop="healthCertUrl">
        <el-input
          v-model="queryParams.healthCertUrl"
          placeholder="请输入健康证图片地址"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="信用积分(满分100)" prop="creditScore">
        <el-input
          v-model="queryParams.creditScore"
          placeholder="请输入信用积分(满分100)"
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
          v-hasPermi="['system:vendor:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:vendor:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:vendor:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:vendor:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="vendorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="摊贩ID" align="center" prop="vendorId" />
      <el-table-column label="关联sys_user表的账号ID" align="center" prop="userId" />
      <el-table-column label="摊主真实姓名" align="center" prop="vendorName" />
      <el-table-column label="联系电话" align="center" prop="phone" />
      <el-table-column label="主营品类(如: 烧烤, 水果)" align="center" prop="goodsCategory" />
      <el-table-column label="健康证图片地址" align="center" prop="healthCertUrl" />
      <el-table-column label="信用积分(满分100)" align="center" prop="creditScore" />
      <el-table-column label="审核状态" align="center" prop="status" />
      <el-table-column label="备注(可存拒审原因)" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:vendor:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:vendor:remove']">删除</el-button>
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

    <!-- 添加或修改摊贩档案对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="vendorRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="关联sys_user表的账号ID" prop="userId">
              <el-input v-model="form.userId" placeholder="请输入关联sys_user表的账号ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="摊主真实姓名" prop="vendorName">
              <el-input v-model="form.vendorName" placeholder="请输入摊主真实姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="主营品类(如: 烧烤, 水果)" prop="goodsCategory">
              <el-input v-model="form.goodsCategory" placeholder="请输入主营品类(如: 烧烤, 水果)" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="健康证图片地址" prop="healthCertUrl">
              <el-input v-model="form.healthCertUrl" placeholder="请输入健康证图片地址" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="信用积分(满分100)" prop="creditScore">
              <el-input v-model="form.creditScore" placeholder="请输入信用积分(满分100)" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="删除标志" prop="delFlag">
              <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注(可存拒审原因)" prop="remark">
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

<script setup name="Vendor">
import { listVendor, getVendor, delVendor, addVendor, updateVendor } from "@/api/system/vendor"

const { proxy } = getCurrentInstance()

const vendorList = ref([])
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
    vendorName: undefined,
    phone: undefined,
    goodsCategory: undefined,
    healthCertUrl: undefined,
    creditScore: undefined,
    status: undefined,
  },
  rules: {
    userId: [
      { required: true, message: "关联sys_user表的账号ID不能为空", trigger: "blur" }
    ],
    vendorName: [
      { required: true, message: "摊主真实姓名不能为空", trigger: "blur" }
    ],
    phone: [
      { required: true, message: "联系电话不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询摊贩档案列表 */
function getList() {
  loading.value = true
  listVendor(queryParams.value).then(response => {
    vendorList.value = response.rows
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
    vendorId: null,
    userId: null,
    vendorName: null,
    phone: null,
    goodsCategory: null,
    healthCertUrl: null,
    creditScore: null,
    status: null,
    delFlag: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  }
  proxy.resetForm("vendorRef")
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
  ids.value = selection.map(item => item.vendorId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加摊贩档案"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _vendorId = row.vendorId || ids.value
  getVendor(_vendorId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改摊贩档案"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["vendorRef"].validate(valid => {
    if (valid) {
      if (form.value.vendorId != null) {
        updateVendor(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addVendor(form.value).then(() => {
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
  const _vendorIds = row.vendorId || ids.value
  proxy.$modal.confirm('是否确认删除摊贩档案编号为"' + _vendorIds + '"的数据项？').then(function() {
    return delVendor(_vendorIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/vendor/export', {
    ...queryParams.value
  }, `vendor_${new Date().getTime()}.xlsx`)
}

getList()
</script>
