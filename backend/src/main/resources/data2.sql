-- MEMBER 테이블 (7명)
INSERT INTO `MEMBER` VALUES('u1', 'alice@example.com', now(), now());
INSERT INTO `MEMBER` VALUES('u2', 'bob@example.com', now(), now());
INSERT INTO `MEMBER` VALUES('u3', 'charlie@example.com', now(), now());
INSERT INTO `MEMBER` VALUES('u4', 'diana@example.com', now(), now());
INSERT INTO `MEMBER` VALUES('u5', 'edward@example.com', now(), now());
INSERT INTO `MEMBER` VALUES('u6', 'fiona@example.com', now(), now());
INSERT INTO `MEMBER` VALUES('u7', 'george@example.com', now(), now());

-- ORDERS 테이블
-- u1의 주문 (5건)
-- 오늘 기준 전날 16:00:00에 만들어진 order를 생성
INSERT INTO `ORDERS` VALUES('o1', 'u1', DATE_SUB(CONCAT(CURDATE(), ' 16:00:00')),  DATE_SUB(CONCAT(CURDATE(), ' 16:00:00')), 15000, 'Seoul, Gangnam-gu', 101010, 'PENDING');
INSERT INTO `ORDERS` VALUES('o2', 'u1', DATE_SUB(CONCAT(CURDATE(), ' 16:00:00')),  DATE_SUB(CONCAT(CURDATE(), ' 16:00:00')), 22000, 'Seoul, Jongno-gu', 101011, 'PENDING');
INSERT INTO `ORDERS` VALUES('o3', 'u1', DATE_SUB(CONCAT(CURDATE(), ' 16:00:00')),  DATE_SUB(CONCAT(CURDATE(), ' 16:00:00')), 18000, 'Seoul, Mapo-gu', 101012, 'SHIPPED');
INSERT INTO `ORDERS` VALUES('o4', 'u1', DATE_SUB(CONCAT(CURDATE(), ' 16:00:00')),  DATE_SUB(CONCAT(CURDATE(), ' 16:00:00')), 30000, 'Seoul, Yongsan-gu', 101013, 'DELIVERED');
INSERT INTO `ORDERS` VALUES('o5', 'u1', DATE_SUB(CONCAT(CURDATE(), ' 16:00:00')),  DATE_SUB(CONCAT(CURDATE(), ' 16:00:00')), 25000, 'Seoul, Seocho-gu', 101014, 'CANCELLED');

-- u2의 주문 (4건)
INSERT INTO `ORDERS` VALUES('o6', 'u2', now(), now(), 17000, 'Busan, Haeundae', 202010, 'PENDING');
INSERT INTO `ORDERS` VALUES('o7', 'u2', now(), now(), 19000, 'Busan, Suyeong-gu', 202011, 'PENDING');
INSERT INTO `ORDERS` VALUES('o8', 'u2', now(), now(), 21000, 'Busan, Dongnae-gu', 202012, 'SHIPPED');
INSERT INTO `ORDERS` VALUES('o9', 'u2', now(), now(), 16000, 'Busan, Nam-gu', 202013, 'DELIVERED');

-- u3의 주문 (6건)
INSERT INTO `ORDERS` VALUES('o10', 'u3', now(), now(), 28000, 'Incheon, Bupyeong', 303010, 'PENDING');
INSERT INTO `ORDERS` VALUES('o11', 'u3', now(), now(), 26000, 'Incheon, Namdong', 303011, 'PENDING');
INSERT INTO `ORDERS` VALUES('o12', 'u3', now(), now(), 32000, 'Incheon, Yeonsu', 303012, 'SHIPPED');
INSERT INTO `ORDERS` VALUES('o13', 'u3', now(), now(), 35000, 'Incheon, Jung', 303013, 'DELIVERED');
INSERT INTO `ORDERS` VALUES('o14', 'u3', now(), now(), 30000, 'Incheon, Dong', 303014, 'PENDING');
INSERT INTO `ORDERS` VALUES('o15', 'u3', now(), now(), 27000, 'Incheon, Bupyeong', 303015, 'PENDING');

-- u4의 주문 (4건)
INSERT INTO `ORDERS` VALUES('o16', 'u4', now(), now(), 24000, 'Daegu, Suseong-gu', 404010, 'PENDING');
INSERT INTO `ORDERS` VALUES('o17', 'u4', now(), now(), 26000, 'Daegu, Nam-gu', 404011, 'PENDING');
INSERT INTO `ORDERS` VALUES('o18', 'u4', now(), now(), 23000, 'Daegu, Dalseo-gu', 404012, 'SHIPPED');
INSERT INTO `ORDERS` VALUES('o19', 'u4', now(), now(), 25000, 'Daegu, Jung-gu', 404013, 'DELIVERED');

