<template>
  <h3 class="px-2">Transaction List</h3>
  <v-infinite-scroll @load="getTransactionList" :items="items.list">
    <template v-for="item in items.list" :key="item">
      <v-card
        class="mb-1"
        rel="noopener"
        target="_blank"
        :title="item.accountName"
        @click="getInfo(item)"
        ><template v-slot:subtitle>
          Recieved amount
          <span class="text-green font-weight-bold"
            >{{ item.currency }} {{ item.amount }}</span
          >
        </template>
        <template v-slot:append>
          <v-icon
            :color="transactionStatusIcon[item.transactionStatus].color"
            :icon="transactionStatusIcon[item.transactionStatus].icon"
          ></v-icon>
        </template>
      </v-card>
    </template>
    <template v-slot:empty> </template>
  </v-infinite-scroll>
  <QrCodeDialog ref="qrCodeDialog"></QrCodeDialog>
  <TransferDialog ref="transferData"></TransferDialog>
</template>

<script setup>
import { ref, inject } from "vue";
import QrCodeDialog from "@/components/QrCodeDialog.vue";
import TransferDialog from "@/components/TransferDialog.vue";

const axios = inject("axios");
const items = ref({});
const qrCodeDialog = ref(null);
const transferData = ref(null);
const transactionStatusIcon = ref({
  SUCCESS: { icon: "mdi-checkbox-marked", color: "#4CAF50" },
  PENDING: { icon: "	mdi-information", color: "#FF9800" },
});

const getTransactionList = ({ done }) => {
  axios
    .get(
      "http://127.0.0.1:7788/api/khqr/get-list?pageSize=" +
        20 +
        "&pageNumber=0"
    )
    .then((res) => {
      items.value = res.data.data;
      done("empty");
    })
    .finally(() => {});
};

const getInfo = (item) => {
  if (item.transactionStatus === "PENDING") {
    qrCodeDialog.value.visible({
      qrCode: item.encodeQr,
      accountName: item.accountName,
      currency: item.currency,
      amount: item.amount,
    });
  } else if (item.transactionStatus === "SUCCESS") {
    transferData.value.visible(item);
  }
};

// onMounted(() => {
//   getTransactionList();
// });
</script>

<style scoped></style>
