package model.board;

import java.util.ArrayList;

public class MyReplySet {

	ArrayList<ReplyVO> rlist = new ArrayList<ReplyVO>();
	private int replyCnt;
	public ArrayList<ReplyVO> getRlist() {
		return rlist;
	}
	public void setRlist(ArrayList<ReplyVO> rlist) {
		this.rlist = rlist;
	}
	public int getReplyCnt() {
		return replyCnt;
	}
	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}
	
	@Override
	public String toString() {
		return "MyReplySet [rlist=" + rlist + ", replyCnt=" + replyCnt + "]";
	}
	
	
}
