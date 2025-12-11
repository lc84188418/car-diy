<template>
  <div class="app-container">
    <!-- 顶部导航 -->
    <header class="app-header">
      <div class="header-content">
        <h1 class="app-title">CarDIY</h1>
        <p class="app-subtitle">自由定制，专属座驾</p>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="main-content" v-if="models.length > 0">
      <!-- 车型选择区域 -->
      <section class="model-section">
        <div class="section-header">
          <h2 class="section-title">选择车型</h2>
          <span class="section-count">{{ models.length }} 款车型</span>
        </div>
        <div class="model-carousel">
          <div
            class="model-card"
            v-for="model in models"
            :key="model.id"
            :class="{ active: selectedModelId === model.id }"
            @click="selectModel(model.id)"
          >
            <div class="model-image-wrapper">
              <img
                :src="model.mainImageUrl || 'https://via.placeholder.com/400x300'"
                :alt="`${model.brand} ${model.series}`"
                class="model-image"
                @error="handleImageError"
              />
              <div class="model-overlay"></div>
            </div>
            <div class="model-info">
              <div class="model-brand">{{ model.brand }}</div>
              <div class="model-series">{{ model.series }} {{ model.year }}</div>
              <div class="model-desc" v-if="model.description">{{ model.description }}</div>
            </div>
          </div>
        </div>
      </section>

      <!-- 配置选择区域 -->
      <section class="config-section" v-if="selectedModel">
        <div class="section-header">
          <h2 class="section-title">定制配置</h2>
        </div>

        <!-- 车漆颜色 -->
        <div class="config-group">
          <div class="config-label">
            <span class="label-text">车漆颜色</span>
            <span class="label-count">{{ selectedModel.options.paintColors?.length || 0 }} 种可选</span>
          </div>
          <div class="config-options">
            <div
              class="config-option"
              v-for="item in selectedModel.options.paintColors"
              :key="item.code"
              :class="{ active: config.paintColor === item.code }"
              @click="config.paintColor = item.code"
            >
              <div class="option-image-wrapper">
                <img
                  :src="item.imageUrl || 'https://via.placeholder.com/200x150'"
                  :alt="item.name"
                  class="option-image"
                  @error="handleImageError"
                />
                <div class="color-preview" :style="{ backgroundColor: item.previewColor }"></div>
              </div>
              <div class="option-info">
                <div class="option-name">{{ item.name }}</div>
                <div class="option-desc" v-if="item.description">{{ item.description }}</div>
              </div>
              <div class="option-check" v-if="config.paintColor === item.code">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 6L9 17l-5-5" />
                </svg>
              </div>
            </div>
          </div>
        </div>

        <!-- 轮毂样式 -->
        <div class="config-group">
          <div class="config-label">
            <span class="label-text">轮毂样式</span>
            <span class="label-count">{{ selectedModel.options.rims?.length || 0 }} 种可选</span>
          </div>
          <div class="config-options">
            <div
              class="config-option"
              v-for="item in selectedModel.options.rims"
              :key="item.code"
              :class="{ active: config.rim === item.code }"
              @click="config.rim = item.code"
            >
              <div class="option-image-wrapper">
                <img
                  :src="item.imageUrl || 'https://via.placeholder.com/200x150'"
                  :alt="item.name"
                  class="option-image"
                  @error="handleImageError"
                />
              </div>
              <div class="option-info">
                <div class="option-name">{{ item.name }}</div>
                <div class="option-desc" v-if="item.description">{{ item.description }}</div>
              </div>
              <div class="option-check" v-if="config.rim === item.code">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 6L9 17l-5-5" />
                </svg>
              </div>
            </div>
          </div>
        </div>

        <!-- 轮胎 -->
        <div class="config-group">
          <div class="config-label">
            <span class="label-text">轮胎</span>
            <span class="label-count">{{ selectedModel.options.tires?.length || 0 }} 种可选</span>
          </div>
          <div class="config-options">
            <div
              class="config-option"
              v-for="item in selectedModel.options.tires"
              :key="item.code"
              :class="{ active: config.tire === item.code }"
              @click="config.tire = item.code"
            >
              <div class="option-image-wrapper">
                <img
                  :src="item.imageUrl || 'https://via.placeholder.com/200x150'"
                  :alt="item.name"
                  class="option-image"
                  @error="handleImageError"
                />
              </div>
              <div class="option-info">
                <div class="option-name">{{ item.name }}</div>
                <div class="option-desc" v-if="item.description">{{ item.description }}</div>
              </div>
              <div class="option-check" v-if="config.tire === item.code">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 6L9 17l-5-5" />
                </svg>
              </div>
            </div>
          </div>
        </div>

        <!-- 刹车卡钳 -->
        <div class="config-group">
          <div class="config-label">
            <span class="label-text">刹车卡钳</span>
            <span class="label-count">{{ selectedModel.options.calipers?.length || 0 }} 种可选</span>
          </div>
          <div class="config-options">
            <div
              class="config-option"
              v-for="item in selectedModel.options.calipers"
              :key="item.code"
              :class="{ active: config.caliper === item.code }"
              @click="config.caliper = item.code"
            >
              <div class="option-image-wrapper">
                <img
                  :src="item.imageUrl || 'https://via.placeholder.com/200x150'"
                  :alt="item.name"
                  class="option-image"
                  @error="handleImageError"
                />
              </div>
              <div class="option-info">
                <div class="option-name">{{ item.name }}</div>
                <div class="option-desc" v-if="item.description">{{ item.description }}</div>
              </div>
              <div class="option-check" v-if="config.caliper === item.code">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 6L9 17l-5-5" />
                </svg>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 预览区域 -->
      <section class="preview-section" v-if="selectedModel">
        <div class="section-header">
          <h2 class="section-title">3D 效果预览</h2>
        </div>
        <div class="preview-container">
          <div class="preview-car" :style="{ background: previewPaintColor }">
            <!-- Three.js 渲染容器 -->
            <div ref="previewCanvasRef" class="three-canvas"></div>
          </div>
          <div class="preview-summary">
            <div class="summary-item">
              <span class="summary-label">车型</span>
              <span class="summary-value">{{ selectedModel.brand }} {{ selectedModel.series }} {{ selectedModel.year }}</span>
            </div>
            <div class="summary-item">
              <span class="summary-label">车漆</span>
              <span class="summary-value">{{ currentOptionName('paintColors', config.paintColor) }}</span>
            </div>
            <div class="summary-item">
              <span class="summary-label">轮毂</span>
              <span class="summary-value">{{ currentOptionName('rims', config.rim) }}</span>
            </div>
            <div class="summary-item">
              <span class="summary-label">轮胎</span>
              <span class="summary-value">{{ currentOptionName('tires', config.tire) }}</span>
            </div>
            <div class="summary-item">
              <span class="summary-label">卡钳</span>
              <span class="summary-value">{{ currentOptionName('calipers', config.caliper) }}</span>
            </div>
          </div>
          <button class="save-button" @click="savePlan">
            <span>保存改装方案</span>
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M19 21H5a2 2 0 01-2-2V5a2 2 0 012-2h11l5 5v11a2 2 0 01-2 2z" />
              <path d="M17 21v-8H7v8" />
              <path d="M7 3v5h8" />
            </svg>
          </button>
        </div>
      </section>
    </main>

    <!-- 加载状态 -->
    <div class="loading" v-if="loading">
      <div class="loading-spinner"></div>
      <p>加载中...</p>
    </div>

    <!-- 空状态 -->
    <div class="empty-state" v-if="!loading && models.length === 0">
      <p>暂无车型数据</p>
    </div>
  </div>
