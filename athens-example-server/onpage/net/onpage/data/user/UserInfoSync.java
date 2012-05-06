package net.onpage.data.user;

import java.sql.Timestamp;

import net.onpage.data.PrivacyType;

public class UserInfoSync {

	private int syncPr = 0;

	private int userPr = 0;
	
	private String syncType = null;
	
	private String userId = null;
	
	private String userKey = null;
	
	private String userKeySecret = null;
	
	private byte[] accessToken = null;
	
	private byte[] accessTokenSecret = null;
	
	private PrivacyType privacyType = null;
	
	private String enabledYn = null;
	
	private Timestamp createDate = null;

	public int getSyncPr() {
		return syncPr;
	}

	public void setSyncPr(int syncPr) {
		this.syncPr = syncPr;
	}

	public int getUserPr() {
		return userPr;
	}

	public void setUserPr(int userPr) {
		this.userPr = userPr;
	}

	public String getSyncType() {
		return syncType;
	}

	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserKeySecret() {
		return userKeySecret;
	}

	public void setUserKeySecret(String userKeySecret) {
		this.userKeySecret = userKeySecret;
	}

	public byte[] getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(byte[] accessToken) {
		this.accessToken = accessToken;
	}

	public byte[] getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(byte[] accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public PrivacyType getPrivacyType() {
		return privacyType;
	}

	public void setPrivacyType(PrivacyType privacyType) {
		this.privacyType = privacyType;
	}

	public String getEnabledYn() {
		return enabledYn;
	}

	public void setEnabledYn(String enabledYn) {
		this.enabledYn = enabledYn;
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
		return "[UserSync="+syncPr+"/"+userPr+"/"+syncType+"/"+userId+"/"+userKey+"/"+userKeySecret+"/"+privacyType+"/"+enabledYn+"/"+createDate+"]";
	}
}
