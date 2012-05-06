package net.onpage.data.page;

import net.eincs.frame.util.ClassUtil;
import net.onpage.data.PageInfoType;

public class PageInfo {

	private Integer infoPr = null;

	private PageInfoType infoType = null;

	private String title = null;

	private String content = null;
		
	private Integer statusPr = null;
	
	private Integer pagePr = null;
	
	public Integer getInfoPr() {
		return infoPr;
	}

	public void setInfoPr(Integer infoPr) {
		this.infoPr = infoPr;
	}

	public PageInfoType getInfoType() {
		return infoType;
	}

	public void setInfoType(PageInfoType infoType) {
		this.infoType = infoType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatusPr() {
		return statusPr;
	}

	public void setStatusPr(Integer statusPr) {
		this.statusPr = statusPr;
	}

	public Integer getPagePr() {
		return pagePr;
	}

	public void setPagePr(Integer pagePr) {
		this.pagePr = pagePr;
	}
	
	@Override
	public String toString() {
		return ClassUtil.getFieldValueWithClass(PageInfo.class, this);
	}
}
