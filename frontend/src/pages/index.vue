<template>
  <v-card-subtitle class="pt-4">
    Self learning project related to KHQR
  </v-card-subtitle>

  <v-card-text>
    <div>Credit: Bakong Cambodia</div>
  </v-card-text>

  <v-card-actions>
    <v-btn
      variant="outlined"
      color="orange"
      text="Generate QR"
      @click="goTo('/generate-qr')"
    ></v-btn>

    <v-btn
      variant="outlined"
      color="blue"
      text="Transaction List"
      @click="goTo('/transaction-list')"
    ></v-btn>
  </v-card-actions>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();
const goTo = (path) => {
  router.push(path);
};
</script>
