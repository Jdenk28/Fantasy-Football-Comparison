import java.util.Scanner;
import java.nio.file.Paths;
import java.util.ArrayList;


public class UserInterface {
    private ArrayList<Player> players;
    private ArrayList<String> list;
    private Scanner scan;
    private ArrayList<Player> comparePlayers;
    
    public UserInterface() {
        this.players = new ArrayList<>();
        this.scan = new Scanner(System.in);
        this.list = new ArrayList<>();
        this.comparePlayers = new ArrayList<>();
    }
    
    public void start() {
        readFile("proj_cbs");
        readFile("proj_nfl");
        readFile("proj_yahoo");

        System.out.println("Welcome to the Fantasy Football Comparison Program!");
        System.out.println("Input Players. Type \"End\" when all players have been input to receive comparison. Type \"Search\" at anytime for a list of players.");
        System.out.println("");
        
        while (true) {
            System.out.print("Enter Player (Full name): ");
            String input = scan.nextLine();
            
            if (input.equals("End")) {
                break;
            }

            if (input.equals("Search")) {
                search();
                continue;
            }
            
            String notOnList = "notOnList";

            int i = 0;
            while (i < this.players.size()) {
                if (this.players.get(i).getName().equals(input)) {
                    this.comparePlayers.add(this.players.get(i));
                    notOnList = this.players.get(i).getName();
                }
                i++;
            }
            
            if (notOnList.equals("notOnList")) {
                System.out.println("This player doesn't have sufficient data for a comparison");
            }
        }
        
        highestAvg();
    }
    
    //Reads and parses file, and adds Player objects to an ArrayList. Also adds projections to the respective players.
    
    public void readFile(String file) {
        try (Scanner scanner = new Scanner(Paths.get(file))) {
            
            while (scanner.hasNextLine()) {
                
                String line = scanner.nextLine();
                this.list.add(line);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } 
        
        int i = 0;
        while (i < this.list.size()) {
            String[] parts = this.list.get(i).split(",");

            String firstName = parts[0];
            String lastName = parts[1];
            String pos = parts[2];
            String team = parts[3];
            double projection = Double.valueOf(parts[4]);

            Player newPlayer = new Player(firstName, lastName, pos, team);
            
            if (!this.players.contains(newPlayer)) {
                this.players.add(newPlayer);
            }

            int j = 0;
            while (j < this.players.size()) {
                if (this.players.get(j).getName().equals(firstName + " " + lastName)) {
                    this.players.get(j).addProj(projection);
                }
                j++;
            }
            i++;
        }
        this.list.clear();
    }
    
    public void search() {
        System.out.print("Search by Position or Team: ");
        String input = this.scan.nextLine();
        
        if (input.equals("Position")) {
            System.out.print("Which position? (QB, RB, WR, TE, K, DEF): ");
            String inputPos = this.scan.nextLine();
            System.out.println("");
            
            int i = 0;
            while (i < this.players.size()) {
                if (this.players.get(i).getPos().equals(inputPos)) {
                    System.out.println(this.players.get(i).getName());
                }
                i++;
            }
            System.out.println("");
        }
        
        if(input.equals("Team")) {
            System.out.print("Which team? (PIT, PHI, KC, NE, etc): ");
            String inputTeam = this.scan.nextLine();
            System.out.println("");
            
            int i = 0;
            while (i < this.players.size()) {
                if (this.players.get(i).getTeam().equals(inputTeam)) {
                    System.out.println(this.players.get(i).getName());
                }
                i++;
            }
            System.out.println("");
        }
    }
    
    public void highestAvg() {
        System.out.println("");
        
        int i = 0;
        double highestAvg = 0;
        Player highestAvgPlayer = new Player();
        
        while (i < this.comparePlayers.size()) {

            if (this.comparePlayers.get(i).projectionAvg() > highestAvg) {
                highestAvg = this.comparePlayers.get(i).projectionAvg();
                highestAvgPlayer = this.comparePlayers.get(i);
            }
            System.out.println(this.comparePlayers.get(i));
            i++;
        }
        System.out.println("");
        System.out.println(highestAvgPlayer.getName() + " has the highest average projection with " + highestAvg + " points.");
    }
}
