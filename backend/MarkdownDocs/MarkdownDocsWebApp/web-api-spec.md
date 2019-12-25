

### Document`

```
interface Document {
    MetaInfo metaInfo;
    String content;
}

interface MetaInfo {	
    uuid Id;
    String title;
    String shareToken;
    Timestamp createdAt;
    Timestamp updatedAt;
}
```

### `GET /documents `

Получить мета-информацию о документах доступных пользователю.

 Response:

```
{
    [
       {
       		String Id;
       		String title;  
            String shareToken;
       		Timestamp updatedAt;
       		Timestamp createdAt;
       },
       ...
    ]
}
```

​    200  - Json содержащий список метаинформаций документов.
​    500 - если в результате выполнения запроса возникли ошибки.
​    403 - неизвестный пользователь.

### `GET /documents/{documentId}` 

Response:

```
{
    metaInfo: {
        String title;
        String content;
        DateTime updatedAt;
        DateTime createdAt;
    };
    content: string
},
```

Получить документ.

​    200  - Json содержащий документ
​    404 - если не удалось найти документ
​    500 - если в результате выполнения запроса возникли ошибки.
​    403 - неизвестный пользователь.

### `POST /documents `

Cоздаёт новый документ, в теле запроса находиться json описывающий документ.

Request

```
{    
   content: string // тут важно понимать один костыль, первая строка content это и есть title 
}
```

Response:
    204 - Ок, в ответ UUID созданного документа
    403 - неизвестный пользователь.
    500 - если в результате выполнения запроса возникли ошибки.

### `PUT /documents/{documentId}` 

Обновить документ.

Request:

```
{    
    content: string // тут важно понимать один костыль, первая строка content это и есть title 
}
```

Response:
    204 No content Просто фану
    404 Документ не найден.
    403 Неизвестный пользователь.
    500 Если в результате выполнения запроса возникли ошибки.

### `DELETE /documents/{documentId}` 

Удалить документ.

Response:

​    204 No content
​    404 документ не найден.
​    403 неизвестный пользователь.
​    500 если в результате выполнения запроса возникли ошибки.

### `POST /users`

Зарегистрировать нового пользователя.

Request:

```{
{
	login: string 
	password: string 
}
```

Response:
		204 - и редирект на окно аутентификации 

​		 409 - пользователь с таким логином уже существует

​         500 - если в результате обработки запроса возникли ошибки

### `POST /users/{login}`

Залогинится в сервис.

Request:

```
password: string
```



Response:

​	200 - в теле ответа 

```
{
	userId : UUID,	
	Auth : String
}
```

401 - не удалось аутентифицировать пользователя

500 - если в результате обработки запроса возникли ошибки



## `POST /share/{documentId}`

Включить document sharing и получить shareToken

200, Content-Type="text/plain" и в теле ответа

```
"shareToken"
```

404 - документ не найден

401 - не аутентифицирован 

403 - у пользователя нет прав на этот документ

500 - если в результате обработки запроса возникли ошибки



## `GET /share/{shareToken}`

200, Content-Type="text/plain" и в теле ответа

```
"document content"
```

404 - не найдена

500  -  если в результате обработки запроса возникли ошибки