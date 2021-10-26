DROP TABLE users cascade constraint;
DROP TABLE board cascade constraint;
DROP TABLE boardreply cascade constraint;
DROP TABLE test cascade constraint;
DROP TABLE testreply cascade constraint;

SELECT * FROM users;
SELECT * FROM board;
SELECT * FROM boardreply;
SELECT * FROM test;
SELECT * FROM testreply;

/* 유저 ============================================*/
CREATE TABLE users(
   usernum INT PRIMARY KEY,
   name VARCHAR(15) NOT NULL,
   id VARCHAR(50) NOT NULL,
   pw VARCHAR(50) NOT NULL,
   phone VARCHAR(25) NOT NULL,
   gender VARCHAR(5) NOT NULL,
   email VARCHAR(255) NOT NULL,
   addr VARCHAR(255) NOT NULL,
   birth VARCHAR(30) NOT NULL,
   iconid VARCHAR(30) NOT NULL
);

/* 보드  ============================================*/
CREATE TABLE board(
   bid INT PRIMARY KEY,
   usernum int NOT NULL,
   bctgr VARCHAR(50) NOT NULL,
   btitle VARCHAR(100) NOT NULL,
   bcontent VARCHAR(4000) NOT NULL,
   bwriter VARCHAR(50) NOT NULL,
   bdate DATE DEFAULT SYSDATE,
   bhit INT DEFAULT 0,
   blang VARCHAR(20),
   recnt INT DEFAULT 0,
   CONSTRAINT board_cons FOREIGN KEY (usernum) REFERENCES users(usernum) ON DELETE CASCADE
);

/* 보드 댓글  ============================================*/
CREATE TABLE boardreply(

   rid INT PRIMARY KEY,
   bid INT NOT NULL,
   usernum INT NOT NULL,
   rcontent VARCHAR(255) NOT NULL,
   rdate DATE DEFAULT SYSDATE,
   deleteat VARCHAR(1) DEFAULT 'N',
   rwriter VARCHAR(20) NOT NULL,
   parentid INT NOT NULL,
   CONSTRAINT b_id_cons FOREIGN KEY (bid) REFERENCES board(bid) ON DELETE CASCADE,
   CONSTRAINT user_num_cons2 FOREIGN KEY (usernum) REFERENCES users(usernum) ON DELETE CASCADE
);

/* 테스트  ============================================*/
CREATE TABLE test(
   tid INT PRIMARY KEY,
   usernum int NOT NULL,
   ttitle VARCHAR(100) NOT NULL,
   tcontent VARCHAR(4000) NOT NULL,
   tanswer VARCHAR(4000) NOT NULL,
   tex VARCHAR(225) NOT NULL,
   twriter VARCHAR(50) NOT NULL,
   tdate DATE DEFAULT SYSDATE,
   thit int DEFAULT 0,
   tlang VARCHAR(20) NOT NULL,
   recnt int DEFAULT 0,
   CONSTRAINT user_num_cons FOREIGN KEY (usernum) REFERENCES users(usernum) ON DELETE CASCADE
);
-- 누적별점 컬럼 추가 
ALTER TABLE Test ADD (ttotal INT DEFAULT 0);
-- 별점제출 컬럼 추가 
ALTER TABLE Test ADD (tsubmit INT DEFAULT 0);
-- 평점 컬럼 추가
ALTER TABLE Test ADD (trating number DEFAULT 0);
-----------------------------------------------------
/* 테스트 댓글 ============================================*/
CREATE TABLE testreply(
	rid INT PRIMARY KEY, -- 댓글 번호
	tid INT NOT NULL, -- TEST게시글 번호
	usernum INT NOT NULL, -- 회원번호
	rcontent VARCHAR(225) NOT NULL, -- 댓글/대댓글 내용 
	rdate DATE DEFAULT SYSDATE, -- 댓글/대댓글 작성일
	deleteat VARCHAR(1) DEFAULT 'N', -- 댓글 삭제 유무
	rwriter VARCHAR(20) NOT NULL, -- 작성자 == ID
	parentid INT NOT NULL, -- 대댓글 확인
	CONSTRAINT t_id_cons FOREIGN KEY (tid) REFERENCES test(tid) ON DELETE CASCADE,
	CONSTRAINT user_num_cons3 FOREIGN KEY (usernum) REFERENCES users(usernum) ON DELETE CASCADE
);

