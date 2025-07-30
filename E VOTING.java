import java.util.*;

class Candidate {
    private String name;
    private int votes;

    public Candidate(String name) {
        this.name = name;
        this.votes = 0;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    public void addVote() {
        votes++;
    }
}

class Voter {
    private String voterId;
    private boolean hasVoted;

    public Voter(String voterId) {
        this.voterId = voterId;
        this.hasVoted = false;
    }

    public String getVoterId() {
        return voterId;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void vote() {
        hasVoted = true;
    }
}

class ElectionSystem {
    private List<Candidate> candidates = new ArrayList<>();
    private Map<String, Voter> voters = new HashMap<>();

    public void addCandidate(String name) {
        candidates.add(new Candidate(name));
    }

    public void registerVoter(String voterId) {
        if (!voters.containsKey(voterId)) {
            voters.put(voterId, new Voter(voterId));
            System.out.println("✅ Voter registered successfully.");
        } else {
            System.out.println("⚠️ Voter already registered.");
        }
    }

    public void castVote(String voterId, int candidateIndex) {
        Voter voter = voters.get(voterId);
        if (voter == null) {
            System.out.println("❌ Voter not registered.");
            return;
        }

        if (voter.hasVoted()) {
            System.out.println("⚠️ Voter has already voted.");
            return;
        }

        if (candidateIndex < 0 || candidateIndex >= candidates.size()) {
            System.out.println("❌ Invalid candidate number.");
            return;
        }

        candidates.get(candidateIndex).addVote();
        voter.vote();
        System.out.println("🗳️ Vote cast successfully.");
    }

    public void showResults() {
        System.out.println("\n📊 --- Election Results ---");
        for (Candidate c : candidates) {
            System.out.println(c.getName() + ": " + c.getVotes() + " votes");
        }
    }

    public void listCandidates() {
        System.out.println("\n👤 --- Candidates ---");
        for (int i = 0; i < candidates.size(); i++) {
            System.out.println(i + ". " + candidates.get(i).getName());
        }
    }
}

public class EVotingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ElectionSystem system = new ElectionSystem();
        int choice;

        // Admin adds candidates
        System.out.println("=== Admin Panel: Add Candidates ===");
        while (true) {
            System.out.print("Enter candidate name (or type 'done' to finish): ");
            String name = sc.nextLine();
            if (name.equalsIgnoreCase("done")) break;
            system.addCandidate(name);
        }

        // Main menu
        do {
            System.out.println("\n=== E-Voting System ===");
            System.out.println("1. Register Voter");
            System.out.println("2. Vote");
            System.out.println("3. View Results (Admin)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter Voter ID to register: ");
                    String voterId = sc.nextLine();
                    system.registerVoter(voterId);
                    break;

                case 2:
                    System.out.print("Enter your Voter ID: ");
                    String voter = sc.nextLine();
                    system.listCandidates();
                    System.out.print("Enter candidate number to vote: ");
                    int candidateIndex = Integer.parseInt(sc.nextLine());
                    system.castVote(voter, candidateIndex);
                    break;

                case 3:
                    system.showResults();
                    break;

                case 4:
                    System.out.println("👋 Exiting system. Goodbye!");
                    break;

                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }

        } while (choice != 4);

        sc.close();
    }
}