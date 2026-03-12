package com.email.writer.others;

import lombok.Data;

@Data
public class EmailRequest {
private String emailContent;
private String Tone;
public String getEmailContent() {
	return emailContent;
}
public void setEmailContent(String emailContent) {
	this.emailContent = emailContent;
}
public String getTone() {
	return Tone;
}
public void setTone(String tone) {
	Tone = tone;
}
}
