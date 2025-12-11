package com.cardiy.render.controller;

import com.cardiy.common.api.Result;
import com.cardiy.common.dto.ConfigPlanDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RenderController {

    @PostMapping("/preview")
    public Result<RenderResult> preview(@RequestBody ConfigPlanDTO dto) {
        RenderResult result = new RenderResult();
        result.setPlanId(dto.getId() != null ? dto.getId() : UUID.randomUUID().toString());

        // 根据车型返回对应的 3D 模型 URL（gltf/glb）
        // 实际项目中可以接入你的 3D 渲染/模型服务，这里用公开示例模型演示
        String modelId = dto.getModelId();
        if ("model-1".equals(modelId)) {
            result.setModelUrl("https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/CesiumMilkTruck/glTF/CesiumMilkTruck.gltf");
        } else if ("model-2".equals(modelId)) {
            result.setModelUrl("https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/VC/glTF/VC.gltf");
        } else {
            result.setModelUrl("https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF/Duck.gltf");
        }

        return Result.success(result);
    }

    public static class RenderResult {
        private String planId;
        /**
         * 3D 模型地址（gltf / glb）
         */
        private String modelUrl;

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getModelUrl() {
            return modelUrl;
        }

        public void setModelUrl(String modelUrl) {
            this.modelUrl = modelUrl;
        }
    }
}


