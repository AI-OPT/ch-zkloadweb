package com.ai.baas.mt.web.model;

/**
 * 分页工具类
 *
 * @author gucl
 */
public final class PagerHelper {


    private PagerHelper() {}


    /**
     * 返回的是起始号
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static int getStartRow(int currentPage, int pageSize) {
        return (currentPage - 1) * pageSize;
    }

    /**
     * 返回的是终止号
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static int getEndRow(int currentPage, int pageSize) {
        return currentPage * pageSize;
    }

}
