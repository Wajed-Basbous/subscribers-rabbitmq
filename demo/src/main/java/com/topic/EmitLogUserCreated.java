package com.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONObject;

public class EmitLogUserCreated {

    private static final String EXCHANGE_NAME = "user_created_topic";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            // Routing key representing a user creation event
            String routingKey = getRouting(argv);
            // JSON formatted message for user data. It could be any other format but I will focus on JSON.
            String message = getJsonMessage(argv);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }

    private static String getRouting(String[] strings) {
        if (strings.length < 1)
            return "user.creation";
        return strings[0];
    }

    private static String getJsonMessage(String[] strings) {
        if (strings.length < 2)
            return "{\"status\":\"Error\",\"message\":\"Error creating user\"}";

        // Example JSON structure for user data
        JSONObject json = new JSONObject();
        json.put("username", strings[1]);
        json.put("email", strings.length > 2 ? strings[2] : "unknown@example.com");
        json.put("status", "User created successfully");

        return json.toString();
    }
}
