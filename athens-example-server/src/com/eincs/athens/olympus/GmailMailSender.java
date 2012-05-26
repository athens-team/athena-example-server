package com.eincs.athens.olympus;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GmailMailSender {
	private static final Logger logger = LoggerFactory
			.getLogger(GmailMailSender.class);
	private static GmailMailSender INSTANCE = new GmailMailSender();

	private ExecutorService executor;
	private AtomicInteger threadId = new AtomicInteger(0);
	private Properties props = null;

	public GmailMailSender() {
		props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.port", "465");
		executor = Executors.newFixedThreadPool(16, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r, "Gmail Mail sender "
						+ threadId.getAndAdd(1));
				return thread;
			}
		});
	}

	public static GmailMailSender getInstance() {
		return INSTANCE;
	}

	public void sendMail(final String from, final String to,
			final String subject, final String content) {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				Session mailSession = Session.getDefaultInstance(props,
						new GmailSmtpAuthenticator());
				MimeMessage msg = new MimeMessage(mailSession);

				try {
					msg.setFrom(InternetAddress.parse(from)[0]);

					msg.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(to));
					msg.setSubject(subject, "UTF-8");
					MimeMultipart multiPart = new MimeMultipart();
					MimeBodyPart contentPart = new MimeBodyPart();
					contentPart.setHeader("Content-Type",
							"text/html;charset=utf-8");
					contentPart.setContent(content, "text/html;charset=utf-8");
					multiPart.addBodyPart(contentPart);
					msg.setContent(multiPart);
					Transport.send(msg);
				} catch (MessagingException e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}

	private static class GmailSmtpAuthenticator extends Authenticator {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("athens.team.2012@gmail.com",
					"dlgmlwhrytnsla");
		}
	}

}
