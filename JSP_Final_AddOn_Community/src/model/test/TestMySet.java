package model.test;

import java.util.ArrayList;

public class TestMySet {
	private int testRecnt; // ������������ ���� ���,���� �����ֱ����� ��ü �� ����
	private ArrayList<TestReplyVO> rlist = new ArrayList<TestReplyVO>();

	public int getTestRecnt() {
		return testRecnt;
	}

	public void setTestRecnt(int testRecnt) {
		this.testRecnt = testRecnt;
	}

	public ArrayList<TestReplyVO> getRlist() {
		return rlist;
	}

	public void setRlist(ArrayList<TestReplyVO> rlist) {
		this.rlist = rlist;
	}

	@Override
	public String toString() {
		return "TestMySet [testRecnt=" + testRecnt + ", rlist=" + rlist + "]";
	}



	
	
	
}
