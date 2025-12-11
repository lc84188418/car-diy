package com.cardiy.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarModelDTO {

    private String id;
    private String brand;
    private String series;
    private String year;
    /**
     * 车型主图URL
     */
    private String mainImageUrl;
    /**
     * 车型描述
     */
    private String description;
    private Options options;

    @Data
    public static class Options {
        private List<SimpleOption> paintColors;
        private List<SimpleOption> rims;
        private List<SimpleOption> tires;
        private List<SimpleOption> calipers;
    }

    @Data
    public static class SimpleOption {
        private String code;
        private String name;
        /**
         * 仅用于前端预览用的颜色值等
         */
        private String previewColor;
        /**
         * 配置项图片URL
         */
        private String imageUrl;
        /**
         * 配置项描述
         */
        private String description;
    }
}


