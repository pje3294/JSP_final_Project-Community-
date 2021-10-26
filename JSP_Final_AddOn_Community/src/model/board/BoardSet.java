package model.board;

import java.util.ArrayList;

public class BoardSet {
	
	private ArrayList<BoardVO> blist = new ArrayList<BoardVO>();
	private int boardCnt;
	public ArrayList<BoardVO> getBlist() {
		return blist;
	}
	public void setBlist(ArrayList<BoardVO> blist) {
		this.blist = blist;
	}
	public int getBoardCnt() {
		return boardCnt;
	}
	public void setBoardCnt(int boardCnt) {
		this.boardCnt = boardCnt;
	}
	@Override
	public String toString() {
		return "BoardSet [blist=" + blist + ", boardCnt=" + boardCnt + "]";
	}
	

	

}
