package com.topic;

import com.rabbitmq.client.*;

public class EmitUserCreatedTopic {
    private static final String EXCHANGE_NAME = "user_event";

    public static void main(String[] args) throws Exception {
        // Retrieve routing key and message from input or use defaults if not provided
        String routingKey = getRoutingKey(args);
        String message = getMessage(args);

        System.out.println(" [x] Dispatched '" + routingKey + "':'" + message + "'");

        // Configure connection properties
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Initialize exchange with topic type
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            // Send the message to the designated exchange
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
        }
    }

    // Extracts the routing key, defaults to "user.created" if none is given
    private static String getRoutingKey(String[] input) {
        return (input.length > 0) ? input[0] : "user.created";
    }

    // Constructs the message from input, defaults to a standard message if missing
    private static String getMessage(String[] input) {
        return (input.length > 1) ? concatenateStrings(input, " ", 1) : "New user has been created.";
    }

    // Combines an array of strings starting from a given position into one string
    private static String concatenateStrings(String[] parts, String delimiter, int start) {
        if (parts.length <= start) return "";
        StringBuilder message = new StringBuilder(parts[start]);
        for (int i = start + 1; i < parts.length; i++) {
            message.append(delimiter).append(parts[i]);
        }
        return message.toString();
    }
}
