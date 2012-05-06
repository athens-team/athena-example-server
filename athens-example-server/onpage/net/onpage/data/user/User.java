package net.onpage.data.user;

import net.eincs.frame.util.ClassUtil;

public class User {

	private Integer userPr = null;

	private String userId = null;

	private String nickName = null;

	private String profilePic = null;

	public Integer getUserPr() {
		return userPr;
	}

	public void setUserPr(Integer userPr) {
		this.userPr = userPr;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@Override
	public String toString() {
		return ClassUtil.getFieldValueWithClass(User.class, this);
	}
}
