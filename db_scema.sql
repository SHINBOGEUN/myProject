CREATE TABLE `member` (
  `member_id` varchar(100) NOT NULL COMMENT '아이디',
  `name` varchar(15) NOT NULL COMMENT '이름',
  `member_role` varchar(10) NOT NULL COMMENT '회원구분코드',
  `password` varchar(256) DEFAULT NULL COMMENT '패스워드',
  `phone` varchar(13) DEFAULT NULL COMMENT '전화번호',
  `birthday` date DEFAULT NULL COMMENT '생일',
  `gender_code` varchar(10) DEFAULT NULL COMMENT '성별',
  `email` varchar(100) DEFAULT NULL COMMENT '이메일',
  `create_ts` datetime NOT NULL COMMENT '생성일',
  `update_ts` datetime NOT NULL COMMENT '갱신일',
  `use_yn` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '사용유무',
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='회원 데이터';