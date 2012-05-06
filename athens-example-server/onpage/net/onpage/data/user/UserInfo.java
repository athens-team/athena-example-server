package net.onpage.data.user;

import java.sql.Timestamp;

import net.onpage.data.PrivacyType;

public class UserInfo {

	private int detailPr = 0;
	
	private int userPr = 0;

	private int infoType = 0;
	
	private int infoSubType = 0;
	
	private String title = null;
	
	private String content = null;
	
	private PrivacyType privacyType = null;
	
	private Timestamp createDate = null;

	public int getDetailPr() {
		return detailPr;
	}

	public void setDetailPr(int detailPr) {
		this.detailPr = detailPr;
	}

	public int getUserPr() {
		return userPr;
	}

	public void setUserPr(int userPr) {
		this.userPr = userPr;
	}

	public int getInfoType() {
		return infoType;
	}

	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}

	public int getInfoSubType() {
		return infoSubType;
	}

	public void setInfoSubType(int infoSubType) {
		this.infoSubType = infoSubType;
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

	public PrivacyType getPrivacyType() {
		return privacyType;
	}

	public void setPrivacyType(PrivacyType privacyType) {
		this.privacyType = privacyType;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString()
	{
		return "[UserInfoDetail="+detailPr+"/"+userPr+"/"+infoType+"/"+infoSubType+"/"+title+"/"+content+"/"+privacyType+"/"+createDate+"]";
	}
}
