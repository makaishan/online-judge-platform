<template>
  <div id="addQuestion">
    <h1 v-if="updatePage">更新题目</h1>
    <h1 v-else>创建题目</h1>
    <a-form :model="form" label-align="left">
      <a-form-item field="title" label="题目标题">
        <a-input v-model="form.title" placeholder="请输入标题" />
      </a-form-item>
      <a-form-item field="tags" label="题目标签">
        <a-input-tag v-model="form.tags" placeholder="请输入标签" allow-clear />
      </a-form-item>
      <a-form-item field="content" label="题目内容">
        <MdEditor
          :value="form.content"
          :handle-change="(v) => (form.content = v)"
        />
      </a-form-item>
      <a-form-item field="answer" label="题目答案">
        <MdEditor
          :value="form.answer"
          :handle-change="(v) => (form.answer = v)"
        />
      </a-form-item>
      <a-form-item label="判题配置" :content-flex="false" :merge-props="false">
        <a-space direction="vertical" fill style="min-width: 480px">
          <a-form-item field="judgeConfig.timeLimit" label="时间限制">
            <a-input-number
              v-model="form.judgeConfig.timeLimit"
              placeholder="请输入时间限制"
              mode="button"
              size="large"
              min="0"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.memoryLimit" label="内存限制">
            <a-input-number
              v-model="form.judgeConfig.memoryLimit"
              placeholder="请输入内存限制"
              mode="button"
              size="large"
              min="0"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.stackLimit" label="堆栈限制">
            <a-input-number
              v-model="form.judgeConfig.stackLimit"
              placeholder="请输入堆栈限制"
              mode="button"
              size="large"
              min="0"
            />
          </a-form-item>
        </a-space>
      </a-form-item>
      <a-form-item label="判题用例" :content-flex="false" :merge-props="false">
        <a-space direction="vertical" fill>
          <a-form-item
            v-for="(judgeCaseItem, index) of form.judgeCase"
            :key="index"
            no-style
          >
            <a-space direction="vertical" fill>
              <a-form-item
                :field="`form.judgeCase[${index}].value`"
                :label="`输入用例-${index}`"
                :key="index"
                style="min-width: 480px"
              >
                <a-input
                  v-model="judgeCaseItem.input"
                  placeholder="请输入测试输入用例"
                />
              </a-form-item>
              <a-form-item
                :field="`form.judgeCase[${index}].value`"
                :label="`输出用例-${index}`"
                :key="index"
                style="min-width: 480px"
              >
                <a-input
                  v-model="judgeCaseItem.output"
                  placeholder="请输入测试输入用例"
                />
              </a-form-item>
              <a-button status="danger" @click="handleDelete(index)"
                >删除
              </a-button>
            </a-space>
          </a-form-item>
          <div style="margin-top: 20px">
            <a-button type="outline" status="success" @click="handleAdd"
              >新增测试用例
            </a-button>
          </div>
        </a-space>
      </a-form-item>
      <a-form-item style="margin-top: 16px">
        <a-button type="primary" @click="doSubmit">提交</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive } from "vue";
import MdEditor from "@/components/MdEditor.vue";
import { QuestionControllerService } from "@/api";
import message from "@arco-design/web-vue/es/message";
import { useRoute } from "vue-router";

const route = useRoute();
const updatePage = route.path.includes("update");

onMounted(() => {
  loadData();
});

const id = route.query.id;
const loadData = async () => {
  if (!id) {
    return;
  }
  const res = await QuestionControllerService.getQuestionByIdUsingGet(
    id as any
  );
  if (res.code === 0) {
    console.log(res.data);
    if (res.data?.title) {
      form.title = res.data?.title;
    } else {
      form.title = "";
    }
    if (res.data?.tags) {
      form.tags = JSON.parse(res.data.tags);
    } else {
      form.tags = [];
    }
    if (res.data?.content) {
      form.content = res.data?.content;
    } else {
      form.content = "";
    }
    if (res.data?.answer) {
      form.answer = res.data?.answer;
    } else {
      form.answer = "";
    }
    if (res.data?.judgeConfig) {
      form.judgeConfig = JSON.parse(res.data.judgeConfig);
    } else {
      form.judgeConfig = {
        timeLimit: 0,
        memoryLimit: 0,
        stackLimit: 0,
      };
    }
    if (res.data?.judgeCase) {
      form.judgeCase = JSON.parse(res.data.judgeCase);
    } else {
      form.tags = [];
    }
  } else {
    message.error("加载失败" + res.message);
  }
};

const form = reactive({
  title: "",
  tags: [],
  content: "",
  answer: "",
  judgeConfig: {
    memoryLimit: 0,
    stackLimit: 0,
    timeLimit: 0,
  },
  judgeCase: [
    {
      input: "",
      output: "",
    },
  ],
});

const doSubmit = async () => {
  //区分更新还是创建
  if (updatePage) {
    // 向后端发送请求，创建题目
    const res = await QuestionControllerService.updateQuestionUsingPost({
      id: id as any,
      ...form,
    });
    if (res.code === 0) {
      message.success("更新成功");
    } else {
      message.error("更新失败" + res.message);
    }
  } else {
    // 向后端发送请求，创建题目
    const res = await QuestionControllerService.addQuestionUsingPost(form);
    if (res.code === 0) {
      message.success("创建成功");
    } else {
      message.error("创建失败" + res.message);
    }
  }
};

/**
 * 添加判题用例
 */
const handleAdd = () => {
  if (form.judgeCase) {
    form.judgeCase.push({
      input: "",
      output: "",
    });
  }
};
/**
 * 删除判题用例
 * @param index
 */
const handleDelete = (index: number) => {
  if (form.judgeCase) {
    form.judgeCase.splice(index, 1);
  }
};
</script>

<style scoped>
#addQuestion {
  max-width: 1280px;
  margin: 0 auto;
}
</style>
