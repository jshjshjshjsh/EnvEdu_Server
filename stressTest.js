import http from 'k6/http';
import { check, sleep, group } from 'k6';
import { SharedArray } from 'k6/data';

// ============================================================
// 1. 테스트 설정 (3가지 타겟 동시 타격)
// ============================================================
export const options = {
    scenarios: {
        // [Target 1] N+1 문제 유발 (교사의 조회 활동)
        n_plus_one_attack: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '30s', target: 40 }, // 교사 40명이 동시에 조회
                { duration: '1m', target: 40 },
                { duration: '30s', target: 0 },
            ],
            exec: 'teacherFlow',
        },

        // [Target 2] 메모리(OOM) 공격 (대량 데이터 조회)
        memory_attack: {
            executor: 'constant-vus',
            vus: 10, // 소수 정예 10명이 거대한 데이터를 계속 요청
            duration: '2m',
            exec: 'heavyDataFlow',
        },

        // [Target 3] CPU 공격 (폴더 재조립)
        cpu_attack: {
            executor: 'constant-vus',
            vus: 20, // 20명이 뚱뚱한 폴더를 계속 조회
            duration: '2m',
            exec: 'folderFlow',
        },
    },
    thresholds: {
        http_req_failed: ['rate<0.01'], // 에러율 1% 미만 허용
        http_req_duration: ['p(95)<1000'], // 1초 넘어가면 실패로 간주 (성능 저하 감지용)
    },
};

const BASE_URL = 'http://localhost:8080';

// ============================================================
// 2. 테스트 계정 데이터
// ============================================================
// 교사 계정 (N+1 테스트용 - 강의실과 학생이 많이 연결되어 있어야 함)
const teachers = new SharedArray('teachers', function () {
    return [
        { username: 'Educator1', password: '1234' }, // InitDB에 있는 교사
    ];
});

// 학생 계정 (데이터 조회용)
const students = new SharedArray('students', function () {
    return [
        { username: 'Student1', password: '1234' },
    ];
});

// ============================================================
// 3. 시나리오 구현
// ============================================================

// [Target 1] 교사: 강의실 목록 & 기기 목록 조회 (N+1)
export function teacherFlow() {
    const user = teachers[Math.floor(Math.random() * teachers.length)];
    const params = { headers: { 'Content-Type': 'application/json' } };

    // 로그인
    const loginRes = http.post(`${BASE_URL}/login`, JSON.stringify(user), params);
    if (!check(loginRes, { 'Teacher login': (r) => r.status === 200 })) return;

    group('N+1 Query Attack', () => {
        // 강의실 목록 조회 (계층 구조 조회 N+1 발생 예상)
        const classroomRes = http.get(`${BASE_URL}/dataLiteracy/classroom/mine`, params);
        check(classroomRes, { 'Classroom List': (r) => r.status === 200 });

        // 학생 기기 목록 조회 (In절/루프 쿼리 문제 예상)
        const deviceRes = http.get(`${BASE_URL}/seed/device`, params);
        check(deviceRes, { 'Device List': (r) => r.status === 200 });
    });

    sleep(1);
}

// [Target 2] 학생: 대량 Seed 데이터 조회 (메모리 뻥튀기)
export function heavyDataFlow() {
    const user = students[Math.floor(Math.random() * students.length)];
    const params = { headers: { 'Content-Type': 'application/json' } };

    const loginRes = http.post(`${BASE_URL}/login`, JSON.stringify(user), params);
    if (!check(loginRes, { 'Student login': (r) => r.status === 200 })) return;

    // 1년치 데이터 조회 -> extendSeedData가 3배로 불림
    const fetchParams = `username=${user.username}&startDate=2024-01-01T00:00:00&endDate=2025-12-31T23:59:59`;

    const res = http.get(`${BASE_URL}/seed/fetch?${fetchParams}`, params);

    check(res, {
        'Heavy Data Fetch': (r) => r.status === 200,
        'Response Time < 2s': (r) => r.timings.duration < 2000 // 2초 안에 오는지 확인
    });

    sleep(3); // 서버 숨 쉴 시간 줌
}

// [Target 3] 학생: 데이터 폴더 조회 (객체 재조립 CPU 부하)
export function folderFlow() {
    const user = students[Math.floor(Math.random() * students.length)];
    const params = { headers: { 'Content-Type': 'application/json' } };

    const loginRes = http.post(`${BASE_URL}/login`, JSON.stringify(user), params);
    if (!check(loginRes, { 'Login': (r) => r.status === 200 })) return;

    // 폴더 목록 조회 후 첫 번째 폴더 ID 획득
    const listRes = http.get(`${BASE_URL}/datafolder/list`, params);
    let folderId = null;
    try {
        const folders = listRes.json();
        if (folders && folders.length > 0) folderId = folders[0].id;
    } catch (e) {}

    if (folderId) {
        // 폴더 내 아이템 상세 조회 (reassemble 로직 수행)
        const itemsRes = http.get(`${BASE_URL}/datafolder/items?id=${folderId}`, params);
        check(itemsRes, { 'Folder Reassembly': (r) => r.status === 200 });
    }

    sleep(1);
}