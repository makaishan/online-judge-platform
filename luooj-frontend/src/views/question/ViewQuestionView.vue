<template>
  <div id="viewQuestion">
    <a-row :gutter="[24, 24]">
      <a-col :md="12" :xs="24">
        <a-tabs default-active-key="question">
          <a-tab-pane key="question" title="题目">
            <a-card
              v-if="question"
              style="height: 707px; overflow: auto"
              :title="question.title"
            >
              <template #extra>
                <a-space wrap>
                  <a-tag
                    v-for="(tag, index) of question.tags"
                    :key="index"
                    color="green"
                    bordered
                  >
                    {{ tag }}
                  </a-tag>
                </a-space>
              </template>
              <a-descriptions
                title="判题条件"
                :column="{ xs: 1, md: 2, lg: 3 }"
              >
                <a-descriptions-item label="时间限制">
                  {{ question.judgeConfig.timeLimit }}
                </a-descriptions-item>
                <a-descriptions-item label="内存限制">
                  {{ question.judgeConfig.memoryLimit }}
                </a-descriptions-item>
                <a-descriptions-item label="堆栈限制">
                  {{ question.judgeConfig.stackLimit }}
                </a-descriptions-item>
              </a-descriptions>
              <MdViewer :value="question.content || ''" />
            </a-card>
          </a-tab-pane>
          <a-tab-pane key="comment" title="评论">
            <a-card style="height: 707px; overflow: auto">
              评论功能暂未开放，敬请期待
            </a-card>
          </a-tab-pane>
          <a-tab-pane key="answer" title="答案">
            <a-card style="height: 707px; overflow: auto">
              答案功能暂未开放，敬请期待
            </a-card>
          </a-tab-pane>
        </a-tabs>
      </a-col>
      <a-col :md="12" :xs="24">
        <a-form :model="form" layout="inline">
          <a-form-item label="编程语言：">
            <a-select
              v-model="form.language"
              style="width: 240px"
              placeholder="请选择编程语言"
            >
              <a-option>java</a-option>
              <a-option>cpp</a-option>
              <a-option>go</a-option>
            </a-select>
          </a-form-item>
        </a-form>
        <CodeEditor
          :value="form.code as string"
          :language="form.language"
          :handle-change="changeCode"
          style="min-height: 660px"
        />
        <a-card style="text-align: right">
          <a-button type="primary" @click="doSubmit" style="min-width: 100px">
            提交代码
          </a-button>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { ref, withDefaults, defineProps, onMounted } from "vue";
import {
  QuestionControllerService,
  QuestionSubmitAddRequest,
  QuestionVO,
} from "@/api";
import message from "@arco-design/web-vue/es/message";
import CodeEditor from "@/components/CodeEditor.vue";
import MdViewer from "@/components/MdViewer.vue";

interface Props {
  id: string;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => "",
});

const question = ref<QuestionVO>();

const loadData = async () => {
  const res = await QuestionControllerService.getQuestionVoByIdUsingGet(
    props.id as any
  );
  if (res.code === 0) {
    question.value = res.data;
  } else {
    message.error("加载失败" + res.message);
  }
};

onMounted(() => {
  loadData();
});

const form = ref<QuestionSubmitAddRequest>({
  language: "java",
  code: "",
});

const changeCode = (value: string) => {
  form.value.code = value;
};

// 提交代码
const doSubmit = async () => {
  if (!question.value?.id) {
    return;
  }
  const res = await QuestionControllerService.doQuestionSubmitUsingPost({
    questionId: question.value?.id,
    ...form.value,
  });
  if (res.code === 0) {
    message.success("提交成功");
  } else {
    message.error("提交失败" + res.message);
  }
};
</script>

<style>
#viewQuestion {
  max-width: 1400px;
  margin: 0 auto;
}

#viewQuestion .arco-space-horizontal .arco-space-item {
  margin-bottom: 0 !important;
}
</style>
