import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private String name;
    private String team;
    private String pos;
    private ArrayList<Double> projection;
    
    public Player() {
        
    }
    
    public Player(String firstName, String lastName, String pos, String team) {
        this.name = firstName + " " + lastName;
        this.team = team;
        this.pos = pos;
        this.projection = new ArrayList<>();
    }
    
    public void addProj(double projection) {
        this.projection.add(projection);
    }
    
    public String getName() {
        return this.name.toLowerCase();
    }
    
    public String getNameCaps() {
        return this.name;
    }
    
    public String getTeam() {
        return this.team.toLowerCase();
    }
    
    public String getPos() {
        return this.pos.toLowerCase();
    }
    
    //Avgeraged projection for this player
    
    public double projectionAvg() {
        int i = 0;
        double sum = 0;
        int total = 0;
        
        while (i < this.projection.size()) {
            sum = sum + this.projection.get(i);
            i++;
            total++;
        }
        
        return 1.0 * sum / total;
    }
    
    public String toString() {
        return this.name + " " + projectionAvg();
    }

    //The following methods prevent duplicate Objects from being added to the Player ArrayList
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.team, other.team)) {
            return false;
        }
        if (!Objects.equals(this.pos, other.pos)) {
            return false;
        }
        return true;
    }

}
