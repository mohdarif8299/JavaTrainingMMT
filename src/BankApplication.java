import cashier.Cashier;
import manager.Manager;
import user.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.Scanner;

public class BankApplication {
    public static void main(String[] args) {
        System.out.println("Welcome to HDFC Bank");
        System.out.println("SELECT your role to continue-");
        System.out.println("1- Manager");
        System.out.println("2- Cashier");
        System.out.println("3- User");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("SELECT an option-");
                System.out.println("1- Create new user account");
                System.out.println("2- View all user account details");
                System.out.println("3- Export all user account details to File");
                int option = scanner.nextInt();
                Manager manager = new Manager();
                switch (option) {
                    case 1:
                        System.out.println("Enter user name");
                        String userName = scanner.next();
                        System.out.println("Enter user phone number");
                        String phoneNumber = scanner.next();
                        System.out.println("Enter minimum amount to deposit");
                        String amount  = scanner.next();
                        String accountNumber = generateAccountNumber();
                        User user = new User(userName,accountNumber, phoneNumber, Double.parseDouble(amount));
                        manager.createUserAccount(user);
                        break;
                    case 2:
                        manager.displayAllAccountDetails();
                        break;
                    case 3:
                        manager.exportUserAccountDetailsToFile();
                        break;
                }
            break;
            case 2:
                System.out.println("SELECT an option-");
                System.out.println("1- Check Balance");
                System.out.println("2- Withdraw Money");
                System.out.println("3- Deposit Money");
                int cashierChoice = scanner.nextInt();
                Cashier cashier = new Cashier();
                switch (cashierChoice) {
                    case 1:
                        System.out.println("Enter the account number to check balance");
                        String accNo = scanner.next();
                        cashier.checkBalance(accNo);
                       break;
                    case 2:
                        System.out.println("Enter the account number to withdraw balance");
                        String withdrawAcc = scanner.next();
                        System.out.println("Enter amount to withdraw");
                        String withdrawAmount = scanner.next();
                        cashier.withdrawAmount(withdrawAcc, withdrawAmount);
                        break;
                    case 3:
                        System.out.println("Enter the account number to deposit money");
                        String depositAcc = scanner.next();
                        System.out.println("Enter amount to withdraw");
                        String depositAmount = scanner.next();
                        cashier.depositAmount(depositAcc, depositAmount);
                        break;
                }

                break;

            case 3:
                System.out.println("Enter you Account Number");
                String accNo = scanner.next();
                try {
                    Connection connection = driver.Connection.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from user where account_no = ?");
                    preparedStatement.setString(1, accNo);
                    System.out.println("Your Account Details are as follows-");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        System.out.println("Your Name: "+ resultSet.getString("name"));
                        System.out.println("Your Account Number: "+ resultSet.getString("account_no"));
                        System.out.println("Your Phone Number: "+ resultSet.getString("phone_no"));
                        System.out.println("Your Available Balance: "+ Double.parseDouble(resultSet.getString("balance")));

                        System.out.println("SELECT an option-");
                        System.out.println("1- UPDATE Name");
                        System.out.println("2- UPDATE Phone Number");
                        System.out.println("3- Check Balance");
                        System.out.println("4- Withdraw Money");
                        System.out.println("5- Transfer Money");

                        int userChoice = scanner.nextInt();
                        switch (userChoice) {
                            case 1:
                                System.out.println("Enter new name");
                                String newName = scanner.next();
                                User.updateName(newName, accNo);
                                break;
                            case 2:
                                System.out.println("Enter new number");
                                String newNumber = scanner.next();
                                User.updateNumber(newNumber, accNo);
                                break;
                            case 3:
                                System.out.println("Your Current Balance is Rs."+ Double.parseDouble(resultSet.getString("balance")));
                                break;
                            case 4:
                                System.out.println("Enter withdraw amount");
                                String withdrawAmount  = scanner.next();
                                User.withdrawAmount(resultSet, withdrawAmount, connection, accNo);
                                break;
                            case 5:
                                System.out.println("Enter Payee Account No");
                                String payeeAcc = scanner.next();
                                System.out.println("Enter Amount to transfer");
                                String amountTransfer = scanner.next();
                                User.transferAmount(resultSet, connection, amountTransfer, payeeAcc, accNo);
                                break;
                        }

                    } else {
                        System.out.printf("Invalid Account Number");
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public static String generateAccountNumber() {
        Random r = new Random(System.currentTimeMillis());
        return 100000000 + r.nextInt(2000000000)+"";
    }
}
