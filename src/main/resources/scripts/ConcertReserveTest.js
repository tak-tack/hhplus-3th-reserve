import http from 'k6/http';
import { check, sleep } from 'k6';

// 전역 변수로 설정할 기본값들
const BASE_URL = 'http://localhost:8080';
const MAX_USER_ID = 25800;
const MAX_CONCERT_OPTION_ID = 258;

export let options = {
  vus: 100,
  duration: '30s',
};
export default function () {
	  const userId =(__VU - 1) * 100 + __ITER + 1; // // userId 계산
  const concertOptionId = Math.floor((userId - 1) / 100) + 1; //Math.floor((userId - 1) / 1000) + 1; // concertOptionId 계산
	// 요청 헤더에 userId 포함
  let headers = {
    'Content-Type': 'application/json',
    'userId': userId.toString(),
  };

  // 요청 본문에 필요한 데이터를 포함
  let reservationRequestDTO = JSON.stringify({
    userId: userId,
    concertDt: "2024-07-16", // 고정된 날짜
    concertOptionId: concertOptionId,
    seatId: userId,
  });


  // 요청 보내기
  let reservationRes = http.post(`${BASE_URL}/concert/reservation`, reservationRequestDTO, {
    headers: headers,
  });

  // 응답 체크
  check(reservationRes, {
    'status is 200': (r) => r.status === 200,
    'response is not empty': (r) => r.body.length > 0,
  });

  // 대기 시간
  sleep(0);
}