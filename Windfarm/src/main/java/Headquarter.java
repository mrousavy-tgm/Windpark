import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;

public class Headquarter {
    public ArrayList<Windfarm> windfarms = new ArrayList<Windfarm>();

    private Session session = null;
    private Connection connection = null;
    private MessageConsumer consumer = null;

    public Headquarter() {
    }

    public static void main(String[] args) {
        try {
            System.out.println("Starting Headquarter..");

            Headquarter hq = new Headquarter();
            hq.connect();
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

    public void connect() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(Statics.USER, Statics.PASSWORD, Statics.URL);
        connection = factory.createConnection();
        connection.start();

        // Create the session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(Statics.SUBJECT);

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
