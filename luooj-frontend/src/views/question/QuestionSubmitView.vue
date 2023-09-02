<template>
  <div id="questionSubmit">
    <h1>浏览题目提交</h1>
    <a-form :model="searchParams" layout="inline">
      <a-form-item field="title" label="题目Id" style="min-width: 360px">
        <a-input v-model="searchParams.questionId" placeholder="请输入题目Id" />
      </a-form-item>
      <a-form-item label="编程语言：">
        <a-select
          v-model="searchParams.language"
          style="width: 240px"
          placeholder="请选择编程语言"
        >
          <a-option value="">all</a-option>
          <a-option>java</a-option>
          <a-option>cpp</a-option>
          <a-option>go</a-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="doSearch">搜索题目</a-button>
      </a-form-item>
    </a-form>
    <a-divider size="0"></a-divider>
    <a-table
      :columns="columns"
      :data="dataList"
      :pagination="{
        showTotal: true,
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total: total,
        showPageSize: true,
        showJumper: true,
      }"
      @page-change="handleCurrentChange"
      @page-size-change="handlePageSizeChange"
    >
      <template #judgeInfo="{ record }">
        <div v-if="record.judgeInfo?.message">
          判题信息：{{ record.judgeInfo?.message }}
        </div>
        <div v-if="record.judgeInfo?.time">
          执行时间：{{ record.judgeInfo?.time }}ms
        </div>
        <div v-if="record.judgeInfo?.memory">
          消耗内存：{{ record.judgeInfo?.memory }}KB
        </div>
      </template>
      <template #status="{ record }">
        <span v-if="record.status === 0"> 待判题 </span>
        <span v-if="record.status === 1"> 判题中 </span>
        <span v-if="record.status === 2"> 判题成功 </span>
        <span v-if="record.status === 3"> 判题失败 </span>
      </template>
      <template #questionId="{ record }">
        {{ record.questionId }}
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watchEffect } from "vue";
import { QuestionControllerService, QuestionSubmitQueryRequest } from "@/api";
import message from "@arco-design/web-vue/es/message";

const columns = [
  {
    title: "提交编号",
    dataIndex: "id",
  },
  {
    title: "编程语言",
    dataIndex: "language",
  },
  {
    title: "判题信息",
    slotName: "judgeInfo",
  },
  {
    title: "判题状态",
    slotName: "status",
  },
  {
    title: "题目Id",
    slotName: "questionId",
  },
  {
    title: "提交人Id",
    dataIndex: "userId",
  },
  {
    title: "提交时间",
    dataIndex: "createTime",
  },
];

const dataList = ref([]);
const total = ref(0);
const searchParams = ref<QuestionSubmitQueryRequest>({
  questionId: undefined,
  language: undefined,
  pageSize: 10,
  current: 1,
});

onMounted(() => {
  loadData();
});

const loadData = async () => {
  const res = await QuestionControllerService.listQuestionSubmitByPageUsingPost(
    {
      ...searchParams.value,
      sortField: "createTime",
      sortOrder: "descend",
    }
  );
  if (res.code === 0) {
    dataList.value = res.data.records;
    total.value = res.data.total;
  } else {
    message.error("加载失败" + res.message);
  }
};
watchEffect(() => {
  loadData();
});

const handleCurrentChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page,
  };
};
const handlePageSizeChange = (pageSize: number) => {
  searchParams.value = {
    ...searchParams.value,
    pageSize: pageSize,
  };
};

const doSearch = () => {
  searchParams.value = {
    ...searchParams.value,
    current: 1,
  };
  //监听到searchParams会自动执行loadData()
  // loadData();
};
</script>

<style scoped>
#questionSubmit {
  max-width: 1280px;
  margin: 0 auto;
}
</style>
