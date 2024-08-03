<template>
  <v-card class="mx-auto" max-width="400" height="100vh">
    <div class="position-relative">
      <v-img
        class="align-end text-white"
        width="400px"
        height="auto"
        src="/src/assets/banner.png"
        cover
      >
      </v-img>
      <v-btn
        class="position-absolute top-0 ma-4"
        color="grey-darken-4"
        icon="mdi-chevron-left"
        @click="goTo('/')"
        v-show="isVisible"
      ></v-btn>
    </div>
    <routerView />
  </v-card>
</template>

<script setup>
import { ref, onUpdated, onMounted } from "vue";
import { useRouter } from "vue-router";

const isVisible = ref(false);

const router = useRouter();
const goTo = (path) => {
  router.push(path);
};

onMounted(() => {
  isVisible.value = router.currentRoute.value.path != "/";
});

onUpdated(() => {
  isVisible.value = router.currentRoute.value.path != "/";
});
</script>
