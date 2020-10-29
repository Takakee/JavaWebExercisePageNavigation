package vo;

import java.util.HashMap;

public class PageBean {

	// 每页有多少条记录
	private Integer pageSize;
	// 当前是哪一页
	private Integer pageNumber;
	// 按照什么排序
	private String sort;
	// 排序是升序还是降序
	private String sortOrder;

	public PageBean(Integer pageSize, Integer pageNumber, String sort, String sortOrder) {
		super();
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.sort = sort;
		this.sortOrder = sortOrder;
	}
	
	public PageBean getByHashMap(HashMap<String, Object> mapPage) {
		PageBean page = new PageBean();
		page.setPageSize(Integer.parseInt(mapPage.get("pageSize").toString()));
		page.setPageNumber(Integer.parseInt(mapPage.get("pageNumber").toString()));
		page.setSort((String)mapPage.get("sort"));
		page.setSortOrder((String)mapPage.get("sortOrder"));
		return page;
	}

	public PageBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return "PageBean [pageSize=" + pageSize + ", pageNumber=" + pageNumber + ", sort=" + sort + ", sortOrder="
				+ sortOrder + "]";
	}

}
