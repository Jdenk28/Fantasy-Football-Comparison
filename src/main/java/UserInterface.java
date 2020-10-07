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
    
    //Main ui that the user will interface with
    
    public void start() {
        readFile("proj_cbs");
        readFile("proj_nfl");
        readFile("proj_yahoo");

        System.out.println("Welcome to the Fantasy Football Comparison Program!");
        System.out.println("Input Players. Type \"End\" when all players have been input to receive comparison. Type \"Search\" at anytime for a list of players.");
        System.out.println("");
        
        while (true) {
            System.out.print("Enter Player (Full name): ");
            String input = scan.nextLine().toLowerCase();
            
            if (input.equals("end")) {
                break;
            }

            if (input.equals("search")) {
                search();
                continue;
            }
            
            String notOnList = "notOnList";
            for (Player player: this.players) {
                if (player.getName().equals(input)) {
                    this.comparePlayers.add(player);
                    notOnList = player.getName();
                }
            }
            
            if (notOnList.equals("notOnList")) {
                System.out.println("Invalid input. Try again.");
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
            
            for (Player player: this.players) {
                if (player.getNameCaps().equals(firstName + " " + lastName)) {
                    player.addProj(projection);
                }
            }
            
            i++;
        }
        
        this.list.clear();
    }
    
    //Code for the search function
    
    public void search() {
        System.out.print("Search by \"Position\" or \"Team\": ");
        String input = this.scan.nextLine().toLowerCase();
        
        if (input.equals("position")) {
            System.out.print("Which position? (QB, RB, WR, TE, K, DEF): ");
            String inputPos = this.scan.nextLine().toLowerCase();
            System.out.println("");
            
            for (Player player: this.players) {
                if (player.getPos().equals(inputPos)) {
                    System.out.println(player.getNameCaps());
                }
            }
            
            System.out.println("");
        }
        
        if(input.equals("team")) {
            System.out.print("Which team? (BAL, PHI, KC, NE, etc): ");
            String inputTeam = this.scan.nextLine().toLowerCase();
            System.out.println("");
            
            for (Player player: this.players) {
                if (player.getTeam().equals(inputTeam)) {
                    System.out.println(player.getNameCaps());
                }
            }
            
            System.out.println("");
        }
    }
    
    //Loops through entered players to find who has the highest average projection
    
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
        System.out.println(highestAvgPlayer.getNameCaps() + " has the highest average projection with " + highestAvg + " points.");
    }
}
