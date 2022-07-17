package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    public String name;
    public String accountNumber;
    public String phoneNumber;
    public double balance;

    public User(String name, String accountNumber, String phoneNumber, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public static void updateName(String newName, String accNo) {
        try {
            Connection con = driver.Connection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE user set name = ? where account_no = ?");
            ps.setString(1, newName);
            ps.setString(2, accNo);
            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Name Updated successfully");
            } else {
                System.out.println("Error while updating name, please try again");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateNumber(String newNumber, String accNo) {
        try {
            Connection conn = driver.Connection.getConnection();
            PreparedStatement ps1 = conn.prepareStatement("UPDATE user set phone_no = ? where account_no = ?");
            ps1.setString(1, newNumber);
            ps1.setString(2, accNo);
            int rs = ps1.executeUpdate();

            if (rs > 0) {
                System.out.println("Name UPDATEd successfully");
            } else {
                System.out.println("Error while updating number, please try again");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void withdrawAmount(ResultSet resultSet, String withdrawAmount, Connection connection, String accNo) {
        try {
            if(Double.parseDouble(resultSet.getString("balance")) - Double.parseDouble(withdrawAmount) < 0) {
                System.out.println("Insufficient Amount to be withdraw");
            } else {
                double leftAmount = Double.parseDouble(resultSet.getString("balance")) - Double.parseDouble(withdrawAmount);
                PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE user set balance = ? where account_no  = ?");
                preparedStatement1.setString(1, leftAmount+"");
                preparedStatement1.setString(2, accNo);
                int rs1 = preparedStatement1.executeUpdate();

                if (rs1 > 0) {
                    System.out.println("Amount withdraw successfully");
                } else {
                    System.out.println("Error while withdrawing amount, please try again");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void transferAmount(ResultSet resultSet, Connection connection, String amountTransfer, String payeeAcc, String accNo) {
        try {
            if(Double.parseDouble(resultSet.getString("balance")) - Double.parseDouble(amountTransfer) < 0) {
                System.out.println("Insufficient amount to transfer");
            } else {
                PreparedStatement ps2 = connection.prepareStatement("SELECT * from user where account_no = ?");
                ps2.setString(1 , payeeAcc);
                ResultSet rs1 = ps2.executeQuery();
                if (rs1.next()) {
                    double leftBalance = Double.parseDouble(resultSet.getString("balance")) - Double.parseDouble(amountTransfer);
                    PreparedStatement ps3 = connection.prepareStatement("UPDATE user set balance = ? where account_no = ?");
                    ps3.setString(1, leftBalance+"");
                    ps3.setString(2, accNo);

                    int r1 = ps3.executeUpdate();

                    double UPDATEBalance = Double.parseDouble(rs1.getString("balance")) + Double.parseDouble(amountTransfer);
                    PreparedStatement ps4 = connection.prepareStatement("UPDATE user set balance = ? where account_no = ?");
                    ps4.setString(1, UPDATEBalance+"");
                    ps4.setString(2, payeeAcc);

                    int r2 = ps4.executeUpdate();

                    if(r1 >0 && r2 >0) {
                        System.out.println("Amount Transfer Successfully");
                    } else {
                        System.out.println("Error while transferring amount");
                    }

                } else {
                    System.out.println("Invalid Payee Account No");
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
