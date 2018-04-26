import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Headquarter {
    private Session session = null;
    private Connection connection = null;
    private MessageConsumer consumer = null;

    public static void main(String[] args) {
        try {
            System.out.println("Starting Headquarter..");
            int id = Integer.parseInt(args[0]);

            Headquarter hq = new Headquarter();
            hq.connect(id);
            // receive a message 10 times in total
            for (int i = 0; i < 10; i++) {
                hq.receive();
            }
            // stop the service
            hq.stop();

            System.out.println("Headquarter finished!");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void connect(int id) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(Statics.USER, Statics.PASSWORD, Statics.URL);
        connection = factory.createConnection();
        connection.start();

        // Create the session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(Statics.SUBJECT + id);

        // Create the consumer
        consumer = session.createConsumer(destination);
    }

    public void receive() throws JMSException {
        // Start receiving
        TextMessage message = (TextMessage) consumer.receive();
        if (message != null) {
            System.out.println("[Headquarter] Message received: " + message.getText());
            message.acknowledge();
        }
    }

    public void stop() throws JMSException {
        connection.close();
        consumer.close();
        session.close();
    }
}
