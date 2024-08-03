<template>
  <div>
    <div class="my-10">
      <v-sheet class="mx-auto" width="300">
        <v-select
          label="Bank"
          :items="['ABA', 'ACLEDA']"
          v-model="bank"
        ></v-select>
        <v-form fast-fail @submit.prevent>
          <v-text-field
            v-model="accountNumber"
            :rules="notEmptyRules"
            label="Bank account number"
          ></v-text-field>

          <v-text-field
            v-model="accountName"
            :rules="notEmptyRules"
            label="Bank account name"
          ></v-text-field>

          <v-text-field
            v-model="amount"
            :rules="notEmptyRules"
            label="Recieve Amount"
          ></v-text-field>

          <v-select
            label="Currency"
            :items="['USD', 'KHR']"
            v-model="currency"
          ></v-select>

          <v-btn
            :disabled="loading"
            :loading="loading"
            class="text-none"
            color="indigo-darken-3"
            size="large"
            variant="flat"
            block
            @click="generateKHQR()"
          >
            Generate
          </v-btn>
        </v-form>
      </v-sheet>
    </div>
    <QrCodeDialog ref="qrCodeDialog"></QrCodeDialog>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { inject } from "vue";
import QrCodeDialog from "@/components/QrCodeDialog.vue";
import { useRouter } from "vue-router";

const router = useRouter();
const goTo = (path) => {
  router.push(path);
};

const axios = inject("axios");

const bank = ref("ABA");
const accountNumber = ref("");
const accountName = ref("");
const amount = ref(null);
const currency = ref("USD");
const loading = ref(false);
const qrCodeDialog = ref(null);

const notEmptyRules = [
  (value) => {
    if (value) return true;

    return "Required";
  },
];

const generateKHQR = () => {
  // loading.value = true;
  // qrCodeDialog.value.visible({
  //   qrCode: "test",
  //   accountName: accountName.value,
  //   currency: currency.value,
  //   amount: amount.value,
  // });
  axios
    .post("http://127.0.0.1:7788/api/khqr/generate", {
      bankAccountNumber: accountNumber.value,
      bankAccountName: accountName.value,
      amount: parseFloat(amount.value),
      currencyCode: currency.value,
      bank: bank.value,
    })
    .then((res) => {
      console.log(res);
      qrCodeDialog.value.visible({
        qrCode: res.data.data,
        accountName: accountName.value,
        currency: currency.value,
        amount: amount.value,
      });
    })
    .finally(() => {
      loading.value = false;
    });
};
</script>
