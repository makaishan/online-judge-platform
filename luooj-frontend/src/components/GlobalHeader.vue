<template>
  <a-row class="globalHeader" align="center" :wrap="false">
    <a-col flex="auto">
      <a-menu
        mode="horizontal"
        :selected-keys="selectKeys"
        @menu-item-click="doMenuClick"
      >
        <a-menu-item
          key="0"
          :style="{ padding: 0, marginRight: '38px' }"
          disabled
        >
          <div class="title-bar">
            <img class="logo" src="../assets/logo.png" alt="珞OJ" />
            <div class="title">珞 OJ</div>
          </div>
        </a-menu-item>
        <a-menu-item v-for="item in visibleRoutes" :key="item.path">
          {{ item.name }}
        </a-menu-item>
      </a-menu>
    </a-col>
    <a-col flex="100px">
      <div
        v-if="store.state.user?.loginUser?.userName != null"
        @click="toLogin"
      >
        {{ store.state.user?.loginUser?.userName }}
      </div>
      <div v-else-if="store.state.user?.loginUser != null" @click="toLogin">
        用户{{ store.state.user?.loginUser?.userAccount }}
      </div>
      <div v-else @click="toLogin">未登录</div>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
import { routes } from "@/router/routes";
import { useRouter } from "vue-router";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import { checkAccess } from "@/access/checkAccess";

const router = useRouter();
const store = useStore();

// 导航栏菜单展示
const visibleRoutes = computed(() => {
  return routes.filter((item) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    // 根据用户权限过滤菜单
    return checkAccess(store.state.user.loginUser, item.meta?.access as string);
  });
});
const selectKeys = ref(["/"]);

//路由跳转后，更新选中的菜单项
router.afterEach((to) => {
  selectKeys.value = [to.path];
});

const doMenuClick = (key: string) => {
  router.push({
    path: key,
  });
};

const toLogin = () => {
  router.push({
    path: "/user/login",
  });
};
</script>

<style scoped>
.title-bar {
  display: flex;
  align-items: center;
}

.logo {
  height: 48px;
}

.title {
  color: #439fb7;
  font-size: large;
  margin-left: 16px;
}
</style>
