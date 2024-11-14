# Спасение Хогвартса (с помощью MongoDb)
В Хогвартсе настали темные времени. Долорес Амбридж захватила пост директора. 
Не все ученики Хогвартса согласны мириться с этим. Отряд Дамблдора готовит заговор, чтобы свергнуть Амбридж с ее поста и вернуть Дамблдора в замок.

А получится у них это или нет, будет зависить от магии и немного от того, 
как вы будете работать с MongoDb!

Создатель сервиса hogwarts https://github.com/noisegrind3r 

Создатель проекта hogwartsApiTests https://github.com/nekirilova

## Запуск проекта

```
docker-compose build
docker-compose up
```

Сервис доступен по адресу http://localhost:8006

### Эндпоинты:

#### Для работы с пользователями
GET http://localhost:8006/users/ - получить всех пользователей

GET http://localhost:8006/users/:id - получить пользователя по его айди

POST http://localhost:8006/users/ - создать пользователя

PATCH http://localhost:8006/users/:id - отредактировать пользователя

DELETE http://localhost:8006/users/:id - удалить пользователя

POST http://localhost:8006/constants/populate - удалить всех и создать пользователей по умолчанию


#### Для работы с константами

GET http://localhost:8006/constants/ - получить все константы

GET http://localhost:8006/constants/:id - получить константу по ее айди

POST http://localhost:8006/constants/ - создать константу

PATCH http://localhost:8006/constants/:id - отредактировать константу

DELETE http://localhost:8006/constants/:id - удалить константу

POST http://localhost:8006/constants/populate - удалить все и создать константы по умолчанию

#### Логические методы

PUT http://localhost:8006/logic/student/hide/ - Если параметр isLightOn = false, проставляет всем студентам признак isHidden = true (прячет студентов)

PUT http://localhost:8006/logic/student/catch/ - Если у студентов признак isHidden = false, то Филч ловит всех студентов. Если студенты спрятались, то ловит одного случайного

GET http://localhost:8006/logic/student/list/ - Зелье правды. В зависимости от параметра veritaserum заставляет выдать либо всех членов отряда Дамблдора, либо половину, либо никого.

POST http://localhost:8006/logic/sendOwl - Отправляет сову со списком "бунтавщиков" на адрес, указанный в параметре owlAdress с ожиданием owlTimeout и возвращает хороший либо плохой ответ

POST http://localhost:8006/logic/cast - Кастует заклинание, которое может вернуть Дамблдора в Хогвартс. Если параметр owlTimeout = 2, то времени слишком мало и заклинание не успеет сработать. Если 6, то успеет.


#### Доступные константы: 

**isLightOn** - выключен ли свет ('true', 'false')
Значение по умолчанию - false

**veritaserum** - зелье правды. Доступные значения 'EVERYTHING', 'HALF', 'NOTHING'
Значение по умолчанию - EVERYTHING

**owlAdress** - адрес, на который отправляется сова. Доступные значения 
'http://magic-ministry.com/', 'http://phoenix-order.com/'
Значение по умолчанию - http://magic-ministry.com/ 

**owlTimeout** - таймаут ожидания совы с ответом в секундах. Доступные значения - 2 и 6
Значение по умолчанию - 2

**owlAddressMinistryResponse** - ответ из Министерства Магии, если  owlAdress = http://magic-ministry.com/
Значение по умолчанию - Bad response. Можно поменять на любое другое.

**owlPhoenixOrderResponse** - ответ из Ордена Феникса, если  owlAdress =  'http://phoenix-order.com/', надо что-то написать
Значение по умолчанию - Good response. Можно поменять на любое другое.

