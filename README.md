# MegaFlix API

API REST de catálogo de filmes e usuários, desenvolvida com Java e Spring Boot como projeto de estudos para praticar backend com autenticação e arquitetura em camadas.

## Funcionalidades implementadas

- CRUD de usuários ✔️
- CRUD de filmes ✔️
- CRUD de streamings ✔️
- Playlist por usuário ✔️
- Marcar filme como assistido/não assistido ✔️
- Favoritar/desfavoritar filmes ✔️
- Controle de acesso por roles (ADMIN / usuário autenticado) ✔️
- Autenticação com Spring Security (HTTP Basic) ✔️
- Migrations de banco com Flyway ✔️
- Arquitetura em camadas (Controller → Service → Repository) ✔️

## Design Patterns usados

- Data Transfer Objects (DTOs)
- Repository Pattern
- Builder

## Tecnologias usadas

- Java
- Spring Boot (Security, Data JPA, Web)
- Hibernate
- MySQL
- Flyway

## Endpoints principais

### Usuários
| Método | Rota | Acesso |
|--------|------|--------|
| POST | /megaflix/users | Público |
| GET | /megaflix/users | Público |
| GET | /megaflix/users/{id} | Público |
| PUT | /megaflix/users/{id} | Público |
| DELETE | /megaflix/users/{id} | ADMIN |
| POST | /megaflix/users/login | Público |
| PUT | /megaflix/users/{userId}/password | Autenticado |

### Filmes
| Método | Rota | Acesso |
|--------|------|--------|
| POST | /megaflix/movies | ADMIN |
| GET | /megaflix/movies | Público |
| GET | /megaflix/movies/{id} | Público |
| PUT | /megaflix/movies/{id} | ADMIN |
| DELETE | /megaflix/movies/{id} | ADMIN |

### Streamings
| Método | Rota | Acesso |
|--------|------|--------|
| POST | /megaflix/streamings | ADMIN |
| GET | /megaflix/streamings | Público |
| PUT | /megaflix/streamings/{id} | ADMIN |
| DELETE | /megaflix/streamings/{id} | ADMIN |

### Playlist
| Método | Rota | Acesso |
|--------|------|--------|
| POST | /megaflix/playlist/{userId}/{movieId} | Autenticado |
| PATCH | .../watched | Autenticado |
| PATCH | .../unwatched | Autenticado |
| PATCH | .../favorite | Autenticado |
| PATCH | .../unfavorite | Autenticado |

## Como rodar localmente

git clone https://github.com/kellyson04/megaflix-api

Configure o banco no application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/megaflix
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

./mvnw spring-boot:run
