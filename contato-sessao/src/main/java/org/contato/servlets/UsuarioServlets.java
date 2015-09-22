package org.contato.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/usuario")
public class UsuarioServlets extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String usuario = req.getParameter("login");
		String senha = req.getParameter("senha");
		
		if(usuario == "" || senha == "")
		{
			PrintWriter r = resp.getWriter();
			r.write("<html>");
			r.write("<head>");
			r.write("</head>");
			r.write("<body>");
			r.write("Por Favor Preencha todos os campos !! <a href='http://localhost:8080/usuario.html'>Voltar</a>");
			r.write("</body>");
			r.write("</html>");
			
		}else
		{
			addUser(usuario, senha);
			PrintWriter r = resp.getWriter();
			r.write("<html>");
			r.write("<head>");
			r.write("</head>");
			r.write("<body>");
			r.write("Usuário cadastrado com sucesso <a href='http://localhost:8080/index.html'>Voltar</a>");
			r.write("</body>");
			r.write("</html>");
		}
	}
	
	private void addUser(String usuario, String senha) throws IOException
	{
		Path path = Paths.get( "C:/Users/Rodrigo-PC/Projetos/contato-sessao/src/main/users.txt" );
		String novoUsuario = usuario+";"+senha+"\n";
		Files.write( path, novoUsuario.getBytes(), StandardOpenOption.APPEND );
	}
	
}
