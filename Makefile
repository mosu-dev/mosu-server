.PHONY: prod-up prod-down local-up local-down

# 프로덕션 환경을 실행하는 명령어
prod-up:
	@echo "빌드 중..."
	./gradlew build -x test
	@echo "빌드 완료..."
	@echo "프로덕션 환경을 실행 중..."
	docker compose -f docker-compose/docker-compose.prod.yml --env-file docker-compose/.env.prod up -d --build
	@echo "프로덕션 환경이 실행되었습니다!"

# 프로덕션 환경을 중지하는 명령어
prod-down:
	@echo "프로덕션 환경을 중지 중..."
	docker compose -f docker-compose/docker-compose.prod.yml --env-file docker-compose/.env.prod down
	@echo "프로덕션 환경이 중지되었습니다!"

# 로컬 개발 환경을 실행하는 명령어
# docker-compose/docker-compose.local.yml 및 docker-compose/.env.local 파일이 존재해야 함
local-up:
	@echo "빌드 중..."
	./gradlew build -x test
	@echo "빌드 완료..."
	@echo "로컬 개발 환경을 실행 중..."
	docker compose -f docker-compose/docker-compose.local.yml --env-file docker-compose/.env.local up -d --build
	@echo "로컬 개발 환경이 실행되었습니다!"

# 로컬 개발 환경을 중지하는 명령어
local-down:
	@echo "로컬 개발 환경을 중지 중..."
	docker compose -f docker-compose/docker-compose.local.yml --env-file docker-compose/.env.local down
	@echo "로컬 개발 환경이 중지되었습니다!"

# 기본 명령어 - 사용 가능한 명령어 출력
help:
	@echo "사용법:"
	@echo "  make prod-up    - 프로덕션 서비스 빌드 및 실행"
	@echo "  make prod-down  - 프로덕션 서비스 중지 및 제거"
	@echo "  make local-up   - 로컬 개발 서비스 빌드 및 실행"
	@echo "  make local-down - 로컬 개발 서비스 중지 및 제거"
