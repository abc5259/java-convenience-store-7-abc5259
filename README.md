# java-convenience-store-precourse

### 구현할 기능 목록

입력 
- [ ] 구매할 상품명과 수량을 입력받는다.
  - (예: [사이다-2],[감자칩-1])
  - [사이다-2],[감자칩-1],[사이다-3] 과 같이 같은 상품이 존재할 수 있다.
- [ ] 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException를 발생시키고, "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
  - [ ] 예외1: 구매할 상품과 수량 형식이 올바르지 않은 경우
  - [ ] 예외2: 존재하지 않는 상품을 입력한 경우
  - [ ] 예외3: 구매 수량이 재고 수량을 초과한 경우
  - [ ] 예외4: 기타 잘못된 입력의 경우

출력
- [ ] 환영 인사를 출력한다.
- [ ] 현재 가지고 있는 상품을 모두 출력한다.
  - 재고가 0개라면 재고 없음을 출력한다.
- [ ] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우, 혜택에 대한 안내 메시지를 출력한다.
- [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메시지를 출력한다.
- [ ] 멤버십 할인 적용 여부를 확인하기 위해 안내 문구를 출력한다.
- [ ] 영수증을 출력한다.

Product
- [x] 상품은 이름, 가격, 수량, 프로모션으로 이루어져 있다.
- [ ] 프로모션이 없는 상품도 존재한다.
- [x] 상품의 재고는 음수가 될 수 없다.

Store
- [x] 여러 상품을 한번에 등록할 수 있다.
- [ ] 상품을 구매시 등록된 상품의 재고보다 많은 상품은 구매할 수 없다.
- [ ] 같은 이름을 가졌지만 프로모션이 다른 상품이 존재할 수 있다.
- [ ] 구매자는 상품을 구입할 수 있다. 
  - [ ] 구매자가 가지고 있는 상품 개수보다 많이 들고올 경우 예외처리한다.

Order
- [ ] 사용자는 Store에 주문을 할 수 있다.
- [ ] 주문은 상품 이름, 상품 개수로 이루어진 OrderItem으로 구성되고 여러개의 OrderItem을 주문할 수 있다.

Promotion
- [ ] 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행됨
- [ ] 프로모션 적용이 가능한 상품인지 계산한다.
  - 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용
- [ ] 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
- [ ] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우 그 수량만큼 추가해야 하는 상품 개수를 계산한다.
  - 예시1. 1+1 상품인데 고객이 1개만 들고온 경우
  - 예시2. 2+1 상품인데 고객이 2개만 들고온 경우
  - 예시3. 3+1 상품인데 고객이 3개만 들고온 경우
- [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 해택 없이 결제해야 하는 상품 개수를 계산한다.
  - 예시1. 프로모션 상품이 현재 7개이고 혜택이 1+1, 일반 상품의 개수가 10개일때 고객이 10개의 상품을 구입하려는 경우 -> 4개를 정가로 결제할건지 확인 
  - 예시2. 프로모션 상품이 현재 7개이고 혜택이 1+1인데 고객이 7개를 구매하는 경우 -> 생각..

멤버십 할인
- [ ] 멤버십 회원은 프로모션 미적용 금액의 30%를 할인할 수 있다.
  - 프로모션 미적용 금액이란 콜라를 2+1행사일때 4개의 콜라가 있을때 콜라를 4개 구입한다면 2+1에 걸리는 3개는 프로모션 적용이 된거고 나머지 하나는 프로모션 적용이 안된것임 
  - 따라서 3개는 멤버십 할인할때 금액에 속하지 않는거로 생각됨 
- [ ] 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
- [ ] 멤버십 할인의 최대 한도는 8,000원이다.

영수증 출력 
- [ ] 구매자가 구매한 상품 내역을 알고있다.
- [ ] 프로모션에 따라 무료로 제공된 증정 상품 목록을 알고있다.
- [ ] 상품의 총 수량과 총 금액을 알고있다.
- [ ] 프로모션에 의해 할인된 금액을 알고있다.
- [ ] 멤버십에 의해 추가로 할인된 금액을 알고있다.
- [ ] 최종 결제 금액을 출력한다.

### 프로그래밍 요구 사항
- [ ]else 예약어를 쓰지 않는다.
  - else를 쓰지 말라고 하니 switch/case로 구현하는 경우가 있는데 switch/case도 허용하지 않는다.
- [ ] Java Enum을 적용하여 프로그램을 구현한다.
- [ ] 구현한 기능에 대한 단위 테스트를 작성한다. 단, UI(System.out, System.in, Scanner) 로직은 제외한다.
- [ ] 함수(또는 메서드)의 길이가 10라인을 넘어가지 않도록 구현한다.
- [ ] 함수(또는 메서드)가 한 가지 일만 잘 하도록 구현한다.
- [ ] 입출력을 담당하는 클래스를 별도로 구현한다.
- [ ] InputView, OutputView 클래스를 참고하여 입출력 클래스를 구현한다.
  - 클래스 이름, 메소드 반환 유형, 시그니처 등은 자유롭게 수정할 수 있다.

### 라이브러리
- [ ] camp.nextstep.edu.missionutils에서 제공하는 DateTimes 및 Console API를 사용하여 구현한다.
  - [ ] 현재 날짜와 시간을 가져오려면 camp.nextstep.edu.missionutils.DateTimes의 now()를 활용한다.
- [ ] 사용자가 입력하는 값은 camp.nextstep.edu.missionutils.Console의 readLine()을 활용한다.

## 실행 결과 예시
```markdown
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 5개
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[콜라-3],[에너지바-5]

멤버십 할인을 받으시겠습니까? (Y/N)
Y 

===========W 편의점=============
상품명		수량	금액
콜라		3 	3,000
에너지바 		5 	10,000
===========증	정=============
콜라		1
==============================
총구매액		8	13,000
행사할인			-1,000
멤버십할인			-3,000
내실돈			 9,000

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
Y

안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 7개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 재고 없음
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[콜라-10]

현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
Y

멤버십 할인을 받으시겠습니까? (Y/N)
N

===========W 편의점=============
상품명		수량	금액
콜라		10 	10,000
===========증	정=============
콜라		2
==============================
총구매액		10	10,000
행사할인			-2,000
멤버십할인			-0
내실돈			 8,000

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
Y

안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 재고 없음 탄산2+1
- 콜라 1,000원 7개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 재고 없음
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[오렌지주스-1]

현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
Y

멤버십 할인을 받으시겠습니까? (Y/N)
Y

===========W 편의점=============
상품명		수량	금액
오렌지주스		2 	3,600
===========증	정=============
오렌지주스		1
==============================
총구매액		2	3,600
행사할인			-1,800
멤버십할인			-0
내실돈			 1,800

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
N
```