</template>

<script setup>
import { computed, onBeforeUnmount, onMounted, reactive, ref, watch } from "vue";
import { fetchModels, saveConfigPlan, previewRender } from "./services/api";
import * as THREE from "three";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";

const models = ref([]);
const selectedModelId = ref("");
const loading = ref(true);
const previewCanvasRef = ref(null);
const currentModelUrl = ref("");

const config = reactive({
  paintColor: "",
  rim: "",
  tire: "",
  caliper: ""
});

const selectedModel = computed(() =>
  models.value.find((m) => m.id === selectedModelId.value)
);

const previewPaintColor = computed(() => {
  if (!selectedModel.value) return "linear-gradient(135deg, #667eea 0%, #764ba2 100%)";
  const item = selectedModel.value.options.paintColors?.find(
    (p) => p.code === config.paintColor
  );
  return item?.previewColor || "linear-gradient(135deg, #667eea 0%, #764ba2 100%)";
});

const currentOptionName = (groupKey, code) => {
  if (!selectedModel.value) return "-";
  const list = selectedModel.value.options[groupKey] || [];
  return list.find((i) => i.code === code)?.name || "-";
};

const selectModel = (modelId) => {
  selectedModelId.value = modelId;
  const model = models.value.find((m) => m.id === modelId);
  if (model && model.options) {
    const opt = model.options;
    config.paintColor = opt.paintColors?.[0]?.code || "";
    config.rim = opt.rims?.[0]?.code || "";
    config.tire = opt.tires?.[0]?.code || "";
    config.caliper = opt.calipers?.[0]?.code || "";
  }
  updateRender();
};

