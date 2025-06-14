import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ProducerMsging {

    // Địa chỉ máy cài ActiveMQ
    private static String url = "failover://tcp://172.20.10.2:61616";
    
    // Tên queue
    private static String subject = "TESTQUEUE2";

    public static void main(String[] args) throws JMSException {
        // Kết nối đến ActiveMQ
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Tạo session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Tạo hàng đợi và producer
        Destination destination = session.createQueue(subject);
        MessageProducer producer = session.createProducer(destination);

        try {
            int msgTemp = 0;
            while (true) {
                msgTemp++;
                String msg = "TextMessage-" + msgTemp;
                TextMessage message = session.createTextMessage(msg);
                producer.send(message);
                System.out.println(">> " + msg + " has been sent.");
                Thread.sleep(1000); // Gửi 1s/lần
            }
        } catch (InterruptedException e) {
           
        } finally {
            // Đóng kết nối khi cần thiết
            connection.close();
        }
    }
}
