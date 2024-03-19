package Assignment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Customer")
public class Customer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
        	String Product = request.getParameter("pname");
            int cId = Integer.parseInt(request.getParameter("cid"));
            String cname = request.getParameter("cname");
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1", "root", "Abhipriya123*");

            // Check if product available quantity is sufficient
            PreparedStatement pstCheck = con.prepareStatement("SELECT pprice,pavailable FROM PRODUCT1 WHERE pname=?");
            //to assign a string
            pstCheck.setString(1, request.getParameter("pname"));
            ResultSet rsCheck = pstCheck.executeQuery();
            

            if (rsCheck.next()) {
                int availableQuantity = rsCheck.getInt("pavailable");
                int price = rsCheck.getInt("pprice");
                if (quantity < availableQuantity) {
                	out.println("<html><body><h2 style='margin-left:10px;'>NEW AVAILABILITY QUANTITY : "+(availableQuantity-quantity)+"</h2></body></html>");
                	
//                	Class.forName("com.mysql.jdbc.Driver");
//                    Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1", "root", "Abhipriya123*");
//                	PreparedStatement pstCheck1 = con1.prepareStatement("UPDATE PRODUCT1 set pavailable=? where pid=?");
//                	pstCheck.setInt(1,(availableQuantity-quantity) );
//                	pstCheck.setInt(2, cId);
//                    int rsCheck1 = pstCheck.executeUpdate();
                	
//                	out.println("Order Success");
//    				out.println("To See Invoice Click on Button");
//    				out.println("<html><body><form action=invoiceservlet>");
//    				out.println("<input type='submit' value='Invoice'></form></body></html>");
//    				out.println("</form></html></body>");
                	
    				out.println("<html><body>");
    				out.println("<fieldset style='width:400px; background-color:orangered;'>");
    				out.println("<div style='margin-left:40px;'>");
    				out.println("<h2>INVOICE DETAILS</h2>");
    				out.println("<div style='color:white;'>");
    				out.println("<h3> Customer Name : "+cname+"</h3>");
    				out.println("<h3> Number of Items bought by customers : "+quantity+"</h3>");
    				out.println("<h3> Total cost : "+(quantity*price)+"</h3>");
    				out.println("</div>");
    				out.println("</div>");
    				out.println("</fieldset>");
    				out.println("</body></html>");
    				out.println("</form></html></body>");
    			
                }
                else {
//                	out.println("Order Failed, due to insufficient quentity");
//    				out.println("Please generate order with less than or equal to "+availableQuantity);
//    				RequestDispatcher rd=request.getRequestDispatcher("Customer.html");
//    				rd.include(request, response);
    				
    				response.setContentType("text/html");

    				// Get a PrintWriter object to write the response
    				PrintWriter out1 = response.getWriter();

    				// Write error messages
    				out1.println("<html><body>");
    				out1.println("<h2>Order Failed</h2>");
    				out1.println("<h2>Due to insufficient quantity. Please generate the order with less than or equal to " + availableQuantity + "</h2>");
    				out1.println("</body></html>");

    				// Include Customer.html
    				//to return old pad stop request
    				RequestDispatcher rd = request.getRequestDispatcher("Customer.html");
    				rd.include(request, response);
                }
            } 
            else {
                out.println("<html><body><h2>Product not found!</h2></body></html>");
                return; 
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            out.println("<html><body><h2>An error occurred while processing your request. Please try again later.</h2></body></html>");
        }
    }
}
