<template>
  <div id="userRegister">
    <a-card hoverable class="registerCard">
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
              style="font-size: 40px; font-weight: bolder; margin-bottom: 50px"
              >用户注册</span
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
            <a-form-item field="checkPassword" label="确认密码">
              <a-input-password
                v-model="form.checkPassword"
                placeholder="请确认密码"
              />
            </a-form-item>
            <a-form-item style="margin-top: 20px; margin-left: 150px">
              <a-button html-type="submit" type="primary">立即注册</a-button>
            </a-form-item>
          </a-form>
        </div>
      </div>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from "vue";
import { UserControllerService, UserRegisterRequest } from "@/api";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";

const router = useRouter();

const form: UserRegisterRequest = reactive({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
});
const handleSubmit = async () => {
  const res = await UserControllerService.userRegisterUsingPost(form);
  if (res.code === 0) {
    message.success("注册成功");
    await router.push({
      path: "/user/login",
      replace: true,
    });
  } else {
    message.error("注册失败，" + res.message);
  }
};
</script>

<style scoped>
#userRegister {
}

#userRegister .card-content {
  display: grid;
  grid-template-columns: auto auto;
}

#userRegister .registerCard {
  margin: 180px auto auto;
  width: 1000px;
  height: 500px;
}

#userRegister .card-content .card-left {
  background: url("../../assets/login-card.jpg");
  width: 470px;
  height: 470px;
  margin: 0;
}

#userRegister .form {
  margin: 50px auto 0;
  width: 480px;
}
</style>
