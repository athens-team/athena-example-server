package net.rothlee.athens.olympus.config;

public class TokenSecretConf {
	private byte[] hmac;
	private byte[] symmetric;

	public byte[] getHmac() {
		return hmac;
	}

	public void setHmac(byte[] hmac) {
		this.hmac = hmac;
	}

	public byte[] getSymmetric() {
		return symmetric;
	}

	public void setSymmetric(byte[] symmetric) {
		this.symmetric = symmetric;
	}
}