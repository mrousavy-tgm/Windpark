import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;

@XmlRootElement
public class Windfarm implements AutoCloseable {
    @XmlElement
    public int id = 0;
    @XmlElement(name = "windmill")
    public ArrayList<Windmill> mills = new ArrayList<Windmill>();

    @XmlTransient
    private Session session = null;
    @XmlTransient
    private Connection connection = null;
    @XmlTransient
    private MessageProducer producer = null;


    public Windfarm() {
        this(0);
    }

    public Windfarm(int id) {
        this.id = id;
    }

    public static void main(String[] args) throws Exception {
        int id;
        try {
            System.out.println("Starting Windfarm..");
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Windfarm ID argument!");
            return;
        }

        try (Windfarm farm = new Windfarm(id)) {
            for (int i = 0; i < 5; i++) {
                farm.mills.add(new Windmill(farm, i));
            }

            farm.connect();
            // send XML every half second, 10 times
            while (true) {
                System.out.println("Sending XML..");
                farm.send();
                Thread.sleep(Statics.INTERVAL);
            }
        } catch (JMSException ex) {
            System.out.println("[Windfarm] error: " + ex);
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Windfarm finished!");
    }

    public String buildXml() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Windfarm.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(this, sw);
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void connect() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(Statics.USER, Statics.PASSWORD, Statics.URL);
        connection = factory.createConnection();
        connection.start();

        // Create the session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(Statics.SUBJECT );

        // Create the producer.
        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    public void send() throws JMSException {
        TextMessage message = session.createTextMessage(buildXml());
        System.out.println(message.getText());
        producer.send(message);
    }

    public void stop() throws JMSException {
        connection.close();
        producer.close();
        session.close();
    }

    @Override
    public boolean equals(Object obj) {
        Windfarm other = (obj instanceof Windfarm ? (Windfarm) obj : null);
        return other != null && id == other.id;
    }

    @Override
    public void close() throws Exception {
        stop();
    }
}
