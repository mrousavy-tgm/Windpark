import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
            for (int i = 0; i < Statics.SEND_COUNT; i++) {
                hq.receive();
            }
            // stop the service
            hq.stop();

            System.out.println("Headquarter finished!");
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    public void receive() throws JMSException, IOException {
        // Start receiving
        TextMessage message = (TextMessage) consumer.receive();
        if (message != null) {
            String text = message.getText();

            List<String> lines = Arrays.asList(text);
            Path file = Paths.get("windpark.xml");
            Files.write(file, lines, Charset.forName("UTF-8"));

            System.out.println("[Headquarter] Message received and saved to windpark.xml");
            message.acknowledge();
        }
    }

    public void stop() throws JMSException {
        connection.close();
        consumer.close();
        session.close();
    }
}
