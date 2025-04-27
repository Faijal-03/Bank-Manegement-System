
import java.util.*;

class accountUser {
    private static long totalAccount = 1000000000L;
    String accountHolder;
    long acNo;
    int pin;
    bankManagement atm;

    public accountUser(String accountHolder ,int pin){
        this.accountHolder = accountHolder;
        this.pin = pin;
        this.acNo = totalAccount++;
        this.atm = new bankManagement(this, pin);  // Pass `this` to link accountUser
    }


    public long getAcNo() {
        return acNo;
    }

    public int getPin() {
        return pin;
    }

    public bankManagement getAtm() {
        return atm;
    }
    public String getAccountHolder(){
        return accountHolder;
    }
    }

    class bankManagement {

        // ANSI color codes
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";


        private int balance = 100;
        private int pin;
        private LinkedList<String> transactions = new LinkedList<>();

        Scanner sc = new Scanner(System.in);

        accountUser user;

        public bankManagement(accountUser user, int pin) {
            this.pin = pin;
            this.user = user;
        }


        // change pin
        public void changePin() throws Exception {
            boolean run = true;
            int count = 0;

            while (run) {

                System.out.println(YELLOW + "Enter Your Old PIN:" + RESET);
                try {
                    int oldPin = sc.nextInt();
                    sc.nextLine(); // Clear the leftover newline

                    if (oldPin == pin) {
                        System.out.println(BLUE + "Enter New PIN:" + RESET);
                        int newPin = sc.nextInt();
                        sc.nextLine(); // Clear the leftover newline

                        if (newPin == oldPin) {
                            System.out.println(RED + "New PIN cannot be the same as old PIN!" + RESET);
                            Thread.sleep(1000);
                            continue;
                        }
                        if (newPin < 1000 || newPin > 9999) {
                            System.out.println(RED + "PIN must be 4 digits!" + RESET);
                            Thread.sleep(1000);
                            continue;
                        }
                        pin = newPin;
                        user.pin = newPin;  // sync with accountUser

                        System.out.println(GREEN + "PIN Changed Successfully!" + RESET);
                        run = false; // Loop बंद हो जाएगा
                    } else {
                        System.out.println(RED + "Invalid PIN! Please Try Again." + RESET);
                        count++;
                        if (count >= 3) {
                            System.out.println(RED + "You Tried More Than 3 Times.\nTry Again 1 day later." + RESET);
                            break;
                            //  run = f; // 3 बार गलत PIN पर लूप बंद
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println(RED + "Invalid input! Please enter numbers only." + RESET);
                    sc.nextLine(); // Clear the entire invalid input
                }
            }
        }

        // valid pin
        public boolean validPin() {
            int count = 0;
            while (count < 3) {
                System.out.println(YELLOW + "Enter Your Pin Please " + RESET);
                try {
                    int enterPin = sc.nextInt();
                    sc.nextLine();
                    if (enterPin == pin) {
                        return true;
                    }
                    System.out.println(RED + "Invalid Pin Try Again " + (2 - count) + RESET);
                    count++;
                } catch (InputMismatchException e) {
                    System.out.println(RED + "Enter only Numbers " + RESET);
                    sc.nextLine();
                }
            }
            System.out.println(RED + " You Enter more 3 times wrong pin try Again 24 hours letters");
            return false;
        }

        // check balance
        public void CheckBalance() {
            if (validPin()) {
                System.out.println(BLUE + "Your Balance Is " + balance + RESET);
            }
        }

        // withdraw
        public void withdraw() throws Exception {
            int count = 1;

            while (true) {
                try {
                    System.out.println(BLUE + "Enter Your withdraw Amount!" + RESET);
                    int withdrawAmount = sc.nextInt();

                    if (withdrawAmount >= balance) {
                        System.out.println(RED + "Insufficient balance" + RESET);
                        Thread.sleep(1000);
                        continue;
                    }

                    System.out.println(YELLOW + "Enter your pin Please ." + RESET);
                    int userPin = sc.nextInt();

                    if (userPin == pin) {
                        System.out.println(PURPLE + withdrawAmount + " SuccessFully withdraw" + RESET);
                        balance -= withdrawAmount;
                        String time = java.time.LocalDateTime.now().toString();
                        addTransaction("Withdraw: ₹" + withdrawAmount + " | " + time);
                        break;
                    } else {
                        System.out.println(RED + "invalid Pin Please Enter Valid Pin" + RESET);
                        count++;
                        Thread.sleep(1000);
                    }

                    if (count >= 3) {
                        System.out.println(RED + "you Try more then 3 time \ntry Again 1 hours later" + RESET);
                        break;
                    }

                } catch (InputMismatchException e) {
                    System.out.println(RED + e + RESET);
                    sc.nextLine();
                }
            }
        }

        // Deposit
        public void deposit() {
            while (validPin()) {

                try {
                    System.out.println(YELLOW + "Enter Deposit Amount " + RESET);
                    int depositAmount = sc.nextInt();
                    System.out.println(GREEN + depositAmount + " successFully DEPOSIT" + RESET);
                    balance += depositAmount;
                    String time = java.time.LocalDateTime.now().toString();
                    addTransaction("Deposit: ₹" + depositAmount + " | " + time);
                    System.out.println(CYAN + "Available Balance is " + balance + RESET);
                    break;

                } catch (InputMismatchException e) {
                    System.out.println(RED + "Enter only Numbers " + RESET);
                }
            }
        }

        // addTransaction
        private void addTransaction(String transaction) {

            if (transactions.size() == 5) {
                transactions.removeFirst();
            }
            transactions.add(transaction + " | Balance: ₹" + balance);
        }

        // mini Statement
        public void miniStatement() {
            System.out.println(YELLOW + "\n----- MINI STATEMENT -----" + RESET);
            if (transactions.isEmpty()) {
                System.out.println(PURPLE + "No transactions yet." + RESET);
            } else {
                for (String t : transactions) {
                    System.out.println(GREEN + t + RESET);
                }
            }
            System.out.println("--------------------------\n");
        }
    }


    public class atm {
        // ANSI color codes
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";

        public static void main(String[] args) throws Exception {


            Scanner sc = new Scanner(System.in);
            HashMap<Long, accountUser> users = new HashMap<>();

            System.out.println(YELLOW + ".... Welcome To || ATM MANAGEMENT SYSTEM || ...." + RESET);

            while (true) {
                accountUser currentUser = null;

                // लॉगिन/अकाउंट क्रिएशन मेनू
                boolean loggedIn = false;
                while (!loggedIn) {
                    System.out.println(PURPLE + "\n1. Create New Account\n2. Login to Existing Account\n3. Exit" + RESET);
                    int opt = sc.nextInt();
                    sc.nextLine();

                    switch (opt) {
                        case 1:
                            System.out.println(YELLOW + "Enter Your FULL Name" + RESET);
                            String accountHolderName = sc.nextLine();
                            System.out.print(YELLOW + "Set a 4-digit PIN: " + RESET);
                            int newPin = sc.nextInt();
                            sc.nextLine();
                            if (newPin < 1000 || newPin > 9999) {
                                System.out.println(RED + "Invalid PIN format!" + RESET);
                                break;
                            }
                            accountUser newUser = new accountUser(accountHolderName,newPin);
                            users.put(newUser.getAcNo(), newUser);
                            System.out.println(GREEN + "Account Created! Your Account No: " + newUser.getAcNo() +"\nAccount holder Name: " + newUser.getAccountHolder()+ RESET);
                            currentUser = newUser;
                            // loggedIn = true;
                            continue;

                        case 2:
                            System.out.print(YELLOW + "Enter your Account Number: " + RESET);
                            long acNo = sc.nextLong();
                            sc.nextLine();
                            System.out.print(YELLOW + "Enter your PIN: " + RESET);
                            int pin = sc.nextInt();
                            sc.nextLine();
                            if (users.containsKey(acNo) && users.get(acNo).getPin() == pin) {
                                currentUser = users.get(acNo);
                                System.out.println(GREEN + "Login Successful!" + RESET);
                                System.out.println(BLUE + "Welcome, " + currentUser.getAccountHolder() + "!" + RESET);
                                loggedIn = true;
                            }
                            else {
                                System.out.println(RED + "Invalid Account Number or PIN!" + RESET);
                            }
                            break;
                        case 3:
                            System.out.println(CYAN+"Program exiting now..." +RESET);
                            Thread.sleep(1000);
                            System.exit(0);
                            break;
                        default:
                            System.out.println(RED + "Invalid option!" + RESET);
                    }
                }

                // ATM ऑपरेशन्स मेनू
                bankManagement Atm = currentUser.getAtm();
                boolean sessionActive = true;
                while (sessionActive) {

                    System.out.println(PURPLE + "------< ATM MENU >-------" + RESET);
                    System.out.println(CYAN + "Press 1 to check balance" + RESET);
                    System.out.println(CYAN + "Press 2 to deposit" + RESET);
                    System.out.println(CYAN + "Press 3 to withdraw" + RESET);
                    System.out.println(CYAN + "Press 4 to change PIN" + RESET);
                    System.out.println(CYAN + "Press 5 to mini statement" + RESET);
                    System.out.println(CYAN + "Press 6 to logout" + RESET);
                    System.out.println(CYAN + "Press 7 to exit" + RESET);
                    System.out.println("..........................");

                    int choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            Atm.CheckBalance();
                            break;
                        case 2:
                            Atm.deposit();
                            break;
                        case 3:
                            Atm.withdraw();
                            break;
                        case 4:
                            Atm.changePin();
                            break;
                        case 5:
                            Atm.miniStatement();
                            break;
                        case 6:
                            System.out.println(YELLOW + "Logging out..." + RESET);
                            sessionActive = false;
                            break;
                        case 7:
                            System.out.println(YELLOW + "Are you sure? (YES or NO)" + RESET);
                            sc.nextLine();
                            String confirmation = sc.nextLine().toLowerCase();
                            if (confirmation.contains("yes")) {
                                System.out.println("Exiting the PROGRAM!");
                                System.exit(0);
                            }
                            break;
                        default:
                            System.out.println(RED + "Invalid input! Please enter valid input" + RESET);
                    }
                }
            }
        }
    }
