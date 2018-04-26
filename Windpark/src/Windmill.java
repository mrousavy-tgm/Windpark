import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.StringWriter;

@XmlRootElement
public class Windmill {
    public enum SpeedUnit {
        KMH,
        MPH
    }

    public enum PowerUnit {
        KILO_WATT,
        MEGA_WATT,
        GIGA_WATT
    }

    @XmlTransient
    public Windfarm farm = null;
    @XmlElement
    public int id = 0;
    @XmlElement
    public double windSpeed = 0.0;
    @XmlElement
    public SpeedUnit speedUnit = SpeedUnit.KMH;
    @XmlElement
    public double powerOutput = 0.0;
    @XmlElement
    public PowerUnit powerUnit = PowerUnit.MEGA_WATT;

    public Windmill() {
        this(null, 0);
    }

    public Windmill(Windfarm farm, int id) {
        this.farm = farm;
        this.id = id;
    }

    public String buildXml() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Windmill.class);
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
        Windmill other = (obj instanceof Windmill ? (Windmill) obj : null);
        return other != null && id == other.id && farm.id == other.farm.id;
    }
}
