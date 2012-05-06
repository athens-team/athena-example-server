package net.onpage.data.page;

import net.eincs.frame.util.ClassUtil;
import net.onpage.data.GeoInfo;
import net.onpage.data.PageType;
import net.onpage.json.annotation.JSONField;
import net.onpage.json.annotation.JSONOptional;
import net.onpage.json.annotation.JSONable;
import net.onpage.json.enumeration.JSONType;

@JSONable
public class Page {
	
	@JSONField(Name="pageId", JsonType=JSONType.Object, JavaType=Integer.class)
	private Integer pagePr = null;
	
	@JSONField(Name="pageType", JsonType=JSONType.Object, JavaType=PageType.class)
	private PageType pageType = null;
	
	@JSONField(Name="pageTitle", JsonType=JSONType.Object, JavaType=String.class)
	private String pageTitle = null;

	@JSONField(Name="profilePic", JsonType=JSONType.Object, JavaType=String.class)
	private String profilePic = null;
	
	@JSONField(Name="InfoCnt", JsonType=JSONType.Object, JavaType=Integer.class)
	private Integer InfoCnt = null;
	
	@JSONField(Name="peopleCnt", JsonType=JSONType.Object, JavaType=Integer.class)
	private Integer peopleCnt = null;
	
	@JSONField(Name="pageCnt", JsonType=JSONType.Object, JavaType=Integer.class)
	private Integer pageCnt = null;
	
	@JSONField(Name="geoInfo", JsonType=JSONType.Object, JavaType=GeoInfo.class)
	@JSONOptional
	private GeoInfo geoInfo = null;

	public Integer getPagePr() {
		return pagePr;
	}

	public void setPagePr(Integer pagePr) {
		this.pagePr = pagePr;
	}

	public PageType getPageType() {
		return pageType;
	}

	public void setPageType(PageType pageType) {
		this.pageType = pageType;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public Integer getInfoCnt() {
		return InfoCnt;
	}

	public void setInfoCnt(Integer infoCnt) {
		InfoCnt = infoCnt;
	}

	public Integer getPeopleCnt() {
		return peopleCnt;
	}

	public void setPeopleCnt(Integer peopleCnt) {
		this.peopleCnt = peopleCnt;
	}

	public Integer getPageCnt() {
		return pageCnt;
	}

	public void setPageCnt(Integer pageCnt) {
		this.pageCnt = pageCnt;
	}

	public GeoInfo getGeoInfo() {
		return geoInfo;
	}

	public void setGeoInfo(GeoInfo geoInfo) {
		this.geoInfo = geoInfo;
	}

	@Override
	public String toString()
	{
		return ClassUtil.getFieldValueWithClass(Page.class, this);
	}
}
