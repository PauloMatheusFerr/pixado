📲 Pixado - API
Pixado é uma API desenvolvida em Spring Boot para facilitar a geração de QR Codes de pagamento via Pix, além de permitir a verificação de status e o gerenciamento de transações por chave Pix e cliente.

🔄 Suporte futuro a integrações com Gerencianet, Bacen, PicPay, Banco Inter, entre outros.

🚀 Tecnologias Utilizadas
Java 17+

Spring Boot (REST API)

Spring Data JPA

PostgreSQL

Docker (para o banco de dados)

Hibernate

Lombok (opcional)

Integração com certificados digitais (.p12)

📦 Funcionalidades
✅ Cadastro de usuários com chave Pix e credenciais bancárias

✅ Geração de QR Code dinâmico com payload e imagem base64

✅ Verificação do status de pagamento (ex: PAGO, PENDENTE)

🚧 Relatórios organizados por cliente e chave Pix (em desenvolvimento)

🚧 Integrações completas com bancos reais (Gerencianet, PicPay etc.)

📁 Estrutura de Diretórios
bash
Copiar
Editar
src/
├── controller/        # Endpoints da API
├── service/           # Regras de negócio
├── model/             # Entidades JPA
├── repository/        # Interfaces de acesso ao banco
├── dto/               # Objetos de transferência de dados
├── provider/          # Abstrações para provedores de Pix
└── config/            # Configurações globais
🔧 Como Executar Localmente
1. Clone o repositório
bash
Copiar
Editar
git clone https://github.com/xenobil/pixado-api.git
cd pixado-api
2. Inicie o banco de dados com Docker
bash
Copiar
Editar
docker-compose up -d
Banco disponível em localhost:5432 com:

Database: pixado

Usuário: postgres

Senha: postgres

3. Configure application.yml ou .properties
yaml
Copiar
Editar
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
4. Execute a aplicação
bash
Copiar
Editar
./mvnw spring-boot:run
🧪 Testes com Postman
Importe a coleção de testes:

📁 Pixado_API.postman_collection.json

📌 Exemplo de Requisição - Cadastro de Usuário
POST /api/usuarios

json
Copiar
Editar
{
  "nome": "João da Silva",
  "chavePix": "joao@email.com",
  "banco": "GERENCIANET",
  "clientId": "SEU_CLIENT_ID",
  "clientSecret": "SEU_CLIENT_SECRET",
  "caminhoCertificado": "/caminho/do/certificado.p12"
}
👨‍💻 Autor
Paulo Matheus Ferreira da Silva
📧 paulomatheusferr@gmail.com
🔗 LinkedIn
🐙 GitHub

📄 Licença
Este projeto está licenciado sob a MIT License.
