# hey_ticket (OpenTicket)

티켓 오픈 일정을 확인하고, 거래까지 이어갈 수 있는 안드로이드 애플리케이션입니다.

## 기능

1. 오픈일 순 티켓 리스트 조회
2. 티켓 검색
3. 티켓 거래 기능 (추후 기능 추가 예정)

## 아키텍처

Clean Architecture 기반의 멀티 모듈 구조로 구성되어 있습니다.

- **presentation** — UI 레이어 (View, ViewModel, Fragment 등). `domain`, `data` 모듈에 의존
- **domain** — 비즈니스 로직 레이어 (UseCase, Repository 인터페이스, Model)
- **data** — 데이터 레이어 (API Service, DB, Repository 구현체, Mapper)

## 기술 스택

- Kotlin
- Hilt (의존성 주입)
- Jetpack Navigation (Safe Args)
- Jetpack Compose / View Binding / Data Binding
- Paging

## 빌드

```bash
./gradlew build
```
