# Parquímetro API

Este projeto é uma API desenvolvida com Java e Spring Boot, designada para gerenciar um sistema de parquímetros. O projeto foi construído como parte do Tech Challenge para a Pós-Graduação em Arquitetura JAVA. A API oferece uma série de funcionalidades para facilitar o gerenciamento de estacionamentos, incluindo registro de condutores, veículos, e formas de pagamento.

## Funcionalidades Principais

### Registro de Condutores e Veículos
A API permite que condutores se registrem no sistema, fornecendo dados pessoais como nome, endereço e informações de contato. Além disso, os condutores podem vincular vários veículos à sua conta, o que facilita o gerenciamento de múltiplos veículos em diferentes situações de estacionamento.

### Registro de Forma de Pagamento
Antes de utilizar o sistema, é necessário que o condutor registre sua forma de pagamento preferida. A API suporta várias formas de pagamento, incluindo cartões de crédito e débito, proporcionando flexibilidade e comodidade aos usuários.

## Tecnologias Utilizadas

Este projeto foi desenvolvido utilizando:

- Java 17
- Spring Boot
- Mongodb Connector
- Spring Boot Starter Validation
- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- Lombok para redução de boilerplate em modelos Java
- Spring Boot Starter Security para autenticação e segurança
- Java JWT para implementação de autenticação com tokens JWT
- SpringDoc para a criacao da documentacao da API

## Como Executar

Banco de dados MongoDB com Docker sem usuario nem senha: 
````shell
docker pull mongo
docker run -d --name mongodb -p 27017:27017 mongo
````
Verifique que a sua imagem baixou e execute
````shell
docker ps
````
Verifique o nome do container e pode lancar : 
````shell
mongo --host localhost --port 27017
````
Quando a conexao for feita você pode visualisar as tables e criar a 'parquimetro'
````shell
show databases
use parquimetro
````
## Documentação

A documentação detalhada da API está acessível na URL:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Segurança

As rotas "/login" e "/customer" não requerem token para uso. Abaixo estão os passos para o uso da API:

1. **Criação de um Customer**:
    - Utilize a rota POST `/customer` para criar um novo usuário fornecendo `login` e `password`.

2. **Autenticação para Recuperação do Token**:
    - Faça uma requisição na rota `/login` para autenticar e recuperar o token Bearer.

3. **Uso do Token**:
    - Utilize o token fornecido durante o login para acessar as demais rotas da API. O token deve ser incluído no cabeçalho da requisição.
    