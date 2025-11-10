Чтобы запустить приложение:

1. Копируешь репозиторий:
   ```bash
   git clone https://github.com/GGlebux/Construction.git
   ```

2. Скачиваешь .jar файл отсюда
   https://github.com/GGlebux/Construction/releases
   и закидываешь в корень проекта

3. Собираешь образ приложения (бэк + фронт) -  запускать в корне!
   ```bash
   docker build -t str_oy_kak .
   ```

4. Запускаешь приложение и дополнительные сервисы через docker compose
   ```bash
   docker compose -f ./src/main/docker/full.yml up
   ```

Админка откроется на http://localhost:8080/
Графана откроектся на http://localhost:3000/