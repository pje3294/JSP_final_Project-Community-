package crawling.model;

import java.util.Date;

public class CodingVO {
	/*
	 * tid INT PRIMARY KEY, usernum int NOT NULL, ttitle VARCHAR(100) NOT NULL,
	 * tcontent VARCHAR(4000) NOT NULL, tanswer VARCHAR(4000) NOT NULL, tex
	 * VARCHAR(225) NOT NULL, twriter VARCHAR(50) NOT NULL, tdate DATE DEFAULT
	 * SYSDATE, thit int DEFAULT 0, tlang VARCHAR(20) NOT NULL, recnt int DEFAULT 0,
	 * CONSTRAINT user_num_cons FOREIGN KEY (usernum) REFERENCES users(usernum) ON
	 * DELETE CASCADE
	 */

	private int tId;
	private int userNum;
	private String tTitle;
	private String tContent;
	private String tAnswer;
	private String tEx;
	private String tWriter;
	private Date tDate;
	private int tHit;
	private String tLang;
	private int reCnt;

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public String gettTitle() {
		return tTitle;
	}

	public void settTitle(String tTitle) {
		this.tTitle = tTitle;
	}

	public String gettContent() {
		return tContent;
	}

	public void settContent(String tContent) {
		this.tContent = tContent;
	}

	public String gettAnswer() {
		return tAnswer;
	}

	public void settAnswer(String tAnswer) {
		this.tAnswer = tAnswer;
	}

	public String gettEx() {
		return tEx;
	}

	public void settEx(String tEx) {
		this.tEx = tEx;
	}

	public String gettWriter() {
		return tWriter;
	}

	public void settWriter(String tWriter) {
		this.tWriter = tWriter;
	}

	public Date gettDate() {
		return tDate;
	}

	public void settDate(Date tDate) {
		this.tDate = tDate;
	}

	public int gettHit() {
		return tHit;
	}

	public void settHit(int tHit) {
		this.tHit = tHit;
	}

	public String gettLang() {
		return tLang;
	}

	public void settLang(String tLang) {
		this.tLang = tLang;
	}

	public int getReCnt() {
		return reCnt;
	}

	public void setReCnt(int reCnt) {
		this.reCnt = reCnt;
	}

	@Override
	public String toString() {
		return "   TestVO [tId=" + tId + ", userNum=" + userNum + ", tTitle=" + tTitle + ", tContent=" + tContent
				+ ", tAnswer=" + tAnswer + ", tEx=" + tEx + ", tWriter=" + tWriter + ", tDate=" + tDate + ", tHit="
				+ tHit + ", tLang=" + tLang + ", reCnt=" + reCnt + "]";
	}

}
