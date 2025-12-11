import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173,
    host: "0.0.0.0",
    proxy: {
      "/CardiyCar": {
        target: "http://localhost:8080",
        changeOrigin: true
      },
      "/CardiyAuth": {
        target: "http://localhost:8080",
        changeOrigin: true
      },
      "/CardiyRender": {
        target: "http://localhost:8080",
        changeOrigin: true
      },
      "/CardiyOrder": {
        target: "http://localhost:8080",
        changeOrigin: true
      }
    }
  }
});


