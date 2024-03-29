package Main;

import java.sql.*;

public class db_connection {

    Connection connection;
    Statement statement;
    String SQL;
   
    String url;
    String username;
    String password;

    public db_connection(String url, String username, String password) {
  
        this.url = url;
        this.username = username;
        this.password = password;
     
    }
    
    public Connection connexionDatabase() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {System.err.println(e);}
        return connection;
    }
 
   public Connection closeconnexion() {

        try {
            connection.close();
        } catch (Exception e) {System.err.println(e);}
        return connection;
    }

    public ResultSet executionQuery(String sql) {
        connexionDatabase();
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(sql);
        } catch (SQLException ex) {System.err.println(ex);}
        return resultSet;
    }

    public String executionUpdate(String sql) {
        connexionDatabase();
        String result = "";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            result = sql;

        } catch (SQLException ex) {
            result = ex.toString();
        }
        closeconnexion();
        return result;

    }

    public ResultSet querySelectAll(String nomTable) {

        connexionDatabase();
        SQL = "SELECT * FROM " + nomTable;
        System.out.println(SQL);
        return this.executionQuery(SQL);

    }

    public ResultSet querySelectAll(String nomTable, String état) {

        connexionDatabase();
        SQL = "SELECT * FROM " + nomTable + " WHERE " + état;
        return this.executionQuery(SQL);

    }

    public ResultSet querySelect(String[] nomColonne, String nomTable) {

        connexionDatabase();
        int i;
        SQL = "SELECT ";

        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }

        SQL += " FROM " + nomTable;
        return this.executionQuery(SQL);

    }

    public ResultSet fcSelectCommand(String[] nomColonne, String nomTable, String état) {

        connexionDatabase();
        int i;
        SQL = "SELECT ";

        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }

        SQL += " FROM " + nomTable + " WHERE " + état;
        return this.executionQuery(SQL);

    }

    public String queryInsert(String nomTable, String[] contenuTableau) {

        connexionDatabase();
        int i;
        
        SQL = "INSERT INTO " + nomTable + " VALUES(";

        for (i = 0; i <= contenuTableau.length - 1; i++) {
            if(contenuTableau[i].equals("NULL")){
                SQL += contenuTableau[i] ;
            }
            else{
                SQL += '"' + contenuTableau[i] + '"';
            }
            if (i < contenuTableau.length - 1) {
                SQL += ",";
            }
        }

        SQL += ")";
        closeconnexion();
        return this.executionUpdate(SQL);

    }

    public String queryInsert(String nomTable, String[] nomColonne, String[] contenuTableau) {

        connexionDatabase();
        int i;
        SQL = "INSERT INTO " + nomTable + "(";
        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }
        SQL += ") VALUES(";
        for (i = 0; i <= contenuTableau.length - 1; i++) {
            if(contenuTableau[i].equals("NULL")){
                SQL += contenuTableau[i] ;
            }
            else{
                SQL += '"' + contenuTableau[i] + '"';
            }
            if (i < contenuTableau.length - 1) {
                SQL += ",";
            }
        }

        SQL += ")";
        closeconnexion();
        return this.executionUpdate(SQL);

    }

    public String queryUpdate(String nomTable, String[] nomColonne, String[] contenuTableau, String état) {

        connexionDatabase();
        int i;
        SQL = "UPDATE " + nomTable + " SET ";

        for (i = 0; i <= nomColonne.length - 1; i++) {
            if(contenuTableau[i].equals("NULL")){
                SQL += nomColonne[i] + "=" + contenuTableau[i] ;
            }
            else{
                SQL += nomColonne[i] + '=' + '"' + contenuTableau[i] + '"';
            }
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }

        SQL += " WHERE " + état;
        closeconnexion();
        return this.executionUpdate(SQL);

    }

    public String queryDelete(String nomtable) {

        connexionDatabase();
        SQL = "DELETE FROM " + nomtable;
        closeconnexion();
        return this.executionUpdate(SQL);

    }

    public String queryDelete(String nomTable, String état) {

        connexionDatabase();
        SQL = "DELETE FROM " + nomTable + " WHERE " + état;
        closeconnexion();
        return this.executionUpdate(SQL);

    }
}
