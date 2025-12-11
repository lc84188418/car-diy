package com.cardiy.admin.controller;

import com.cardiy.common.api.Result;
import com.cardiy.common.dto.CarModelDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final List<CarModelDTO> MODELS = new ArrayList<>();

    @GetMapping("/stats")
    public Result<Stats> stats() {
        Stats s = new Stats();
        s.setModelCount(MODELS.size());
        s.setOptionCount(20);
        s.setPlanCount(5);
        return Result.success(s);
    }

    @GetMapping("/models")
    public Result<List<CarModelDTO>> listModels() {
        return Result.success(MODELS);
    }

    @PostMapping("/models")
    public Result<CarModelDTO> saveModel(@RequestBody CarModelDTO dto) {
        MODELS.removeIf(m -> m.getId() != null && m.getId().equals(dto.getId()));
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId("admin-" + (MODELS.size() + 1));
        }
        MODELS.add(dto);
        return Result.success(dto);
    }

    public static class Stats {
        private int modelCount;
        private int optionCount;
        private int planCount;

        public int getModelCount() {
            return modelCount;
        }

        public void setModelCount(int modelCount) {
            this.modelCount = modelCount;
        }

        public int getOptionCount() {
            return optionCount;
        }

        public void setOptionCount(int optionCount) {
            this.optionCount = optionCount;
        }

        public int getPlanCount() {
            return planCount;
        }

        public void setPlanCount(int planCount) {
            this.planCount = planCount;
        }
    }
}


