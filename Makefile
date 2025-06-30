.PHONY: prod-up prod-down local-up local-down

# Target for bringing up the production environment
prod-up:
	@echo "Bringing up production environment..."
	docker compose -f docker-compose/docker-compose.prod.yml --env-file docker-compose/.env.prod up -d --build
	@echo "Production environment is up!"

# Target for bringing down the production environment
prod-down:
	@echo "Bringing down production environment..."
	docker compose -f docker-compose/docker-compose.prod.yml --env-file docker-compose/.env.prod down
	@echo "Production environment is down!"

# Target for bringing up the local development environment
# Assumes docker-compose/docker-compose.local.yml and docker-compose/.env.local exist
local-up:
	@echo "Bringing up local development environment..."
	docker compose -f docker-compose/docker-compose.local.yml --env-file docker-compose/.env.local up -d --build
	@echo "Local development environment is up!"

# Target for bringing down the local development environment
local-down:
	@echo "Bringing down local development environment..."
	docker compose -f docker-compose/docker-compose.local.yml --env-file docker-compose/.env.local down
	@echo "Local development environment is down!"

# Default target - shows available commands
help:
	@echo "Usage:"
	@echo "  make prod-up    - Build and start production services"
	@echo "  make prod-down  - Stop and remove production services"
	@echo "  make local-up   - Build and start local development services"
	@echo "  make local-down - Stop and remove local development services"
