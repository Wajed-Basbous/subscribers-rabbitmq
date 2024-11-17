package com.topic;

import com.rabbitmq.client.*;

public class ReceiveEmailTopic {
    private static final String EXCHANGE_NAME = "user_event";

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: ReceiveEmailTopic [binding_key]...");
            System.exit(1);
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declare the exchange with type set to 'topic'
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();

        // Loop through provided routing keys and bind each to the queue
        for (String bindingKey : args) {
            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        }

        System.out.println(" [*] Listening for user-created events. Press CTRL+C to exit.");

        // Callback to handle received messages
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String routingKey = delivery.getEnvelope().getRoutingKey();
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Event received '" + routingKey + "':'" + message + "'");

            // Trigger specific actions based on routing key suffix
            if (routingKey.endsWith(".accounting")) {
                processAccountingEvent(message);
            } else if (routingKey.endsWith(".email")) {
                processEmailEvent(message);
            }
        };

        // Start consuming from the queue with auto-acknowledgment
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }

    // Method to process accounting-related messages
    private static void processAccountingEvent(String message) {
        System.out.println("[Accounting] Adding user to the accounting system: " + message);
    }

    // Method to process email-related messages
    private static void processEmailEvent(String message) {
        System.out.println("[Email] Sending a welcome email to: " + message);
    }
}
