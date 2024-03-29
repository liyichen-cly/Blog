package cly.Article;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.*;
import cly.Datalink.DataLink;
public class CommentDAO extends ActionSupport 
{
	private static final long serialVersionUID = 1L;
	public CommentDAO() 
	{
		super();
	}
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
		String id=request.getParameter("id");
		String strserial = request.getParameter("strserial");
		int article_no = 1;
		if(strserial != null)
		{
		    article_no = Integer.parseInt(strserial);
		}
		String content = request.getParameter("content");
		String INSERT_SQL = "insert into comment (article_id,User_Id,Comment_Content) values(?,?,?)";
		try
		{
			DataLink datalink = new DataLink();
			Connection con = datalink.getConnection();
			PreparedStatement ps = null;
			con.setAutoCommit(false);
			ps = con.prepareStatement(INSERT_SQL);
			ps.setInt(1,article_no);
			ps.setString(2,id);
			ps.setString(3,content);
			ps.executeUpdate();
			con.commit();
			try
			{
				ps.close();
				con.close();
			}
			catch(SQLException e2)
			{
				out.print("���ݿ����ʧ�ܣ�");
				out.print("<script language='javascript'>");
				out.print("alert('����ʧ��');history.back();");
				out.print("</script>");
			}
		}
		catch(SQLException e)
		{
			System.out.print(e.getMessage());
		}
		try 
		{
			response.sendRedirect("article.jsp?id="+id+"&&serial="+strserial);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return NONE;
	}
}