/* 유저 샘플 =====================================================================================================================*/
INSERT INTO USERS VALUES (1, '김길동', 'kim', '1234', '01012341234', 'M', 'xxs@naver.com', '경기도', '19960205', 'default.png');
INSERT INTO USERS VALUES (2, '홍길동', 'hong', '1234', '01012345678', 'F', 'asdf1234@naver.com', '경기도', '19991021', 'default.png');
INSERT INTO USERS VALUES (99, '관리자', 'manager', 'managerpw', '01012341234', 'M', 'xxssgg120@naver.com', '경기도 군포시', '19960205', 'default.png');


/* 보드 샘플 =====================================================================================================================*/
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'board','게시글1','게시글 내용1','kim','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'board','게시글2','게시글 내용2','hong','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'board','게시글3','게시글 내용3','kim','C#');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'board','게시글4','게시글 내용4','hong','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'board','게시글5','게시글 내용5','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'board','게시글6','게시글 내용6','hong','C#');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'board','게시글7','게시글 내용7','hong','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'board','게시글8','게시글 내용8','kim','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'board','게시글9','게시글 내용9','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'board','게시글10','게시글 내용10','hong','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'question','질문1','게시글 내용11','kim','C#');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'question','질문2','게시글 내용12','hong','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'question','질문3','게시글 내용13','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'question','질문4','게시글 내용14','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'question','질문5','게시글 내용15','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'question','질문6','게시글 내용16','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'question','질문7','게시글 내용17','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'question','질문8','게시글 내용18','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'announce','공지사항1','게시글 내용19','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'announce','공지사항2','게시글 내용20','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'announce','공지사항3','게시글 내용21','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'announce','공지사항4','게시글 내용22','kim','C');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),1,'announce','공지사항5','게시글 내용23','kim','C');


/* 보드 댓글 샘플 =====================================================================================================================*/
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,2,'댓글1','hong',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,1,'댓글2','kim',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,1,'대댓글1','hong',1);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,1,'대댓글2','kim',1);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,1,'댓글3','kim',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),2,1,'댓글5','kim',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,1,'댓글6','kim',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),2,1,'댓글7','kim',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,1,'댓글8','kim',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,1,'댓글9','kim',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),2,1,'댓글10','kim',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),2,1,'댓글11','kim',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),2,1,'댓글12','kim',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,1,'대댓글3','kim',1);


/* 테스트 샘플 =====================================================================================================================*/
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제1','내용1','답1','출력1','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제2','내용2','답2','출력2','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제3','내용3','답3','출력3','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제4','내용4','답4','출력4','kim','C#');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제5','내용5','답5','출력5','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제6','내용6','답6','출력6','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제7','내용7','답7','출력7','kim','C');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제8','내용8','답8','출력8','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제9','내용9','답9','출력9','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제10','내용10','답10','출력10','kim','C');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제11','내용11','답11','출력11','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제12','내용12','답12','출력12','kim','C');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제13','내용13','답13','출력13','kim','C');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제14','내용14','답14','출력14','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제15','내용15','답15','출력15','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제16','내용16','답16','출력16','kim','C#');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제17','내용17','답17','출력17','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제18','내용18','답18','출력18','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제19','내용19','답19','출력19','kim','C');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제20','내용20','답20','출력20','kim','C');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제21','내용21','답21','출력21','kim','C');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제22','내용22','답22','출력22','kim','JAVA');
INSERT INTO test (tid, usernum, ttitle, tcontent, tanswer, tex, twriter, tlang) VALUES ((SELECT NVL(MAX(tid),0)+1 FROM test),1,'문제23','내용23','답23','출력23','kim','C#');


