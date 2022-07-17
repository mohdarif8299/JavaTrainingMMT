package manager;

import user.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Manager {
    public void createUserAccount(User user) {
        Connection connection = null;
        try {
            connection = driver.Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO user (name, account_no, phone_no, balance) VALUES (?, ?, ?, ?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getAccountNumber());
            ps.setString(3, user.getPhoneNumber());
            ps.setDouble(4, user.getBalance());

            int result = ps.executeUpdate();

            if (result > 0)
                System.out.println("User Account Created Successfully");

            else
                System.out.println("Error while creating user account");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while creating user account");
        } finally {
            try {
                if (connection!=null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void displayAllAccountDetails() {
        try {
            Connection connection = driver.Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from user");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("------------------------------START-----------------------------------");
            while (resultSet.next()) {
                System.out.println();
                System.out.println("Account Holder Name: "+resultSet.getString("name"));
                System.out.println("Account Holder Phone Number: "+resultSet.getString("phone_no"));
                System.out.println("Account Holder Account Number: "+resultSet.getString("account_no"));
                System.out.println("Account Holder Available Balance: "+resultSet.getString("balance"));
                System.out.println();
            }
            System.out.println("-------------------------------END------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exportUserAccountDetailsToFile() {
        BufferedWriter output = null;
        try {
            File file = new File("account_details.txt");
            output = new BufferedWriter(new FileWriter(file));
            Connection connection = driver.Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from user");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                output.write("-------------START--------------"+"\n");
                output.write("Account Holder Name: "+resultSet.getString("name")+"\n");
                output.write("Account Holder Phone Number: "+resultSet.getString("phone_no")+"\n");
                output.write("Account Holder Account Number: "+resultSet.getString("account_no")+"\n");
                output.write("Account Holder Available Balance: "+resultSet.getString("balance")+"\n");
                output.write("-------------END--------------\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                try {
                    output.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
