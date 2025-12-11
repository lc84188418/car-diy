package com.cardiy.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuchun
 * @version v2.2.0
 * @className DateUtil
 * @desc TODO
 * @createTime 2025/12/3 15:23
 */
public class DateUtil {
    public static final String PATTERN_Y = "yyyy";
    public static final String PATTERN_YM = "yyyyMM";
    public static final String PATTERN_YMD = "yyyyMMdd";
    /**
     * @desc:获取当前年月日的integer类型的数组
     * @author: liuchun
     * @createTime: 2024/9/23 11:36
     * @param: []
     * @version: V1.29.0
     * @return: java.lang.Integer[]
     **/
    public static Integer[] getIntegerDateByYMD() {
        Date date = new Date();
        Integer[] dates = new Integer[3];
        dates[0] = Integer.valueOf(new SimpleDateFormat(PATTERN_Y).format(date));
        dates[1] = Integer.valueOf(new SimpleDateFormat(PATTERN_YM).format(date));
        dates[2] = Integer.valueOf(new SimpleDateFormat(PATTERN_YMD).format(date));
        return dates;
    }
}
