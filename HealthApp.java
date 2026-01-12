import java.sql.*;
import java.net.*;
import java.io.*;

public class HealthApp {

    public static void main(String[] args) throws Exception {

        // Sample input
        String json = "{\"age\":45,\"bmi\":28.4,\"bp\":130,\"glucose\":160,\"heart_rate\":85}";

        // Call Python ML API
        URL url = new URL("http://localhost:5000/predict");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.getOutputStream().write(json.getBytes());

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = br.readLine();
        int risk = response.contains("1") ? 1 : 0;

        // JDBC save
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection db = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/healthdb","root","password");

        PreparedStatement ps = db.prepareStatement(
            "INSERT INTO records(age,bmi,bp,glucose,heart_rate,risk) VALUES(?,?,?,?,?,?)");

        ps.setInt(1,45);
        ps.setFloat(2,28.4f);
        ps.setInt(3,130);
        ps.setInt(4,160);
        ps.setInt(5,85);
        ps.setInt(6,risk);
        ps.executeUpdate();

        System.out.println("Saved to DB. Risk=" + risk);
        db.close();
    }
}
