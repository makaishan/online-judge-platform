<template>
  <div id="userLogin">
    <a-card hoverable class="loginCard">
      <div class="card-content">
        <div class="card-left"></div>
        <div class="card-right">
          <a-form
            :model="form"
            label-align="left"
            auto-label-width
            @submit="handleSubmit"
            class="form"
          >
            <span
              style="font-size: 20px; font-weight: bolder; margin-bottom: 20px"
              >用户登录</span
            >
            <a-form-item field="userAccount" label="账号">
              <a-input v-model="form.userAccount" placeholder="请输入账号" />
            </a-form-item>
            <a-form-item field="userPassword" label="密码">
              <a-input-password
                v-model="form.userPassword"
                placeholder="请输入密码"
              />
            </a-form-item>
            <a-form-item>
              <a-button html-type="submit" type="primary">立即登录</a-button>
            </a-form-item>
          </a-form>
        </div>
      </div>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import { UserControllerService, UserLoginRequest } from "@/api";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const router = useRouter();
const store = useStore();

const form: UserLoginRequest = reactive({
  userAccount: "",
  userPassword: "",
});
const handleSubmit = async () => {
  const res = await UserControllerService.userLoginUsingPost(form);
  if (res.code === 0) {
    message.success("登录成功");
    await store.dispatch("getLoginUser");
    router.push({
      path: "/",
      replace: true,
    });
  } else {
    message.error("登录失败，" + res.message);
  }
};
</script>

<style scoped>
#userLogin {
}

#userLogin .card-content {
  display: grid;
  grid-template-columns: auto auto;
}

#userLogin .loginCard {
  margin: 180px auto auto;
  width: 1000px;
  height: 500px;
}
#userLogin .card-content .card-left {
  background: red;
  width: 480px;
  margin: 0;
}

#userLogin .form {
  margin: 50px auto 0;
  width: 480px;
}
</style>
