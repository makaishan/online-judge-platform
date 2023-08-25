<template>
  <div id="editorExample">
    <h1>Editor Example</h1>
    <MdEditor :value="mdValue" :handle-change="mdChange" />
    <CodeEditor :value="codeValue" :handle-change="codeChange" />
  </div>
</template>
<script setup lang="ts">
import MdEditor from "@/components/MdEditor.vue";
import { ref } from "vue";
import CodeEditor from "@/components/CodeEditor.vue";

const mdValue = ref("");
const codeValue = ref("");
const mdChange = (v: string) => {
  mdValue.value = v;
  console.log(v);
};
const codeChange = (v: string) => {
  codeValue.value = v;
  console.log(v);
};
</script>
