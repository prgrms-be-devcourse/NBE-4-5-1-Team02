import type { NextConfig } from "next";

// 비동기 함수 rewrites를 통해, 클라이언트에서 /api/:path*로 요청이 들어오면 해당 요청을 내부적으로 http://localhost:8080/:path*로 전달
const nextConfig: NextConfig = {
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: 'http://localhost:8080/:path*'
      }
    ];
  }
};

export default nextConfig;
