# Тестовое задание

### Предусловия

1. Необходим предустановленный postgres.
2. В postgres должна быть создана база данных applications_db. В случае использования другого наименования необходимо внести правки в конфигурацию `application.yml`.

### Спецификация на Swagger

После запуска приложения спецификация доступна по адресу http://localhost:8080/swagger-ui/index.html


### Имеющиеся пользователи в системе


| username:password    | role          |
|----------------------|---------------|
| `user1:password`     | ROLE_USER     |
| `user2:password`     | ROLE_USER     |
| `user3:password`     | ROLE_USER     |
| `operator1:password` | ROLE_OPERATOR |
| `operator2:password` | ROLE_OPERATOR |
| `operator3:password` | ROLE_OPERATOR |
| `admin1:password`    | ROLE_ADMIN    |


