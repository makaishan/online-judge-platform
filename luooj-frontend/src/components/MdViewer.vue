<template>
  <div id="mdEditor">
    <Viewer :value="value" :plugins="plugins" />
  </div>
</template>

<script setup lang="ts">
import { Viewer } from "@bytemd/vue-next";
import { withDefaults, defineProps } from "vue";
import gfm from "@bytemd/plugin-gfm";
import highlight from "@bytemd/plugin-highlight";

/**
 * 通过父组件传递属性
 */
interface Props {
  value: string;
}

/**
 * 属性默认值
 */
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const props = withDefaults(defineProps<Props>(), {
  value: () => "",
});

const plugins = [
  gfm(),
  highlight(),
  // Add more plugins here
];
</script>

<style scoped></style>
