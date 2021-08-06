import java.rmi.RemoteException;
// import java.rmi.registry.LocateRegistry;
// import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientIF, Runnable{ //since we execute instance of this class with mul threads
    private ServerIF server;
    private String name =null;
    static Scanner input;
    protected Client(String name, ServerIF server) throws RemoteException {
        this.name = name;
        this.server =server;
        server.registerClient(this);
    }
    
    public synchronized void retriveMessage(String message) throws RuntimeException{
        System.out.println("----------------------------WELCOME TO eEXAM----------------------------\n\n");
        System.out.println("Studnet Name :" + this.name);
        int qNum =1;
        System.out.println("Note: Say 1 if it's true or 0 if it's false");
        for(String question: message.split("@")){
            System.out.println(qNum +", "+ question);
            ++qNum;
        }
    }
    public synchronized void retriveDisscution(String message) throws RemoteException{
        System.out.println(message);
    }
    public void run(){
        input = new Scanner(System.in);
        System.out.println("=================Answer Sheet=================n");
        int answers [] = new int[5], numQuestions =1, ans;
        while(numQuestions <=5){
            System.out.print(numQuestions+" :");
            ans = input.nextInt();
            while(ans != 0 & ans !=1){
                System.err.println("Please Enter 1 for True and 0 for False!");
                ans = input.nextInt();
            }
            answers[numQuestions-1] = ans;
            ++numQuestions;
        }
        try {
            System.out.println("========================================================");
            System.out.println("|\tResult :"+server.sendAnswer(this ,answers)+"         |");
            System.out.println("========================================================");
            //Thread Synchronization needed like barrier here
            // for(Thread thread : )
            //     thread.join(); getback to here
            System.out.println("\n\nYou can Disscuss with your classmate in the chatboot!\n\n");
            while(true){
                String disscutionMsg = input.nextLine();
                server.broadcastDisscussion(name+" : "+disscutionMsg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // static Scanner input = new Scanner(System.in); 
    // public Client(){}
    // public static void main(String[] args) {
    //     try {  
    //         // Getting the registry 
    //         Registry registry = LocateRegistry.getRegistry(null); 
       
    //         // Looking up the registry for the remote object 
    //         ExamInterface stub = (ExamInterface) registry.lookup("Hello"); 
       
    //         // Calling the remote method using the obtained object 
    //         System.out.print("\n==========================WELCOME TO eEXAM==========================n");
    //         System.out.print("Enter Firstname :");
    //         stub.setStudentFname(input.nextLine());
    //         System.out.print("Enter Lastname :");
    //         stub.setStudentLname(input.nextLine());
    //         // System.out.println("Remote method invoked"); 
    //      } catch (Exception e) {
    //         System.err.println("Client exception: " + e.toString()); 
    //         e.printStackTrace(); 
    //      } 
    // }
}
