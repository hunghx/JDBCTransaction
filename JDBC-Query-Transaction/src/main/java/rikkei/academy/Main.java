package rikkei.academy;

import rikkei.academy.util.ConnectDB;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection conn = ConnectDB.getConnection();
        System.out.println(conn);

        // CallableStatement
//        CallableStatement callSt = null;
//        try {
//            callSt = conn.prepareCall("{call call_avg_age(?)}");
//            callSt.registerOutParameter(1, Types.DOUBLE);
//            callSt.executeUpdate();
//            double out = callSt.getDouble(1);
//            System.out.println(out);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }finally {
//            if (callSt!=null){
//                try {
//                    callSt.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }

        Statement stmt = null;
        // Transaction
        try {
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            stmt.executeUpdate("UPDATE accounts set balance = balance +500 where id = ? ");

            stmt.executeUpdate("UPDATE accounts set balance = balance -500 where id = 2 ");

            conn.commit();
        }catch (SQLException e){
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }

    }
}