import http from 'k6/http';
import { check, sleep } from 'k6';  // check 함수 임포트

// 전역 변수로 설정할 기본값들
const BASE_URL = 'http://localhost:8080';
const TOKEN = 'your_token_here';
const MAX_USER_ID = 50;

export let options = {
    vus: 100,
    duration: '30s',
};

export default function () {
    // 각 VU에 고유한 userId를 할당
    const userId = (__VU - 1) * 100 + __ITER + 1;

    // 요청 본문에 필요한 데이터를 포함
    let paymentRequestDTO = JSON.stringify({
        userId: userId,
        chargeAmount: 100000, // 충전 금액
    });

    // 요청 헤더에 userId 포함
    let headers = {
        'Content-Type': 'application/json',
        'userId': userId.toString(),
    };

    // GET 요청 사용
    let paymentChargeRes = http.post(`${BASE_URL}/payment/${userId}/balance/charge`,paymentRequestDTO, {
        headers: headers,
    });

    console.log(JSON.stringify(paymentChargeRes));
    check(paymentChargeRes, {
        'status is 200': (r) => r.status === 200,
        'response is not empty': (r) => r.body.length > 0,
    });

    sleep(1);
}