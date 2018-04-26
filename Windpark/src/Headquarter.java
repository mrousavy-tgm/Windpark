import java.util.ArrayList;
import java.util.List;

public class Headquarter {
    private ArrayList<Windfarm> _windfarms = new ArrayList<>();

    public Headquarter() {}

    public void addFarm(Windfarm windfarm) {
        _windfarms.add(windfarm);
    }

    public void removeFarm(Windfarm windfarm) {
        _windfarms.remove(windfarm);
    }

    public List<Windfarm> getFarms() {
        return _windfarms;
    }

    public static void main(String[] args){
        System.out.println("Starting Headquarter..");
        Windfarm farm = new Windfarm(1);
        farm.mills.add(new Windmill(farm, 1));
        String xml = farm.buildXml();
        System.out.println(xml);
        System.out.println("Headquarter finished!");
    }
}
