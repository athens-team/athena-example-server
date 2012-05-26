package net.rothlee.athens.olympus.test;

import com.eincs.athens.olympus.GmailMailSender;

public class GmailSenderTest {

	public static void main(String[] args) {
		GmailMailSender sender = GmailMailSender.getInstance();
		sender.sendMail("athens.team.2012@gmail.com", "roth2520@gmail.com",
				"하하하 이건 제목 ㅋㅋ", "ㅋㅋㅋㅋ  <b> ㅋㅋㅋㅋ </b>  안녕하세요.");
	}
}
