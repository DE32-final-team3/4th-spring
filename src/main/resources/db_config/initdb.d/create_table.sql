CREATE TABLE face_age (
    num INT AUTO_INCREMENT PRIMARY KEY NOT NULL COMMENT 'Serial number',
    origin_name VARCHAR(100) NOT NULL COMMENT '원본 파일명',
    file_path VARCHAR(255) NOT NULL COMMENT '저장 전체 경로 및 변환 파일명',
    request_time CHAR(19) NOT NULL COMMENT '요청시간',
    prediction_result VARCHAR(10) COMMENT '예측 결과',
    answer VARCHAR(10) COMMENT '실제 정답'
);