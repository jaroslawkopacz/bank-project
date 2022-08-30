import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    private ClientService clientService;


    public static void main(String[] args) {
        new Main().run();
    }

    public void run(){
        MemoryClientRepository memoryClientRepository = new MemoryClientRepository(new ArrayList<>());
        clientService = new ClientService(memoryClientRepository);

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("1 - Add user");
            System.out.println("2 - Find user");
            System.out.println("3 - Exit");

            String next = scanner.next();

            if(next.equals("1")){
                addUser();
            }

            if(next.equals("2")){
                findUser();
            }

            if(next.equals("3")){
                break;
            }


        }
    }

    private void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = scanner.next();
        System.out.println("Enter email: ");
        String email = scanner.next();
        System.out.println("Enter balance: ");
        double balance = scanner.nextDouble();
        clientService.save(new Client(name, email, balance));
    }

    private void findUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter email: ");
        String email = scanner.next();
        System.out.println(clientService.findByEmail(email));
    }
}
