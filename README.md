# JDBC Movie Theatre App

Tема: "Система Кинотеатр" 

ТЗ: Интернет витрина маленького Кинотеатра. В нем есть Расписание показа фильмов на все 7 дней недели 
с 9:00 до 22:00 (начало последнего фильма). Незарегистрированный пользователь может видеть: расписание, свободные
места в зале, возможность зарегистрироваться. Зарегистрированный пользователь должен быть в состоянии выкупить 
билет на выбранное место. Администратор может: внести в расписание новый фильм, отменить фильм,
просматривать посещаемость зала.
Уровни доступа: аноним, USER, ADMIN.

## Технологии
- DB - MySql
- Java version up 8.
- Maven

## Установка и запуск проекта

1. Clone project with "git clone" command
2. Install plugin for Lombok library
3. Run create_schema.sql from resources/script folder
4. Run insert_into_schema.sql from resources/script folder
5. Set required number of additional days to correct the movie session dates in movie_session table in update_schema.sql file from resources/script folder and run script.
5. Update DB username and password in src/main/webapp/META_INF/context.xml (and in file db.properties if you want use connections without pool)
6. Run in terminal command "mvn clean tomcat7:run"
7. Go to link localhost:8080/theatre

## Доступный функционал

- #### Уровень доступа - любой:

1.  Стартовая страница (HomeCommand) +
2.  Расписание сеансов (ScheduleCommand) - уникальное view для администратора ++
3.  Просмотр заполненности зала на определенный сеанс (MovieSessionCommand) +
4.  Вход (LoginCommand Post/Get) ++
5.  Регистрация (SignUpCommand Post/Get) ++
6.  Смена языка (LanguageCommand) +

- #### Уровень доступа - любой пользователь вошедший в систему:

7.  Возможномть выйти из системы (LogOutCommand) +

- #### Уровень доступа - USER:

8.  Аккаунт со списком актуальных купленных сеансов (AccountCommand) +
9.  При просмотре зала возможность купить билеты (BookTicketsCommand) +

- #### Уровень доступа - ADMIN:

10.  Просмотр зала без интерактива (MovieSessionCommand) +
11.  Возможность добавить/удалить новый фильм в систему (PostCommand) -
12.  Возможность прикрепить/открепить фильм к определенному дню (PostCommand) -
13.  Возможность добавить/удалить новый сеанс для фильма (зал + время показа) (PostCommand) -
