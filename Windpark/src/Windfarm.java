import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Windfarm {
    public int id;
    @XmlElement(name = "windmill")
    public ArrayList<Windmill> mills = new ArrayList<>();


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

    @Override
    public boolean equals(Object obj) {
        Windfarm other = (obj instanceof Windfarm ? (Windfarm) obj : null);
        return other != null && id == other.id;
    }
}
