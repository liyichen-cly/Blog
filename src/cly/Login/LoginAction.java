package cly.Login;
import javax.servlet.http.*;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.*;
import java.sql.*;
import cly.Datalink.DataLink;
public class LoginAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	public String execute()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try 
		{
			out = response.getWriter();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		HttpSession session = request.getSession();
		String id = request.getParameter("login_id");
		String password = request.getParameter("password");
		String url = "";
		try
	    {
	    	DataLink datalink = new DataLink();
		    Connection conn = datalink.getConnection();
		    Statement stmt=conn.createStatement(); 
		    ResultSet RS=null; 
		    RS = stmt.executeQuery("select * from Registered_User where User_Id = '" + id + "' and User_Password='" +password+"'" );
		    if(RS.next())
		    {
		    	  RS.close();
	    		  stmt.close();
	    		  conn.close();
	    		  session.setMaxInactiveInterval(15*60);		      
		    	  session.setAttribute("currentLoginUserId",id);
		    	  url = "pass.jsp?id="+id;
		    	  try 
		    	  {
					response.sendRedirect(url);
				  } 
		    	  catch (IOException e) 
		    	  {
					e.printStackTrace();
   				  }
	    		  return NONE;
		    	  
			}
		    else
		    {
		    	RS.close();
		    	stmt.close();
			    conn.close();
			}
	    }
	    catch(SQLException e)
	    {
	    	System.out.print("���ݿ����Ӵ���");
	    }
	    out.println("<script language=javascript>");
		out.println("alert('��ĵ�¼���벻��ȷ!�����µ�¼:');history.back();");
		out.println("</script>");
		return NONE;
	}
}
