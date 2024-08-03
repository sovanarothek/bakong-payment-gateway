<template>
  <div class="text-center pa-4">
    <v-dialog v-model="dialog" width="auto">
      <v-card
        class="pb-10"
        width="300px"
        prepend-icon="mdi-information"
        title="Transaction Data"
      >
        <v-list-item v-for="data in dataDisplay" :key="data.key">
          <template v-slot:prepend> {{ data.value }} </template>

          <template v-slot:append>
            {{ transferData[data.key] }}
          </template>
        </v-list-item>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
import { ref } from "vue";
const dialog = ref(false);
const transferData = ref({});
const dataDisplay = ref([
  {
    key: "accountName",
    value: "Account Name",
  },
  {
    key: "accountNumber",
    value: "Account Number",
  },
  {
    key: "amount",
    value: "Amount",
  },
  {
    key: "currency",
    value: "Currency",
  },
  {
    key: "createdAt",
    value: "Created At",
  },
  {
    key: "updatedAt",
    value: "Updated At",
  },
]);

const visible = (data) => {
  dialog.value = true;
  transferData.value = data;
  console.log(transferData.value);
};

defineExpose({
  visible,
});
</script>

<style scoped></style>
