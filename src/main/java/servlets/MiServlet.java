package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@WebServlet("/controlador")
public class MiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static final String URL = "jdbc:mysql://10.48.142.213/dbalumnos";
	//static final String SQL = "SELECT * FROM alumnos";

    public MiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		
		// index.html
        String operacion = request.getParameter("operacion");
		//String sentencia = request.getParameter("sentencia");
		//String sentenciaId = request.getParameter("sentenciaId");
		//String sentenciaCurso = request.getParameter("sentenciaCurso");
		//String sentenciaNombre = request.getParameter("sentenciaNombre");

		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try(Connection con = DriverManager.getConnection(URL, "mp7", "secreto");
					Statement st = con.createStatement())
			{
				
				if (operacion.equals("consulta")) {
					
					String sentencia = request.getParameter("sentencia");
					ResultSet rs = st.executeQuery(sentencia);
					out.append("<html><body>");
					out.append("<h1 style='text-align:center;'>Usa JDBC para recuperar registros de una tabla</h1>");
					out.append("<p>id curso nombre</p>");
					while(rs.next()) {
						int id = rs.getInt(1);
						String curso = rs.getString(2);
						String nombre = rs.getString(3);
						response.setContentType("text/html;charset=UTF-8");
						out.append(crearSalida(id, curso, nombre));
						//out.close();
					}
					
				} else if ("alta".equals(operacion)) {
	                String sentenciaId = request.getParameter("sentenciaId");
	                String sentenciaCurso = request.getParameter("sentenciaCurso");
	                String sentenciaNombre = request.getParameter("sentenciaNombre");

	                if (sentenciaId != null && sentenciaCurso != null && sentenciaNombre != null) {
	                    String insertSQL = "INSERT INTO alumnos (id, curso, nombre) VALUES (?, ?, ?)";
	                    try (PreparedStatement pst = con.prepareStatement(insertSQL)) {
	                        pst.setInt(1, Integer.parseInt(sentenciaId));
	                        pst.setString(2, sentenciaCurso);
	                        pst.setString(3, sentenciaNombre);
	                        int rowsAffected = pst.executeUpdate();
	                        out.append("<html><body>");
	    					out.append("<h1 style='text-align:center;'>Usa JDBC para grabar registros de una tabla</h1>");
	                        out.append("<p>Filas afectadas: " + rowsAffected + "</p>");
	                    }
	                } else {
	                    out.append("<h1>ERROR</h1>");
	                }
				    
				    
					
				}
				
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			} finally {
				out.close();
			}
			
			
			/*{
		        String insertSQL = "INSERT INTO alumnos (id, curso, nombre) VALUES (?, ?, ?)";
		        
		        

			}*/
			
			
			out.append("</body></html>");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		/*String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String edad = request.getParameter("edad");
		String[] aficiones = request.getParameterValues("hobbies");
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String salida = crearSalida(id, curso, nombre);
		out.append(salida);
		out.close();*/
		
		
		
	}

	private String crearSalida(int id, String curso, String nombre) {
		
		return id + " " + curso + " " + nombre + "<br>";
		
	}



}
