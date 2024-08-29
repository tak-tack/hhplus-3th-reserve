import http from 'k6/http';
import { check, sleep } from 'k6';

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
    // VU 인덱스와 MAX_USER_ID를 조합하여 고유한 userId 생성
    const userId = (__VU - 1) * 100 + __ITER + 1;

    // 요청 본문에 필요한 데이터를 포함합니다
    let requestPayload = JSON.stringify({
        // 요청 데이터 (예시로 token 포함)
        token: TOKEN,
    });

    // 요청 헤더에 userId 포함
    let headers = {
        'Content-Type': 'application/json',
        'userId': userId.toString(),
    };

    let availabilityRes = http.post(`${BASE_URL}/concert/availabilityConcertList`, requestPayload, {
        headers: headers,
    });

    //console.log(JSON.stringify(availabilityRes));
    check(availabilityRes, {
        'status is 200': (r) => r.status === 200,
        'response is not empty': (r) => r.body.length > 0,
    });

    sleep(1);
}