package Assignment;
//Import necessary classes/interfaces from the Java API and other packages. 
//several classes/interfaces related to Servlets, JDBC, and IO operations are imported.
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//WebServlet-This part of the annotation specifies the URL pattern to which the servlet should respond. 
//In this case, the servlet OrderGenerate will handle requests that match the URL pattern "/order".
//So, when a client sends an HTTP request to the server with the URL ending with "/order", 
//The servlet container (such as Apache Tomcat) will invoke the doGet or doPost method of the OrderGenerate servlet to handle the request.
@WebServlet("/order")
public class OrderGenerate extends HttpServlet {
    private static final long serialVersionUID = 1L;
//method handles HTTP GET requests.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
        	//Loading the driver(creation of driver class object)-forname(factory method)
            Class.forName("com.mysql.jdbc.Driver");
            //define the connection url(communication protocal is jdbc,location of a database database location is localhost:3306,global or installation name of a database  default name is xe)
            //to get the connection execute getConnection() method with connection url,username,password of a database
            //driver manager is a service,it holds currently loaded driver class objects
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1", "root", "Abhipriya123*");
            //create a prepared statement objects
            PreparedStatement pst = con.prepareStatement("SELECT * FROM company2 where cid=1");
            //for select commands--drl
            //resultset id used to store the database
            ResultSet rs = pst.executeQuery();

            out.println("<html><body>");
            out.println("<fieldset style='width: 300px; background-color: orangered; border-color: white;'>");
            out.println("<h1 style='text-align:center;'>Select a Product</h1>");
            out.println("<form action='Customer.html' method='post'>");
            out.println("<div style='margin-left:90px;'>");
            out.println("<select name='product'>");
            //rs.next() moves the cursor to the second row
            while (rs.next()) {
            	//rs.getString(3) retrieves the value of the third column ("products") in the current row, which is "Curd"
                String name = rs.getString(3);
                //Output so far: <option value='Milk'>Milk</option>
                out.println("<option value='" + name + "'>" + name + "</option>");
            }
          
            out.println("</select>");
            out.println("<input type='submit' value='Confirm'>");
            out.println("</div>");
            out.println("</form>");
            out.println("</fieldset>");
            out.println("</body></html>");
            
        

            pst.close();
            con.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
