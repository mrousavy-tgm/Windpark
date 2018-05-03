import org.apache.activemq.ActiveMQConnection;

public class Statics {
    public static final String USER = ActiveMQConnection.DEFAULT_USER;
    public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    public static final String URL = "failover://tcp://192.168.152.148:61616";
    public static final String SUBJECT = "Windfarm";
    public static final int INTERVAL = 1000;
    public static final int SEND_COUNT = 10;
}
