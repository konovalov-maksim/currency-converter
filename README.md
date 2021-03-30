# Конвертер валют
Демонстрационное веб-приложение, позволяющее рассчитать соотношения денежных едениц по данным cbr.ru

![Screenshot_1](https://user-images.githubusercontent.com/49783652/88678786-782dfa00-d0f7-11ea-894c-22be61d3dc9a.png)

Реализовано:
- авторизация пользователя
- получение актуальных валют и курсов от cbr.ru
- механизм конвертации (+верификация данных)
- вывод списка ранее совершенных пользователем конвертаций (+фильтры, +пагинация)

### Сборка и запуск
Потребуются:
- Java 13
- PostgreSQL 12
- Maven

#### Подготовка БД
1. Создать базу данных converter
2. Выполнить скрипт coverter_postgres_dump для создания элементов БД и вставки начальных данных:
    > psql -U username -d converter -f coverter_postgres_dump
3. В файле /application.properties указать параметры подключения к БД: адрес, логин, пароль

#### Сборка
   > mvn clean package

#### Запуск
   > java --enable-preview -jar converter.jar

Стартовая страница приложения доступна по адресу localhost:8080. Доступы для входа:  
user1 password1  
user2 password2
