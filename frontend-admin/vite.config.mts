import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path";

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src")
    }
  },
  server: {
    port: 5174,
    host: "0.0.0.0",
    proxy: {
      "/CardiyCar": {
        target: "http://localhost:8080",
        changeOrigin: true
      },
      "/CardiyRender": {
        target: "http://localhost:8080",
        changeOrigin: true
      },
      "/CardiyAdmin": {
        target: "http://localhost:8080",
        changeOrigin: true
      },
      "/CardiyOrder": {
        target: "http://localhost:8080",
        changeOrigin: true
      },
    }
  }
});


