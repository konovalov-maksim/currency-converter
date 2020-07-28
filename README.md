# Конвертер валют
Демонстрационное веб-приложение, позволяющее перевести одну денежную единицу в другую по данным cbr.ru

![Screenshot_1](https://user-images.githubusercontent.com/49783652/88678786-782dfa00-d0f7-11ea-894c-22be61d3dc9a.png)

Реализовано:
- авторизация пользователя
- получение актуальных валют и курсов от cbr.ru
- механизм конвертации
- список ранее совершенных пользователем конвертаций (+ фильтры, + пагинация)

### Сборка и запуск
Потребуются:
- JDK 13
- PostgreSQL 12
- Intellij Idea + Maven

#### Подготовка БД
1. Создать базу данных converter
2. Выполнить скрипт coverter_postgres_dump для создания элементов БД и вставки начальных данных. Пример команды запуска скрипта для Windows:
    > psql -U username -d converter -f C:\coverter_postgres_dump
3. В файле /application.properties указать параметры подключения к БД: адрес, логин, пароль

#### Сборка через Intellij Idea

##### Community Edition
Открыть проект, запустить командой Maven
   > mvn spring-boot:run -f pom.xml 

##### Ultimate edition
1. Открыть проект
2. В настройках проекта указать Projeсt SDK: 13, Project language level: 13 (Preview)
3. В настройках модуля converter указать Language level: 13 (Preview)
4. Создать конфигурацию запуска Spring Boot, указать Main class:
    > com.konovalov.converter.ConverterApplication

#### Запуск
Стартовая страница приложения доступна по адресу localhost:8080. Доступы для входа:  
user1 password1  
user2 password2

