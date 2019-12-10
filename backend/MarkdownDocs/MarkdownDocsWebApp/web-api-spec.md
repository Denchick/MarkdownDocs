### `Document`

```
interface Document {
    MetaInfo metaInfo;
    String content;
}

interface MetaInfo {
    uuid author;
    String title;
    DateTime createdAt;
    DateTime updatedAt;
}
```

### `GET /documents `

Получить мета-информацию о документах доступных пользователю.

 Response:

```
{
    [
       {
       		String documentId;
       		String title;
       		// желательно, когда последний раз обновлен
       		DateTime updatedAt;
       		DateTime createdAt;
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
    metaInfo: {
        String title;
        String content;
        DateTime updatedAt;
        DateTime createdAt;
    };
    content: string
}
```

Response:
    204 - Ок, в ответ пустое тело
    403 - неизвестный пользователь.
    500 - если в результате выполнения запроса возникли ошибки.

### `PUT /documents/{documentId}` 

Обновить документ.

Request:

```
{
    metaInfo: {
        String title;
        String content;
        DateTime updatedAt;
        DateTime createdAt;
    };
    content: string
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