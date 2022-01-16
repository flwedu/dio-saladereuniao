# dio-saladereuniao

Projeto de um gerenciador de salas de reunião utilizando Spring + Angular.
Criado durante o **Santander Bootcamp | Fullstack Developer**, em parceria com a [Digital Innovation One](https://web.digitalinnovation.one/) em 2021.

## Informações 📢

O projeto consiste em duas aplicações, separadas em dois repositórios.

- API Rest para o back-end (este repositório)
- Aplicação Angular para o front-end, disponível em https://github.com/flwedu/client-sala-reuniao.

Foi criado com o intuito de demonstrar o desenvolvimento de uma aplicação para o cadastro e visualização de salas de reunião.

## Tecnologias utilizadas 🔧

### Back-End

- Java 11
- Spring
- H2 Database Engine
- Lombok
- Gradle

### Front-End

- Angular 12
- Typescript
- HTML
- CSS
- Bootstrap 5
- Material Icons

## Rodando a aplicação 🚀

Fazendo build e baixando dependências com gradle:

```bash
gradle build
```

Rodando a aplicação:

```bash
gradle bootRun
```

A API Rest irá rodar em `http://localhost:8080/api/v1`. Os dados serão persistidos em memória, através da H2 Database Engine.

A aplicação possui um arquivo com um código SQL de inicialização e população da database localizado
em `src/main/resources/data.sql`.

Qualquer problema ao iniciar tal base de dados, inicialize um banco de dados vazio, apagando a linha
`spring.jpa.hibernate.ddl-auto=none` do arquivo `src/main/resources/application.properties`. Dessa forma, o próprio Spring
se encarregará da inicialização.

## Requisições aceitas

Lista de requisições possíveis e suas respostas:

- `GET` para `/api/v1/rooms` : Lista de todas as salas disponíveis;
- `GET` para `/api/v1/rooms/{id}` : Informações sobre a sala com o `{id}`. Exemplo de retorno obtido:

```JSON
{
  "id": 1,
  "name": "Sala 1",
  "date": "09/08/2021",
  "startHour": "12:00",
  "endHour": "18:00"
}
```

- `POST` para `/api/v1/rooms`: Salva os dados. Formato de dados aceito:

```JSON
{
	"name": "Sala 2",
	"date": "09/08/2021",
	"startHour": "12:00",
	"endHour": "18:00"
}
```

- `PUT`para `/api/v1/rooms/{id}`: Atualiza a sala requisitada com os dados no _request body_.
- `DELETE`para `/api/v1/rooms/{id}`: Deleta uma sala com o mesmo `{id}`.

## Recursos

[ ✔ ] Listar todas as salas

[ ✔ ] Recuperar dados de sala individualmente

[ ✔ ] Salvar salas

[ ✔ ] Atualizar dados de salas.

[ ✔ ] Deletar salas
