# kotlin-movie

## 🚀 3단계 - 영화 예매(데이터베이스)

### 데이터베이스 스키마 설계

#### MOVIE (영화)

| 컬럼 | 타입 | 설명 |                                                    
  |---|---|---|
| id | BIGINT PK | 자동 증가 |                                            
| title | VARCHAR(255) | 영화 제목 |
| running_time | INT | 상영 시간 (분) |                                  
| start_date | DATE | 상영 시작 기간 |                                    
| end_date | DATE | 상영 종료 기간 |

#### SCREENING (상영 일정)

| 컬럼 | 타입 | 설명 |
  |---|---|---|                                                            
| id | BIGINT PK | 자동 증가 |
| movie_id | BIGINT FK | MOVIE 참조 |                                     
| start_time | TIMESTAMP | 상영 시작 시각 |
| end_time | TIMESTAMP | 상영 종료 시각 |                                 

#### RESERVATION (예매 내역)

| 컬럼 | 타입 | 설명 |                                                    
  |---|---|---|
| id | BIGINT PK | 자동 증가 |                                            
| payment_method | VARCHAR(50) | 결제 수단 (CREDIT_CARD 등) |
| used_point | INT | 사용 포인트 |                                        
| total_price | INT | 최종 결제 금액 |
| created_at | TIMESTAMP | 예매 일시 |                                    

#### RESERVATION_ITEM (예매 상세)

| 컬럼 | 타입 | 설명 |
  |---|---|---|                                                            
| id | BIGINT PK | 자동 증가 |
| reservation_id | BIGINT FK | RESERVATION 참조 |                         
| screening_id | BIGINT FK | SCREENING 참조 |
| seat_name | VARCHAR(10) | 좌석 이름 (예: C2) |                          

---                                                                       

### 기능 목록

#### 데이터베이스 초기화

- [x] 애플리케이션 실행 시 H2 연결 및 DDL 실행
- [ ] 최초 실행 시 MockData를 DB에 적재

#### 영화 및 상영 조회

- [ ] DB에서 영화 목록과 상영 정보를 조회하여 도메인 객체로 변환
- [ ] 상영별 예약된 좌석 목록을 DB에서 조회하여 좌석 현황 출력

#### 예매 및 결제

- [ ] 예매 요청 처리 시 `RESERVATION`, `RESERVATION_ITEM`을 하나의 트랜잭션으로 저장
- [ ] 중복 예약 시도 시 예외 처리
- [ ] 프로그램 재시작 후 DB에서 과거 예매 내역 조회

#### 테스트

- [ ] H2를 사용한 Repository 통합 테스트 작성
- [ ] 예매, 중복 예약 방지, 조회 시나리오 테스트 작성