-- u5의 주문 (5건)
INSERT INTO `ORDERS` VALUES('o20', 'u5', now(), now(), 31000, 'Daejeon, Yuseong-gu', 505010, 'PENDING');
INSERT INTO `ORDERS` VALUES('o21', 'u5', now(), now(), 29000, 'Daejeon, Seo-gu', 505011, 'PENDING');
INSERT INTO `ORDERS` VALUES('o22', 'u5', now(), now(), 33000, 'Daejeon, Jung-gu', 505012, 'SHIPPED');
INSERT INTO `ORDERS` VALUES('o23', 'u5', now(), now(), 30000, 'Daejeon, Dong-gu', 505013, 'DELIVERED');
INSERT INTO `ORDERS` VALUES('o24', 'u5', now(), now(), 32000, 'Daejeon, Daedeok-gu', 505014, 'PENDING');

-- u6의 주문 (4건)
INSERT INTO `ORDERS` VALUES('o25', 'u6', now(), now(), 20000, 'Gwangju, Buk-gu', 606010, 'PENDING');
INSERT INTO `ORDERS` VALUES('o26', 'u6', now(), now(), 22000, 'Gwangju, Dong-gu', 606011, 'PENDING');
INSERT INTO `ORDERS` VALUES('o27', 'u6', now(), now(), 24000, 'Gwangju, Nam-gu', 606012, 'SHIPPED');
INSERT INTO `ORDERS` VALUES('o28', 'u6', now(), now(), 21000, 'Gwangju, Seo-gu', 606013, 'DELIVERED');

-- u7의 주문 (6건)
INSERT INTO `ORDERS` VALUES('o29', 'u7', now(), now(), 35000, 'Ulsan, Nam-gu', 707010, 'PENDING');
INSERT INTO `ORDERS` VALUES('o30', 'u7', now(), now(), 33000, 'Ulsan, Dong-gu', 707011, 'PENDING');
INSERT INTO `ORDERS` VALUES('o31', 'u7', now(), now(), 36000, 'Ulsan, Jung-gu', 707012, 'SHIPPED');
INSERT INTO `ORDERS` VALUES('o32', 'u7', now(), now(), 37000, 'Ulsan, Buk-gu', 707013, 'DELIVERED');
INSERT INTO `ORDERS` VALUES('o33', 'u7', now(), now(), 34000, 'Ulsan, Nam-gu', 707014, 'PENDING');
INSERT INTO `ORDERS` VALUES('o34', 'u7', now(), now(), 38000, 'Ulsan, Dong-gu', 707015, 'PENDING');

-- PRODUCT 테이블 (25개 상품)
-- coffeeBean 카테고리
INSERT INTO `PRODUCT` VALUES('p1', 'Coffee 1', 'coffeeBean', 1200, '싱글 오리진 커피 원두', '/img/coffee1.png');
INSERT INTO `PRODUCT` VALUES('p2', 'Coffee 2', 'coffeeBean', 1300, '에스프레소용 커피 원두', '/img/coffee2.png');
INSERT INTO `PRODUCT` VALUES('p3', 'Coffee 3', 'coffeeBean', 1250, '다크 로스트 커피 원두', '/img/coffee3.png');
INSERT INTO `PRODUCT` VALUES('p4', 'Coffee 4', 'coffeeBean', 1400, '라이트 로스트 커피 원두', '/img/coffee4.png');
INSERT INTO `PRODUCT` VALUES('p5', 'Coffee 5', 'coffeeBean', 1350, '블렌드 커피 원두', '/img/coffee5.png');

-- tea 카테고리
INSERT INTO `PRODUCT` VALUES('p6', 'Tea 1', 'tea', 800, '녹차 티백', '/img/tea1.png');
INSERT INTO `PRODUCT` VALUES('p7', 'Tea 2', 'tea', 900, '홍차 티백', '/img/tea2.png');
INSERT INTO `PRODUCT` VALUES('p8', 'Tea 3', 'tea', 850, '우롱차 티백', '/img/tea3.png');
INSERT INTO `PRODUCT` VALUES('p9', 'Tea 4', 'tea', 950, '허브차 티백', '/img/tea4.png');
INSERT INTO `PRODUCT` VALUES('p10', 'Tea 5', 'tea', 800, '과일차 티백', '/img/tea5.png');

