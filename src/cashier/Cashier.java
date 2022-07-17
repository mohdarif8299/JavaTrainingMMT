package cashier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cashier {
    public void checkBalance(String accNo) {
        try {
            Connection connection = driver.Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from user where account_no = ?");
            ps.setString(1, accNo);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                System.out.println("Balance Available is: Rs. "+ resultSet.getString("balance"));
            } else {
                System.out.println("Invalid Account No");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void withdrawAmount(String withdrawAcc, String withdrawAmount) {
        try {
            Connection connection = driver.Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from user where account_no = ?");
            ps.setString(1, withdrawAcc);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                double currBal = Double.parseDouble(resultSet.getString("balance")) - Double.parseDouble(withdrawAmount);
                if(currBal < 0) {
                    System.out.println("Insufficient Amount");
                } else {
                    PreparedStatement ps1 = connection.prepareStatement("UPDATE user set balance = ? where account_no = ?");
                    ps1.setString(1, currBal+"");
                    ps1.setString(2, withdrawAcc);

                    int result = ps1.executeUpdate();

                    if(result > 0) {
                        System.out.println("Withdraw Successful");
                    } else {
                        System.out.println("Error while withdrawing money, try again");
                    }

                }
            } else {
                System.out.println("Invalid Account Number");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void depositAmount(String depositAcc, String depositAmount) {
        try {
            Connection connection = driver.Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from user where account_no = ?");
            ps.setString(1, depositAcc);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                double currBal = Double.parseDouble(resultSet.getString("balance")) + Double.parseDouble(depositAmount);
                PreparedStatement ps1 = connection.prepareStatement("UPDATE user set balance = ? where account_no = ?");
                ps1.setString(1, currBal+"");
                ps1.setString(2, depositAcc);
                int result = ps1.executeUpdate();
                if(result > 0) {
                    System.out.println("Deposit Successful");
                } else {
                    System.out.println("Error while depositing money, try again");
                }
            } else {
                System.out.println("Invalid Account Number");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
