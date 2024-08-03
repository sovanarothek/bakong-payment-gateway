/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createRouter, createWebHistory } from "vue-router/auto";
import GenerateQR from "@/pages/GenerateQR.vue";
import TransactionList from "@/pages/TransactionList.vue";
import HeaderBanner from "@/components/HeaderBanner.vue";
import index from "@/pages/index.vue";

const routes = [
  {
    path: "/",
    component: HeaderBanner,
    children: [
      {
        path: "",
        component: index,
      },
      {
        path: "generate-qr",
        component: GenerateQR,
      },
      {
        path: "transaction-list",
        component: TransactionList,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

router.onError((err, to) => {
  if (err?.message?.includes?.("Failed to fetch dynamically imported module")) {
    if (!localStorage.getItem("vuetify:dynamic-reload")) {
      console.log("Reloading page to fix dynamic import error");
      localStorage.setItem("vuetify:dynamic-reload", "true");
      location.assign(to.fullPath);
    } else {
      console.error("Dynamic import error, reloading page did not fix it", err);
    }
  } else {
    console.error(err);
  }
});

router.isReady().then(() => {
  localStorage.removeItem("vuetify:dynamic-reload");
});

export default router;
