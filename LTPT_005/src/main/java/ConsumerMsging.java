import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ConsumerMsging {
    // URL of the JMS server
    private static String url = "failover://tcp://172.20.10.2:61616";

    // Name of the queue we will receive messages from
    private static String subject = "TESTQUEUE4";

    public static void main(String[] args) throws JMSException {
        // Getting JMS connection from the server
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Creating session for sending messages
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Getting the queue 'TESTQUEUE1'
        Destination destination = session.createQueue(subject);

        // MessageConsumer is used for receiving (consuming) messages
        MessageConsumer consumer = session.createConsumer(destination);

        while (true) {
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Received message = " + textMessage.getText());
            }
        }

        // connection.close(); // Chưa gọi đóng kết nối trong khi loop chạy mãi
    }
}
