# dio-saladereuniao

Projeto de um gerenciador de salas de reunião utilizando Spring + Angular.
Criado durante o **Santander Bootcamp | Fullstack Developer**, em parceria com a [Digital Innovation One](https://web.digitalinnovation.one/) em 2021.

<div display="inline-block" align="center">

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

</div>


## Informações 📢

O projeto consiste em duas aplicações, separadas em dois repositórios. O projeto em Angular foi substituído por uma nova versão construída com Vue.js.

- API Rest para o back-end (este repositório)
- Nova aplicação front-end criada em Vue.js, disponível no repositório [saladereuniao-app](https://github.com/flwedu/saladereuniao-app)

Foi criado com o intuito de demonstrar o desenvolvimento de uma aplicação para o cadastro e visualização de salas de reunião.

## Tecnologias utilizadas 🔧

- Java 11
- Spring
- H2 Database Engine
- Lombok
- Gradle
- Swagger

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

Uma vez inicializada a aplicação, a documentação de cada um dos endpoints estará disponível em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## Recursos

[x] Listar todas as salas

[x] Recuperar dados de sala individualmente

[x] Salvar salas

[x] Atualizar dados de salas.

[x] Deletar salas

[x] Adicionar evento à uma sala

[x] Listar todos eventos de uma sala
