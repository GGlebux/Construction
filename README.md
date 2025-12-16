Чтобы запустить приложение:

1. Копируешь репозиторий:
   ```bash
   git clone https://github.com/GGlebux/Construction.git
   ```

2. Заходишь в папку docker
   ```bash
   cd docker/
   ```

3. Копируешь/создаешь файл .env и заполняешь. Пример example.env:
   ```env
   MAIL_USERNAME=some-mail@gmail.com
   MAIL_PASSWORD=csrtffsxcbtfgeqpdc
   JWT_SECRET=YjUyYmRkMmFkOTRhOWM0YzczNDk2NjNTYwODljODY5ZWRhNDRWNjMDU2YWUzNTkzMjExZTMzMmFiNDhiYmU=
   ```


4. Запускаешь приложение и дополнительные сервисы через docker compose
   ```bash
   docker compose  up
   ```

- API доступен по адресу http://localhost:8080/api/
- Grafana доступна по адресу на http://localhost:3000/