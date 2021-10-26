package model.board;

import java.util.ArrayList;

public class ReplySet {
	
	// ´ñ±Û °´Ã¼
	private ReplyVO rvo;
	// ´ë´ñ±Û ¸®½ºÆ®
	private ArrayList<ReplyVO> rrlist = new ArrayList<ReplyVO>();
	private int replyCnt;
	
	public ReplyVO getRvo() {
		return rvo;
	}
	public void setRvo(ReplyVO rvo) {
		this.rvo = rvo;
	}
	public ArrayList<ReplyVO> getRrlist() {
		return rrlist;
	}
	public void setRrlist(ArrayList<ReplyVO> rrlist) {
		this.rrlist = rrlist;
	}
	public int getReplyCnt() {
		return replyCnt;
	}
	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}
	
	@Override
	public String toString() {
		return "ReplySet [rvo=" + rvo + ", rrlist=" + rrlist + ", repltCnt=" + replyCnt + "]";
	}
	
	
	
}
