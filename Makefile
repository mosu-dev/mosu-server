# 기본 변수 정의
COMPOSE = docker-compose
DOCKER = docker
PROJECT_NAME = mosu

# 환경별 공통 옵션
LOCAL_COMPOSE_FILE = docker-compose/docker-compose.local.yml
LOCAL_ENV_FILE = docker-compose/.env.local

PROD_COMPOSE_FILE = docker-compose/docker-compose.prod.yml
PROD_ENV_FILE = docker-compose/.env.prod

# 공통 실행 함수
define compose_up
	$(COMPOSE) -p $(PROJECT_NAME) -f $(1) --env-file $(2) up -d
endef

define compose_down
	$(COMPOSE) -p $(PROJECT_NAME) down
endef

define compose_ps
	$(COMPOSE) -p $(PROJECT_NAME) ps
endef

# 실행 (로컬)
up-local:
	$(call compose_up, $(LOCAL_COMPOSE_FILE), $(LOCAL_ENV_FILE))

# 실행 (운영)
up-prod:
	$(call compose_up, $(PROD_COMPOSE_FILE), $(PROD_ENV_FILE))

# 중지 및 컨테이너 제거
down:
	$(call compose_down)

# 상태 보기
ps:
	$(call compose_ps)

# 재시작
restart-local:
	$(call compose_down)
	$(call compose_up, $(LOCAL_COMPOSE_FILE), $(LOCAL_ENV_FILE))

restart-prod:
	$(call compose_down)
	$(call compose_up, $(PROD_COMPOSE_FILE), $(PROD_ENV_FILE))