const handleImageError = (e) => {
  e.target.src = "https://via.placeholder.com/400x300?text=Image+Not+Found";
};

const loadModels = async () => {
  try {
    loading.value = true;
    const data = await fetchModels();
    models.value = data;
    if (data.length > 0) {
      selectModel(data[0].id);
    }
  } catch (e) {
    console.error("加载车型失败", e);
  } finally {
    loading.value = false;
  }
};

// ---------------- 3D 渲染相关 ----------------
let renderer;
let scene;
let camera;
let controls;
let animationId;
let currentModel;

const initThree = () => {
  const container = previewCanvasRef.value;
  if (!container) {
    console.error("Three.js 容器不存在");
    return;
  }

  const { clientWidth, clientHeight } = container;
  console.log("Three.js 初始化，容器尺寸：", clientWidth, clientHeight);

  if (clientWidth === 0 || clientHeight === 0) {
    console.warn("容器尺寸为 0，延迟初始化");
    setTimeout(() => initThree(), 100);
    return;
  }

  // 如果已经初始化过，先清理
  if (renderer) {
    renderer.dispose();
    if (renderer.domElement && renderer.domElement.parentNode) {
      renderer.domElement.parentNode.removeChild(renderer.domElement);
    }
  }

  scene = new THREE.Scene();
  scene.background = null;

  camera = new THREE.PerspectiveCamera(45, clientWidth / clientHeight, 0.1, 1000);
  camera.position.set(0, 2, 5);

  renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true });
  renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));
  renderer.setSize(clientWidth, clientHeight);
  // 兼容新旧版本 Three.js
  if (renderer.outputColorSpace !== undefined) {
    renderer.outputColorSpace = THREE.SRGBColorSpace;
  } else {
    renderer.outputEncoding = THREE.sRGBEncoding;
  }
  renderer.shadowMap.enabled = true;

  container.innerHTML = "";
  container.appendChild(renderer.domElement);

  // 环境光
  const ambientLight = new THREE.AmbientLight(0xffffff, 0.6);
  scene.add(ambientLight);

  // 主光源
  const dirLight = new THREE.DirectionalLight(0xffffff, 0.8);
  dirLight.position.set(5, 10, 7.5);
  dirLight.castShadow = true;
  scene.add(dirLight);

  // 辅助光源
  const fillLight = new THREE.DirectionalLight(0xffffff, 0.3);
  fillLight.position.set(-5, 5, -5);
  scene.add(fillLight);

  // 地面
  const floor = new THREE.Mesh(
    new THREE.CircleGeometry(3, 64),
    new THREE.MeshStandardMaterial({
      color: 0x111827,
      roughness: 0.8,
      metalness: 0.2
    })
  );
  floor.rotation.x = -Math.PI / 2;
  floor.position.y = -1;
  scene.add(floor);

  // 控制器
  controls = new OrbitControls(camera, renderer.domElement);
  controls.enableDamping = true;
  controls.dampingFactor = 0.05;
  controls.enablePan = false;
  controls.minDistance = 3;
  controls.maxDistance = 10;
  controls.target.set(0, 0.5, 0);
  controls.update();

  // 窗口大小调整
  const onResize = () => {
    if (!container || !camera || !renderer) return;
    const w = container.clientWidth;
    const h = container.clientHeight;
    if (w === 0 || h === 0) return;
    camera.aspect = w / h;
    camera.updateProjectionMatrix();
    renderer.setSize(w, h);
  };

  window.addEventListener("resize", onResize);

  // 动画循环
  const animate = () => {
    animationId = requestAnimationFrame(animate);
    if (controls) controls.update();
    if (renderer && scene && camera) {
      renderer.render(scene, camera);
    }
  };
  animate();

  console.log("Three.js 初始化完成");
};

