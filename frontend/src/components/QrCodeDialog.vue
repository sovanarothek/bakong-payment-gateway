<template>
  <div class="text-center pa-4">
    <v-dialog v-model="dialog" width="auto">
      <v-card
      class="pb-10"
        width="300px"
        prepend-icon="mdi-information"
        :text="`It will expire in ${duration} sec`"
        title="Here is your QR"
      >
        <div class="text-center bg-white rounded-lg mx-10 overflow-hidden">
          <div class="banner rounded-t-lg">
            <v-img
              class="mx-auto"
              height="auto"
              width="100px"
              src="/src/assets/khqr.png"
              cover
            >
            </v-img>
          </div>
          <div>
            <h3>{{ qrCodeInfo.accountName }}</h3>
            <p>
              {{ qrCodeInfo.currency }} <span>{{ qrCodeInfo.amount }}</span>
            </p>
          </div>
          <canvas id="canvas"></canvas>
        </div>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
import { ref } from "vue";
import QRCode from "qrcode";


const dialog = ref(false);
const intervalTime = ref(null);
const duration = ref(60);
const qrCodeInfo = ref({});

const hideDialog = () => {
  dialog.value = false;
  if (intervalTime.value) {
    clearInterval(intervalTime.value);
  }
};

const visible = (data) => {
  qrCodeInfo.value = data;
  dialog.value = true;
  duration.value = 60;
  intervalTime.value = setInterval(() => {
    if (intervalTime.value && duration.value <= 0) {
      clearInterval(intervalTime.value);
      hideDialog();
    } else {
      duration.value--;
    }
  }, 1000);
  setTimeout(() => {
    QRCode.toCanvas(
      document.getElementById("canvas"),
      data.qrCode,
      { toSJISFunc: QRCode.toSJIS },
      function (error) {
        if (error) console.error(error);
        console.log("success!");
      }
    );
  }, 1000);
};

defineExpose({
  visible,
});
</script>

<style scoped>
.banner {
  position: relative;
  background-color: #e0252e; /* Use your custom color */
}
.banner::after {
  position: absolute;
  background-color: #e0252e;
  content: " ";
  width: 40px;
  height: 20px;
  transform: rotate(45deg);
  top: 35px;
  right: -15px;
}
</style>
