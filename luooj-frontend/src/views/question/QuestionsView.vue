<template>
  <div id="questions">
    <h1>浏览题目</h1>
    <a-form :model="searchParams" layout="inline">
      <a-form-item field="title" label="题目名称" style="min-width: 360px">
        <a-input v-model="searchParams.title" placeholder="请输入名称" />
      </a-form-item>
      <a-form-item field="tags" label="题目标签" style="min-width: 360px">
        <a-input-tag v-model="searchParams.tags" placeholder="请输入标签" />
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
      <template #tags="{ record }">
        <a-space wrap>
          <a-tag
            v-for="(tag, index) of record.tags"
            :key="index"
            color="green"
            bordered
          >
            {{ tag }}
          </a-tag>
        </a-space>
      </template>
      <template #acceptedRate="{ record }">
        {{
          `${
            record.submitNum
              ? ((record.acceptedNum / record.submitNum) * 100).toFixed(3)
              : "0.000"
          }%
          (${record.acceptedNum} / ${record.submitNum})`
        }}
      </template>
      <template #optional="{ record }">
        <a-button type="primary" @click="toQuestionPage(record)">
          去做题
        </a-button>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watchEffect } from "vue";
import { Question, QuestionControllerService } from "@/api";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";

const columns = [
  {
    title: "题号",
    dataIndex: "id",
  },
  {
    title: "题目名称",
    dataIndex: "title",
  },
  {
    title: "标签",
    slotName: "tags",
  },
  {
    title: "通过率（通过数/提交数）",
    slotName: "acceptedRate",
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
  },
  {
    title: "操作",
    slotName: "optional",
  },
];

const dataList = ref([]);
const total = ref(0);
const searchParams = ref({
  title: "",
  tags: [],
  pageSize: 10,
  current: 1,
});

const router = useRouter();

onMounted(() => {
  loadData();
});

const loadData = async () => {
  const res = await QuestionControllerService.listQuestionVoByPageUsingPost(
    searchParams.value
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

/**
 * 去做题
 * @param question
 */
const toQuestionPage = (question: Question) => {
  router.push({
    path: `/view/question/${question.id}`,
  });
};
</script>

<style scoped>
#questions {
  max-width: 1280px;
  margin: 0 auto;
}
</style>
