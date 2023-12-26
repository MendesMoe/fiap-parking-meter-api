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
- Flyway para migrações de banco de dados
- Lombok para redução de boilerplate em modelos Java
- Spring Boot Starter Security para autenticação e segurança
- Java JWT para implementação de autenticação com tokens JWT

## Como Executar

Instruções detalhadas sobre como configurar e executar o projeto localmente serão fornecidas em breve.

--- (falar do docker, etc)

Banco de dados MongoDB com Docker sem usuario nem senha: 
````shell
docker pull mongo
docker run -d --name mongodb -p 27017:27017 mongo
````
Verifique que a sua imagem baixou e execute
````shell
docker ps
````
Verifique o nome do container e pode lancer : 
````shell
mongo --host localhost --port 27017
````
Quando a conexao for feita você pode visualisar as tables e criar a 'parquimetro'
````shell
show databases
use parquimetro
````