const loadModel = (url) => {
  if (!scene || !url) {
    console.warn("无法加载模型：scene 或 url 不存在", { scene: !!scene, url });
    return;
  }

  console.log("开始加载 3D 模型：", url);
  const loader = new GLTFLoader();

  // 清理旧模型
  if (currentModel) {
    scene.remove(currentModel);
    // 清理模型资源
    currentModel.traverse((child) => {
      if (child.isMesh) {
        if (child.geometry) child.geometry.dispose();
        if (child.material) {
          if (Array.isArray(child.material)) {
            child.material.forEach((mat) => mat.dispose());
          } else {
            child.material.dispose();
          }
        }
      }
    });
    currentModel = null;
  }

  loader.load(
    url,
    (gltf) => {
      console.log("3D 模型加载成功", gltf);
      currentModel = gltf.scene;
      currentModel.position.set(0, -1, 0);

      // 计算包围盒并缩放
      const box = new THREE.Box3().setFromObject(currentModel);
      const size = new THREE.Vector3();
      box.getSize(size);
      const center = new THREE.Vector3();
      box.getCenter(center);

      const maxSize = Math.max(size.x, size.y, size.z) || 1;
      const scale = 2 / maxSize;
      currentModel.scale.set(scale, scale, scale);

      // 居中模型
      currentModel.position.sub(center.multiplyScalar(scale));
      currentModel.position.y = -1;

      scene.add(currentModel);
      console.log("3D 模型已添加到场景");
    },
    (progress) => {
      // 加载进度
      if (progress.lengthComputable) {
        const percentComplete = (progress.loaded / progress.total) * 100;
        console.log("模型加载进度：", percentComplete.toFixed(2) + "%");
      }
    },
    (error) => {
      console.error("加载 3D 模型失败", error);
      // 显示错误提示
      const toast = document.createElement("div");
      toast.className = "toast error";
      toast.textContent = "3D 模型加载失败，请检查网络连接";
      document.body.appendChild(toast);
      setTimeout(() => {
        toast.classList.add("show");
        setTimeout(() => {
          toast.classList.remove("show");
          setTimeout(() => document.body.removeChild(toast), 300);
        }, 3000);
      }, 10);
    }
  );
};

const updateRender = async () => {
  if (!selectedModel.value) return;
  try {
    console.log("请求渲染服务，参数：", {
      modelId: selectedModel.value.id,
      ...config
    });
    const res = await previewRender({
      modelId: selectedModel.value.id,
      ...config
    });
    console.log("渲染服务返回：", res);
    if (res && res.modelUrl) {
      console.log("设置模型URL：", res.modelUrl);
      currentModelUrl.value = res.modelUrl;
    } else {
      console.warn("渲染服务返回数据格式异常：", res);
    }
  } catch (e) {
    console.error("请求渲染服务失败", e);
  }
};

