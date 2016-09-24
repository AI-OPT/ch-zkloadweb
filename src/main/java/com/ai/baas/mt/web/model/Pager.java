package com.ai.baas.mt.web.model;

import java.io.Serializable;

/**
 * 分页对象
 *
 * @author gucl
 */
public class Pager implements Serializable {

	//每页显示的记录条数
	private int pageSize;
    //当前显示的页数
    private int currentPage;
    //总页数
    private int totalPages;

    public Pager(int currentPage, int totalSize, int pageSize){
        this.currentPage = currentPage;
        this.totalPages = (totalSize + pageSize -1) / pageSize;
        this.pageSize=pageSize;
    }

    public Pager(int currentPage, int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageSize=10;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
    
    
}
