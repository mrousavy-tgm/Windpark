import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.StringWriter;
import java.util.ArrayList;

@XmlRootElement
public class Windfarm {
    @XmlElement
    public int id = 0;
    @XmlElement(name = "windmill")
    public ArrayList<Windmill> mills = new ArrayList<>();

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
        Destination destination = session.createTopic(Statics.SUBJECT);

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

    public static void main(String[] args) {
        try {
            System.out.println("Starting Windfarm..");

            Windfarm farm = new Windfarm();
            farm.connect();
            // send XML every half second, 10 times
            for (int i = 0; i < 10; i++) {
                System.out.println("Sending XML..");
                farm.send();
                Thread.sleep(500);
            }
            // stop service
            farm.stop();

            System.out.println("Windfarm finished!");
        } catch (JMSException ex) {
            System.out.println("[Windfarm] error: " + ex);
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