const savePlan = async () => {
  if (!selectedModel.value) return;
  try {
    await saveConfigPlan({
      modelId: selectedModel.value.id,
      ...config
    });
    // 使用更友好的提示
    const toast = document.createElement("div");
    toast.className = "toast";
    toast.textContent = "保存成功！";
    document.body.appendChild(toast);
    setTimeout(() => {
      toast.classList.add("show");
      setTimeout(() => {
        toast.classList.remove("show");
        setTimeout(() => document.body.removeChild(toast), 300);
      }, 2000);
    }, 10);
  } catch (e) {
    console.error("保存失败", e);
    const toast = document.createElement("div");
    toast.className = "toast error";
    toast.textContent = "保存失败，请稍后再试";
    document.body.appendChild(toast);
    setTimeout(() => {
      toast.classList.add("show");
      setTimeout(() => {
        toast.classList.remove("show");
        setTimeout(() => document.body.removeChild(toast), 300);
      }, 2000);
    }, 10);
  }
};

watch(
  () => ({ ...config, modelId: selectedModelId.value }),
  () => {
    if (selectedModel.value) {
      updateRender();
    }
  },
  { deep: true }
);

watch(
  currentModelUrl,
  (url) => {
    if (url) {
      loadModel(url);
    }
  }
);

onMounted(() => {
  loadModels();
  // 使用 nextTick 确保 DOM 完全渲染后再初始化 Three.js
  setTimeout(() => {
    if (previewCanvasRef.value) {
      initThree();
    } else {
      console.warn("预览容器未找到，延迟初始化");
      setTimeout(() => {
        if (previewCanvasRef.value) {
          initThree();
        }
      }, 200);
    }
  }, 100);
});

onBeforeUnmount(() => {
  if (animationId) {
    cancelAnimationFrame(animationId);
  }
  if (renderer) {
    renderer.dispose();
  }
  if (scene) {
    scene.clear();
  }
});
</script>

<style scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

.app-container {
  min-height: 100vh;
  background: linear-gradient(180deg, #0a0e27 0%, #1a1f3a 100%);
  color: #ffffff;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", sans-serif;
  padding-bottom: 100px;
}

/* 顶部导航 */
.app-header {
  position: sticky;
  top: 0;
  z-index: 100;
  background: rgba(10, 14, 39, 0.95);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  padding: 20px 16px;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
}

.app-title {
  font-size: 32px;
  font-weight: 700;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 4px;
}

.app-subtitle {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.6);
  font-weight: 300;
}

/* 主内容 */
.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px 16px;
}

/* 区块标题 */
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-title {
  font-size: 24px;
  font-weight: 600;
  color: #ffffff;
}

.section-count {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.5);
}

/* 车型选择区域 */
.model-section {
  margin-bottom: 40px;
}

.model-carousel {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
}

.model-card {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 20px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  border: 2px solid transparent;
  backdrop-filter: blur(10px);
}

.model-card:hover {
  transform: translateY(-8px);
  border-color: rgba(102, 126, 234, 0.5);
  box-shadow: 0 20px 40px rgba(102, 126, 234, 0.2);
}

.model-card.active {
  border-color: #667eea;
  box-shadow: 0 0 30px rgba(102, 126, 234, 0.4);
}

.model-image-wrapper {
  position: relative;
  width: 100%;
  padding-top: 60%;
  overflow: hidden;
}

.model-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.model-card:hover .model-image {
  transform: scale(1.1);
}

.model-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(to bottom, transparent 0%, rgba(0, 0, 0, 0.6) 100%);
}

.model-info {
  padding: 20px;
  position: relative;
  z-index: 1;
}

.model-brand {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 4px;
}

.model-series {
  font-size: 16px;
  color: rgba(255, 255, 255, 0.8);
  margin-bottom: 8px;
}

