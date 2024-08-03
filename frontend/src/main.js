/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from "@/plugins";

// Components
import App from "./App.vue";

// Composables
import { createApp } from "vue";
import axios from "axios";
import VueAxios from "vue-axios";

const app = createApp(App);
app.use(VueAxios, axios);
app.provide("axios", app.config.globalProperties.axios);

registerPlugins(app);

app.mount("#app");