-- snack 카테고리
INSERT INTO `PRODUCT` VALUES('p11', 'Snack 1', 'snack', 1500, '바삭한 감자칩', '/img/snack1.png');
INSERT INTO `PRODUCT` VALUES('p12', 'Snack 2', 'snack', 1600, '고소한 견과류 믹스', '/img/snack2.png');
INSERT INTO `PRODUCT` VALUES('p13', 'Snack 3', 'snack', 1700, '건과일 믹스', '/img/snack3.png');
INSERT INTO `PRODUCT` VALUES('p14', 'Snack 4', 'snack', 1550, '치즈 크래커', '/img/snack4.png');
INSERT INTO `PRODUCT` VALUES('p15', 'Snack 5', 'snack', 1650, '초콜릿 바', '/img/snack5.png');

-- beverage 카테고리
INSERT INTO `PRODUCT` VALUES('p16', 'Beverage 1', 'beverage', 2000, '탄산수', '/img/beverage1.png');
INSERT INTO `PRODUCT` VALUES('p17', 'Beverage 2', 'beverage', 2200, '과일 주스', '/img/beverage2.png');
INSERT INTO `PRODUCT` VALUES('p18', 'Beverage 3', 'beverage', 2100, '에너지 드링크', '/img/beverage3.png');
INSERT INTO `PRODUCT` VALUES('p19', 'Beverage 4', 'beverage', 2300, '스포츠 드링크', '/img/beverage4.png');
INSERT INTO `PRODUCT` VALUES('p20', 'Beverage 5', 'beverage', 2400, '생과일 주스', '/img/beverage5.png');

-- bakery 카테고리
INSERT INTO `PRODUCT` VALUES('p21', 'Bakery 1', 'bakery', 3000, '크루아상', '/img/bakery1.png');
INSERT INTO `PRODUCT` VALUES('p22', 'Bakery 2', 'bakery', 3200, '마들렌', '/img/bakery2.png');
INSERT INTO `PRODUCT` VALUES('p23', 'Bakery 3', 'bakery', 3100, '도넛', '/img/bakery3.png');
INSERT INTO `PRODUCT` VALUES('p24', 'Bakery 4', 'bakery', 3300, '머핀', '/img/bakery4.png');
INSERT INTO `PRODUCT` VALUES('p25', 'Bakery 5', 'bakery', 3400, '쿠키', '/img/bakery5.png');

-- PRODUCT_ORDER_RELATION 테이블
-- u1의 주문
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p1', 'o1');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p2', 'o1');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p3', 'o2');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p4', 'o2');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p5', 'o3');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p6', 'o3');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p7', 'o4');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p8', 'o4');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p9', 'o5');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p10', 'o5');

-- u2의 주문
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p11', 'o6');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p12', 'o6');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p13', 'o7');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p14', 'o7');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p15', 'o8');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p16', 'o8');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p17', 'o9');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p18', 'o9');

-- u3의 주문
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p19', 'o10');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p20', 'o10');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p21', 'o11');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p22', 'o11');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p23', 'o12');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p24', 'o12');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p25', 'o13');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p1', 'o13');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p2', 'o14');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p3', 'o14');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p4', 'o15');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p5', 'o15');

-- u4의 주문
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p6', 'o16');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p7', 'o16');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p8', 'o17');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p9', 'o17');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p10', 'o18');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p11', 'o18');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p12', 'o19');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p13', 'o19');

-- u5의 주문
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p14', 'o20');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p15', 'o20');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p16', 'o21');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p17', 'o21');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p18', 'o22');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p19', 'o22');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p20', 'o23');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p21', 'o23');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p22', 'o24');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p23', 'o24');

-- u6의 주문
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p24', 'o25');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p25', 'o25');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p1', 'o26');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p2', 'o26');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p3', 'o27');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p4', 'o27');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p5', 'o28');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p6', 'o28');

-- u7의 주문
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p7', 'o29');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p8', 'o29');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p9', 'o30');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p10', 'o30');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p11', 'o31');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p12', 'o31');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p13', 'o32');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p14', 'o32');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p15', 'o33');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p16', 'o33');

INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p17', 'o34');
INSERT INTO `PRODUCT_ORDER_RELATION` VALUES('p18', 'o34');