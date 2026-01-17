import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 10 },
    { duration: '1m', target: 10 },
    { duration: '30s', target: 0 },
  ],
  thresholds: {
    http_req_duration: ['p(95)<2000'], // 95% of requests under 2s
    http_req_failed: ['rate<0.1'],
  },
};

const BASE_URL = 'https://iucat-library.onrender.com';

export default function () {
  // Test 1: Health Check
  let healthRes = http.get(`${BASE_URL}/actuator/health`);
  check(healthRes, {
    'health check status 200': (r) => r.status === 200,
    'health check response time < 500ms': (r) => r.timings.duration < 500,
  });
  
  sleep(1);
  
  // Test 2: Search Page
  let searchRes = http.get(`${BASE_URL}/search`);
  check(searchRes, {
    'search page status 200': (r) => r.status === 200,
    'search page has content': (r) => r.body.includes('Search for Books'),
  });
  
  sleep(1);
  
  // Test 3: Login Page
  let loginRes = http.get(`${BASE_URL}/login`);
  check(loginRes, {
    'login page status 200': (r) => r.status === 200,
    'login page has form': (r) => r.body.includes('Login'),
  });
  
  sleep(2);
}