.model-desc {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.6);
  line-height: 1.5;
}

/* 配置选择区域 */
.config-section {
  margin-bottom: 40px;
}

.config-group {
  margin-bottom: 32px;
}

.config-label {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.label-text {
  font-size: 18px;
  font-weight: 600;
  color: #ffffff;
}

.label-count {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.5);
}

.config-options {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
  gap: 16px;
}

.config-option {
  position: relative;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 16px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  border: 2px solid transparent;
  backdrop-filter: blur(10px);
}

.config-option:hover {
  transform: translateY(-4px);
  border-color: rgba(102, 126, 234, 0.3);
}

.config-option.active {
  border-color: #667eea;
  box-shadow: 0 0 20px rgba(102, 126, 234, 0.3);
  background: rgba(102, 126, 234, 0.1);
}

.option-image-wrapper {
  position: relative;
  width: 100%;
  padding-top: 75%;
  overflow: hidden;
}

.option-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.color-preview {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0.8;
}

.option-info {
  padding: 12px;
}

.option-name {
  font-size: 14px;
  font-weight: 600;
  color: #ffffff;
  margin-bottom: 4px;
}

.option-desc {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  line-height: 1.4;
}

.option-check {
  position: absolute;
  top: 8px;
  right: 8px;
  width: 28px;
  height: 28px;
  background: #667eea;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #ffffff;
  animation: scaleIn 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.option-check svg {
  width: 16px;
  height: 16px;
}

@keyframes scaleIn {
  from {
    transform: scale(0);
  }
  to {
    transform: scale(1);
  }
}

/* 预览区域 */
.preview-section {
  margin-bottom: 40px;
}

.preview-container {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 24px;
  padding: 32px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.preview-car {
  width: 100%;
  height: 300px;
  border-radius: 20px;
  margin-bottom: 24px;
  position: relative;
  overflow: hidden;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}

.three-canvas {
  width: 100%;
  height: 100%;
  position: relative;
  min-height: 300px;
}

.three-canvas canvas {
  display: block;
  width: 100% !important;
  height: 100% !important;
}

.preview-summary {
  margin-bottom: 24px;
}

.summary-item {
  display: flex;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.summary-item:last-child {
  border-bottom: none;
}

.summary-label {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.6);
}

.summary-value {
  font-size: 14px;
  font-weight: 600;
  color: #ffffff;
}

.save-button {
  width: 100%;
  padding: 16px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 16px;
  color: #ffffff;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.4);
}

.save-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 30px rgba(102, 126, 234, 0.6);
}

.save-button:active {
  transform: translateY(0);
}

.save-button svg {
  width: 20px;
  height: 20px;
}

/* 加载状态 */
.loading {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 60vh;
  color: rgba(255, 255, 255, 0.6);
}

.loading-spinner {
  width: 48px;
  height: 48px;
  border: 4px solid rgba(255, 255, 255, 0.1);
  border-top-color: #667eea;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 16px;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

/* 空状态 */
.empty-state {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 60vh;
  color: rgba(255, 255, 255, 0.6);
}

/* Toast 提示 */
:global(.toast) {
  position: fixed;
  bottom: 100px;
  left: 50%;
  transform: translateX(-50%) translateY(100px);
  background: rgba(0, 0, 0, 0.9);
  color: #ffffff;
  padding: 16px 24px;
  border-radius: 12px;
  font-size: 14px;
  z-index: 1000;
  opacity: 0;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
}

:global(.toast.show) {
  transform: translateX(-50%) translateY(0);
  opacity: 1;
}

:global(.toast.error) {
  background: rgba(239, 68, 68, 0.9);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .app-title {
    font-size: 24px;
  }

  .section-title {
    font-size: 20px;
  }

  .model-carousel {
    grid-template-columns: 1fr;
  }

  .config-options {
    grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
  }

  .preview-container {
    padding: 20px;
  }

  .preview-car {
    height: 200px;
  }
}
</style>
