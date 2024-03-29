# Parquímetro API

Este projeto é uma API desenvolvida com Java e Spring Boot, designada para gerenciar um sistema de parquímetros. O projeto foi construído como parte do Tech Challenge para a Pós-Graduação em Arquitetura JAVA. A API oferece uma série de funcionalidades para facilitar o gerenciamento de estacionamentos, incluindo registro de condutores, veículos, e formas de pagamento.

## Funcionalidades Principais

### Registro de Condutores e Veículos
A API permite que condutores se registrem no sistema, fornecendo dados pessoais como nome, endereço e informações de contato. Além disso, os condutores podem vincular vários veículos à sua conta, o que facilita o gerenciamento de múltiplos veículos em diferentes situações de estacionamento.

### Registro de Forma de Pagamento
Antes de utilizar o sistema, é necessário que o condutor registre sua forma de pagamento preferida. A API suporta várias formas de pagamento, incluindo cartões de crédito e débito, proporcionando flexibilidade e comodidade aos usuários.

### Gerenciamento de sessoes de estacionamento e envio de notificacoes
O utilizador pode iniciar uma sessao de estacionamento com o tempo pré-determinado ou livre. Ele podera receber notificacoes para acompanhar o tempo restante até o fim de sue sessao. As notificacoes serao enviadas por e-mail.

## Tecnologias Utilizadas

Este projeto foi desenvolvido com as seguintes tecnologias e bibliotecas:

- **Java 17**: Versão do Java utilizada no projeto.
- **Spring Boot**: Framework principal para a criação de aplicações Spring.
- **Spring Data MongoDB**: Para integração com bancos de dados MongoDB.
- **Spring Boot Starter Validation**: Para validação de dados das requisições.
- **Spring Boot DevTools**: Para desenvolvimento rápido com reinício automático.
- **Spring Boot Starter Web**: Para construção de aplicações web, usando o Spring MVC.
- **Lombok**: Para reduzir o código boilerplate, como getters e setters.
- **Spring Boot Starter Test**: Para testes e garantia de qualidade do código.
- **Spring Boot Starter Security**: Para autenticação e segurança da aplicação.
- **Java JWT (com.auth0)**: Para implementação de JWT (JSON Web Tokens) em Java.
- **Springdoc OpenAPI**: Para documentação da API REST com Swagger.
- **Spring Boot Starter AMQP**: Para mensageria com Advanced Message Queuing Protocol.
- **Jackson Datatype JSR310**: Para suporte ao Java 8 LocalDate/LocalDateTime no Jackson.
- **Jakarta XML Bind API**: Para manipulação de XML.
- **Spring Boot Starter Mail**: Para envio de e-mails.
- **Jakarta EE API**: Para APIs padrões da plataforma Jakarta EE.
- **Jakarta Validation API**: Para validação de dados baseada em anotações.

## Como Executar

Nos utilizamos Docker e criamos um dockerfile para gerenciar os containers.

Uma vez o projeto baixado do github e com docker desktop instalado na maquina, execute:
````shell
docker-compose up --build
````
Verifique que os tres containers foram baixados
````shell
docker ps
````
3 imagens devem aparecer : spring-app, mongodb e my-rabbitmq

Para ver o serviço rodando em uma interface, acessar a porta e entrar "guest" para usuario e senha
````shell
http://localhost:5672
````
Mais infos no dockerhub :
[https://hub.docker.com/_/rabbitmq]()
## Documentação

A documentação detalhada da API está acessível na URL:
[http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/index.html)

Um quadro MIRO regrupa varios dados criados e modificados durante a gestao do projeto:
[https://miro.com](https://miro.com/app/board/uXjVN_o6f1M=/)

## Segurança

As rotas "/login" e "/customer" não requerem token para uso. Abaixo estão os passos para o uso da API:

1. **Criação de um Customer**:
   - Utilize a rota POST `/customer` para criar um novo usuário fornecendo `login` e `password`.

2. **Autenticação para Recuperação do Token**:
   - Faça uma requisição na rota `/login` para autenticar e recuperar o token Bearer.

3. **Uso do Token**:
   - Utilize o token fornecido durante o login para acessar as demais rotas da API. O token deve ser incluído no cabeçalho da requisição.
    
4. Utilize o token (Baerer token) para criar um veiculo `/vehicle` e, em seguida, uma sessao de estacionamento `/session`.