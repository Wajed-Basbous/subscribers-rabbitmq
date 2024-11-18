package com.topic;

import com.rabbitmq.client.*;
import org.json.JSONObject;

public class AccountingUserSubscriber {

    private static final String EXCHANGE_NAME = "user_created_topic";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();

        // Bind the queue with the routing key for user creation
        if (argv.length < 1) {
            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
            System.exit(1);
        }

        for (String bindingKey : argv) {
            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        }

        System.out.println(" [*] Waiting for user creation messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received in Accounting System: '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");

            try {
                // Parse the message as JSON and extract the email field
                JSONObject json = new JSONObject(message);
                if (json.has("email")) {
                    String email = json.getString("email");
                    System.out.println(" [x] Processed user email: " + email);
                } else {
                    System.out.println(" [x] No email found in the message.");
                }
            } catch (Exception e) {
                System.err.println(" [!] Failed to parse message as JSON: " + e.getMessage());
            }
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
