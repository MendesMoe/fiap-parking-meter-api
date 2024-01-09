# Escolha a imagem do RabbitMQ com a tag de gerenciamento
FROM rabbitmq:3.12-management

# Adicione o plugin de mensagens atrasadas
RUN apt-get update -y \
    && apt-get install -y wget \
    && wget https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v3.12.0/rabbitmq_delayed_message_exchange-3.12.0.ez \
    && mv rabbitmq_delayed_message_exchange-3.12.0.ez $RABBITMQ_HOME/plugins/ \
    && rabbitmq-plugins enable --offline rabbitmq_delayed_message_exchange

# Expose os ports padrão do RabbitMQ (5672 para amqp, 15672 para gerenciamento)
EXPOSE 5672 15672

#1 = docker build -t my-custom-rabbitmq .
#2 = docker run -d --name my-rabbitmq -p 5672:5672 -p 15672:15672 my-custom-rabbitmq
