package net.onpage.db.mapper;

import net.onpage.data.page.Page;
import net.onpage.data.page.PageInfo;
import net.onpage.data.status.Status;
import net.onpage.data.user.User;

public interface OnPageMapper {

	public Page getPage(int pagePr);
	
	public PageInfo getPageInfo(int infoPr);
	
	public PageInfo getPageRelationUser(int relationPr);
	
	public PageInfo getPageRelationPage(int relationPr);

	public User getUser(int userPr);
	
	public Status getStatus(int statusPr);

}