/* 테스트 댓글 샘플 =====================================================================================================================*/
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글1','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글1_대댓1','kim',1);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글2','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글2_대댓1','kim',3);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글3','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글4','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글5','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글6','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,2,'댓글7','hong',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,2,'댓글8','hong',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글9','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글10','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글11','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),2,2,'댓글12','hong',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),2,1,'댓글13','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),1,1,'댓글14','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),2,1,'댓글15','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),2,1,'댓글16','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),2,1,'댓글17','kim',0);
INSERT INTO testreply (rid, tid, usernum, rcontent, rwriter, parentid) VALUES ((SELECT NVL(MAX(rid),0)+1 FROM testreply),2,1,'댓글18','kim',0);



update test set trating= round(total/submit,2) where tid=1;

-- 컬럼명 변경
ALTER TABLE Test RENAME COLUMN total TO ttotal;
ALTER TABLE Test RENAME COLUMN submit TO tsubmit;

ALTER TABLE Test DROP COLUMN trating;

INSERT INTO USERS VALUES (2, '김영진', 'yj', '1234', '01012341234', 'M', 'ccc@naver.com', '경기도 군포시', '19961223', 'default.png');
INSERT INTO USERS VALUES (3, '박정은', 'je', '1222', '01000000000', 'F', 'bbb@naver.com', '경기도 안양시', '19961112', 'default.png');
INSERT INTO USERS VALUES (4, '유태희', 'you', '1111', '01000000000', 'M', 'aaa@naver.com', '경기도 안성시', '19971222', 'default.png');
INSERT INTO USERS VALUES (5, '이현준', 'lee', '1245', '01022222222', 'M', 'ddd@naver.com', '서울특별시 서대문구', '19971111', 'default.png');


INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'자유게시판','이게 왜 되지??','아니 안돌아가야되는데 진짜 왜 돌아가는지 1도 모르겠다... ㅠㅠ','yj','기타');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'자유게시판','이게 왜 안되지??','돌아가야되는데 이게 왜 안돌아가는지 1도 모르겠다','yj','기타');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'자유게시판','코딩공부 있잖아....','님들은 공부 어떤식으로 하고있어???','kim','기타');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'자유게시판','이직하고 싶다 진짜...','괜찮은데 없나??','kim','기타');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),3,'board','가을맞나요?','가을 없어진거 같은데,,,어디갔죠?','je','기타');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),3,'board','JAVA스터디 구함','코린이신 분들 중에 스터디하실 분 구해요!','je','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),3,'board','스프링 많이 어렵나요?','요즘 백엔드하면 스프링 많이 사용한다는데 많이 어렵나요?','je','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),4,'board','요즘 취업 하려면 스프링은 필수 인 듯','좋은 데 취업하려면 필요한 스프링은 무조건 해야 하는 것 같아요 ','you','Spring');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),4,'board','면접 잘하는 팁 공유','기본 개념을 탑 재하는 게 정말 중요해보여요 . 무엇보다도 ','you','취업');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),4,'board','요즘 인강 퀄리트 정말 좋은 것 같아요','예전에 비해서 훨씬 양질의 강의가 많이 올라오는 듯','you','기타');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),5,'board','홍제역 하이드미 플리즈 후기','여기 도넛 가격 좀 있는데 진짜 맛나여! 먹고나서 또 포장도 해버려서 지갑 탈탈...','lee','자유');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),5,'board','점심메뉴 추천받아요!','오늘도 무엇을먹고 버텨야될까요 추천좀 ㅎㅎ','lee','자유');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),5,'board','오늘도...지옥철','으아아아 나도 앉아서 가고싶다아아아','lee','자유');

INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'question','접근 제어자 질문','접근제어자 private는 왜 사용하나요?','yj','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'question','MVC가 뭐에요?','최근에 mvc패턴이라고 배웠는데 오히려 더 귀찮은 작업이 많아지는 거 같은데 장점이 뭐에요?','yj','DB');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'question','트랜잭션??','트랜잭션 배웠는데 어떻게 써야할지 막막하네요 ㅠㅠ 이거 왜 쓰는거에요??','yj','DB');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'question','객체지향언어 특징??','객체지향언어의 특징이 있다던데 뭐에요??','yj','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),2,'question','server 질문있는데요','웹서버랑 WAS차이점이 뭐에요?? 비슷한거 같은데 헛갈리네요 ㅠㅠ','yj','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),3,'question','DBMS가 뭔가요?','DB는 데이터인걸 알겠는데 DBMS는 뭐인가요??','je','DB');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),3,'question','DB관리? ','DB관리의 핵심을 면접에서 물어보면 어떻게 답해야되죠?','je','DB');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),3,'question','sql문 DDL,DML,DCL??','DDL,DML,DCL <- 이 3개의 차이가 뭔가요??!','je','DB');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),3,'question','빌드? 컴파일?','빌드 컴파일!!! 차이 좀 알려주세요! 급해요!!!','je','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),3,'question','자바 입문하는 코린이 질문이요,,','JVM, JRE, JDK에 대해 궁금합니다. 각각 무엇을 말하는 거죠?','je','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),4,'question','자료구조 잘하시는 분!!','스택과 큐 많이 들어 봤는 데, 개념 설명 좀 해주세요!','you','자료구조');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),4,'question','인터페이스 질문드립니다~','인터페이스로 메서드 강제하는 건 알 겠는데 왜 써야 하는 건가요?','you','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),4,'question','url과 uri의 차이','uri 와 rul 개념이 비슷한 것 같은데 차이점 좀 알려주세요!','you','네트워크');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),4,'question','get과 post의 차이','get과 post 의 차이점 좀 알려주세요!','you','네트워크');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),4,'question','request와 response의 차이','request와 response 개념이 헤깔리네요 ... 비슷한거 아닌가요?','you','네트워크');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),5,'question','예외처리 왜해요??','그 try catch문 쓰잖아요 그거 왜하는거에요??','lee','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),5,'question','인터프리터언어 VS 컴파일언어','두개 언어 정확한 차이점이 뭐에요??','lee','코딩');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),5,'question','JNDI,DBCP,JDBC ?','3개 전부 db관련되있는건 알겠는데 정확히 뭐가 뭐에요??','lee','DB');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),5,'question','지역변수와 전역변수 두개 차이점?','둘다 변수라는데 어떤식으로 차이점이 있는거에요??','lee','JAVA');
INSERT INTO board(bid, usernum, bctgr, btitle, bcontent, bwriter, blang) VALUES((SELECT NVL(MAX(bid), 0)+1 FROM board),5,'question','클래스란?','클래스라는게 항상 사용하는건 알겠는데 정의가 뭐라고 해야되요??','lee','JAVA');

INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,2,'아싸 1등','yj',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,2,'너도?? 나도 ㅋㅋㅋ','yj',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,2,'기도가 부족해서 그래ㅋㅋ','yj',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),1,3,'댓글남겨요~','je',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),4,3,'안녕하세요','je',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),7,3,'댓글댓글','je',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),2,4,'좋은 글 감사합니다.','you',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),5,4,'안녕하세요','you',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),8,4,'앞으로도 글 많이 써주세요~','you',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),2,5,'하여','lee',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),3,5,'머리폭팔','lee',0);
INSERT INTO boardreply (rid, bid, usernum, rcontent, rwriter, parentid) VALUES((SELECT NVL(MAX(rid), 0)+1 FROM boardreply),8,5,'ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ','lee',0);

update board set recnt = 4 where bid=1;
update board set recnt = 2 where bid=2;
update board set recnt = 1 where bid=3;
update board set recnt = 1 where bid=4;
update board set recnt = 1 where bid=5;
update board set recnt = 1 where bid=7;
update board set recnt = 2 where bid=8;

