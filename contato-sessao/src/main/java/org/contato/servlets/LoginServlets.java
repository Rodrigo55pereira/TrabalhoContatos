package org.contato.servlets;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/logar")
public class LoginServlets extends HttpServlet {
	private static final long	serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException 
	{
		 String usuario =	req.getParameter("usuario");
		 String senha = req.getParameter("senha");
		 
		if(usuario == "" || senha == ""){
			PrintWriter r = resp.getWriter();
			r.write("<html>");
			r.write("<head>");
			r.write("</head>");
			r.write("<body>");
			r.write("Por Favor Preencha todos os campos !! <a href='http://localhost:8080/index.html'>Voltar</a>");
			r.write("</body>");
			r.write("</html>");
		}else
		{	
			FileInputStream entrada = new FileInputStream("C:/Users/Rodrigo-PC/Projetos/contato-sessao/src/main/users.txt");
			InputStreamReader formatado = new InputStreamReader(entrada);
			BufferedReader entradaLinha = new BufferedReader(formatado);
		
		
		
		boolean pertence = false;
		String linha = null;
		
			do{
				linha = entradaLinha.readLine(); // verifica cada linha 
				if( linha.contains(usuario) )
				{
					if( linha.contains(senha) )
					{
						HttpSession session = req.getSession();
						
						session.setAttribute("usuario", usuario);
						PrintWriter r = resp.getWriter();
						r.write(showContato(usuario));
						entrada.close();
						pertence = true;
						break;
					}
					
					PrintWriter r = resp.getWriter();
					r.write("Login Invalido");
					pertence = true;
					break;
				}
			}
			while(linha != null);	
			
			if( !pertence ){
				PrintWriter r = resp.getWriter();
				r.write("Login Invalido");
				}
		}
	}
	
	public String showContato( String login ) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		FileInputStream entrada = new FileInputStream("C:/Users/Rodrigo-PC/Projetos/contato-sessao/src/main/contatos.txt");
		InputStreamReader entradaFormatada = new InputStreamReader(entrada);
		BufferedReader entradaString = new BufferedReader(entradaFormatada);
		
		sb.append("<html lang='pt-br'>");
		sb.append("<head>");
		sb.append("<meta charset='Windows-1252'>");
		sb.append("<link rel='stylesheet' href='resources/bootstrap-3.3.5-dist/css/bootstrap.min.css'>");
		sb.append("<link rel='stylesheet' href='resources/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css'>");
		sb.append("<link rel='stylesheet' href='resources/style.css'>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div class='container'>");
		sb.append("<div class='panel panel-default'>");
		sb.append("<div class='panel-heading'>");
		sb.append("<h3 class='panel-title'>Contatos</h3>");
		sb.append("</div>");
		sb.append("<div class='panel-body'>");
		
		String linha = entradaString.readLine();
		
		sb.append("<form method='post' action='excluiContato'>");
		String[] contatosLista;
		while(linha != null)
		{
			if( linha.contains(login) )
			{
				int primeiro, ultimo;
		        primeiro = linha.indexOf("=") + 1;
		        ultimo = linha.lastIndexOf("");
		        contatosLista = linha.split(";");
				sb.append("<table class='table table-bordered'>"
						+ "<tr class='active'><td>Nome</td>" +
						  "<td>Telefone</td>" +	
						  "<td>Excluir</td>" +	
						  "</tr>" +
						  "<tr class='success'>" +
						  "<td>" 
						  +contatosLista[1]+
						  "</td>"+
						  "<td>" 
						  +contatosLista[2]+
						  "</td>"+
						  "<td>" +
						  "<input type='checkbox' name='contato' value=\""
								+linha.substring(primeiro, ultimo)+"\">"+
						  "</td>"+
						  "</tr>"+		
						  "</table>");
			}
			linha = entradaString.readLine();
		}
		entrada.close();
		sb.append("<button type='submit'>Excluir</button>");
		sb.append("</form>");		
		sb.append("<a href='index.html'><button type='button'> << Voltar</button></a>");
		sb.append("<button type='button'><a href='contato.html'>"
				+ "Cadastrar contato >> </a></button>");
		sb.append("</body>");
		sb.append("</html>");
		
		return sb.toString();
	}
}
