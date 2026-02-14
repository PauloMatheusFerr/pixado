# 📲 Pixado - API

API do projeto **Pixado**: um sistema que permite gerar QR Code de pagamento via Pix, verificar status de transações e organizar relatórios por chave Pix e cliente.  
Desenvolvido com **Spring Boot** e integração futura com serviços como **Gerencianet**, **Pix via Bacen**, **PicPay** etc.

---

## 🚀 Tecnologias

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker (para o banco de dados)
- RESTful API

---

## 📦 Funcionalidades

- ✅ Cadastro de usuários com chave Pix e credenciais bancárias
- ✅ Geração de QR Code de pagamento
- ✅ Verificação de status de pagamento
- 🚧 Organização de relatórios por cliente e chave Pix (em desenvolvimento)
- 🚧 Integração real com provedores de Pix (em desenvolvimento)

---

## 📂 Estrutura do Projeto

```
src/
├── controller/
│   └── UsuarioController.java
├── service/
│   └── UsuarioService.java
├── model/
│   └── Usuario.java
├── repository/
│   └── UsuarioRepository.java
├── dto/
│   ├── UsuarioRequestDTO.java
│   └── UsuarioResponseDTO.java
└── ...
```

---

## 🔧 Como rodar

### 1. Clone o projeto
```bash
git clone https://github.com/seu-usuario/pixado-api.git
cd pixado-api
```

### 2. Suba o banco com Docker
```bash
docker-compose up -d
```

> Isso sobe um PostgreSQL em `localhost:5432` com:
> - Banco: `pixado`
> - Usuário: `postgres`
> - Senha: `postgres`

### 3. Configure o `application.yml`
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pixado
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

### 4. Rode a aplicação
```bash
./mvnw spring-boot:run
```

---

## 📖 Documentação da API (Swagger)

Após rodar a aplicação, acesse a documentação interativa Swagger UI:

🔗 **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

🔗 **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

A documentação inclui:
- ✅ Descrição detalhada de todos os endpoints
- ✅ Exemplos de requisição e resposta
- ✅ Schemas dos objetos
- ✅ Interface interativa para testar a API

---

## 🧪 Teste com Postman

Importe o arquivo:
📁 [`Pixado_API.postman_collection.json`](Pixado_API.postman_collection.json)

---

## ✍️ Exemplo de cadastro de usuário

**POST** `/api/usuarios`
```json
{
  "nome": "João da Silva",
  "chavePix": "joao@email.com",
  "banco": "GERENCIANET",
  "clientId": "SEU_CLIENT_ID",
  "clientSecret": "SEU_CLIENT_SECRET",
  "caminhoCertificado": "/caminho/certificado.p12"
}
```

---

## 👨‍💻 Autor

Paulo Matheus Ferreira da Silva  
📧 paulomatheusferr@gmail.com  
🔗 [LinkedIn](https://www.linkedin.com/in/paulo-matheus-ferreira-566ba8124/)  
🐙 [GitHub](https://github.com/xenobil)

---

## 📄 Licença

MIT License
