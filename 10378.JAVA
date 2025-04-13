//EASY LEVEL 
// HTML (login.html)

<form action="LoginServlet" method="post">
    Username: <input type="text" name="username"><br>
    Password: <input type="password" name="password"><br>
    <input type="submit" value="Login">
</form>
  
// Java Servlet (LoginServlet.java)
  import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if ("admin".equals(user) && "admin123".equals(pass)) {
            out.println("<h2>Welcome, " + user + "!</h2>");
        } else {
            out.println("<h2>Login failed. Try again.</h2>");
        }
    }
}

//OUTPUT

//Successful Output (from LoginServlet):

<h2>Welcome, admin!</h2>
//Unsuccessful Output (wrong credentials):
 <h2>Login failed. Try again.</h2>

  // MEDIUM LEVEL
  // HTML (employee.html)

  <form action="EmployeeServlet" method="get">
    Enter Employee ID to Search: <input type="text" name="empId">
    <input type="submit" value="Search">
</form>

  // Java Servlet (EmployeeServlet.java)

  import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    Connection conn;

    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String empId = request.getParameter("empId");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try {
            Statement stmt = conn.createStatement();
            String query = (empId != null && !empId.isEmpty())
                    ? "SELECT * FROM employees WHERE id = " + empId
                    : "SELECT * FROM employees";

            ResultSet rs = stmt.executeQuery(query);
            out.println("<h2>Employee Details</h2><ul>");
            while (rs.next()) {
                out.println("<li>" + rs.getInt("id") + " - " + rs.getString("name") + "</li>");
            }
            out.println("</ul>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// OUTPUT 
Scenario 1: No input in the search field
<h2>Employee Details</h2>
<ul>
    <li>1 - John Doe</li>
    <li>2 - Alice Smith</li>
    <li>3 - Bob Johnson</li>
</ul>

Scenario 2: Searching by ID (e.g., empId = 2)
  <h2>Employee Details</h2>
<ul>
    <li>2 - Alice Smith</li>
</ul>

  // HARD LEVEL 
  // JSP (attendance.jsp)

  <form action="AttendanceServlet" method="post">
    Student ID: <input type="text" name="studentId"><br>
    Date: <input type="date" name="date"><br>
    Status: <select name="status">
        <option value="Present">Present</option>
        <option value="Absent">Absent</option>
    </select><br>
    <input type="submit" value="Submit Attendance">
</form>

  // Java Servlet (AttendanceServlet.java)
  
  import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AttendanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "password");

            String sql = "INSERT INTO attendance(student_id, date, status) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            ps.setString(2, date);
            ps.setString(3, status);
            ps.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Attendance submitted successfully!</h2>");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// OUTPUT
// From AttendanceServlet (after saving to DB):
<h2>Attendance submitted successfully!</h2>
// If there’s a DB error (like DB not connected):
  <!-- Exception trace will be printed in console or browser -->
  

