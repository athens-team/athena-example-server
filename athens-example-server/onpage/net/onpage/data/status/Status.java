package net.onpage.data.status;

import java.sql.Timestamp;

import net.eincs.frame.util.ClassUtil;
import net.onpage.data.StatusStatus;
import net.onpage.data.StatusType;
import net.onpage.data.page.Page;
import net.onpage.data.user.User;

public class Status {

	private Integer statusPr = null;
	
	private StatusType statusType = null;
	
	private StatusStatus status = null;
	
	private String content = null;
	
	private Integer commentCnt = null;
	
	private Integer likeCnt = null;
	
	private Page postedPage = null;
	
	private User createUser = null;
	
	private Timestamp createDate = null;
	
	private Object attachInfo = null;
	
	public Integer getStatusPr() {
		return statusPr;
	}

	public void setStatusPr(Integer statusPr) {
		this.statusPr = statusPr;
	}

	public StatusType getStatusType() {
		return statusType;
	}

	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}

	public StatusStatus getStatus() {
		return status;
	}

	public void setStatus(StatusStatus status) {
		this.status = status;
	}

	public Page getPostedPage() {
		return postedPage;
	}

	public void setPostedPage(Page postedPage) {
		this.postedPage = postedPage;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(Integer commentCnt) {
		this.commentCnt = commentCnt;
	}

	public Integer getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(Integer likeCnt) {
		this.likeCnt = likeCnt;
	}

	public Object getAttachInfo() {
		return attachInfo;
	}

	public void setAttachInfo(Object attachInfo) {
		this.attachInfo = attachInfo;
	}

	@Override
	public String toString()
	{
		return ClassUtil.getFieldValueWithClass(Status.class, this);
	}
	
}
