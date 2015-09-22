package org.contato.servlets;

import java.io.*;
import java.io.FileInputStream;
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

@WebServlet(urlPatterns = "/contato")
public class ContatoServlets extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String nome = req.getParameter("nome");
		String telefone = req.getParameter("telefone");
		
		if(nome == "" || telefone == ""){
		PrintWriter r = resp.getWriter();
		r.write("<html>");
		r.write("<head>");
		r.write("</head>");
		r.write("<body>");
		r.write("Por Favor Preencha todos os campos !! <a href='http://localhost:8080/contato.html'>Voltar</a>");
		r.write("</body>");
		r.write("</html>");
		}else{
			// pegando da sessão do usuário e adicionando 
			addContato( (String)req.getSession().getAttribute("usuario"), nome, telefone );
				
			PrintWriter r = resp.getWriter();
			LoginServlets contato = new LoginServlets();
			r.write(contato.showContato((String)req.getSession().getAttribute("usuario")));
		}
	}
	
	private int linhaConta() throws IOException
	{	
		// lendo para adicionar ao id
		int contador = 1;
		FileInputStream entrada = new FileInputStream("C:/Users/Rodrigo-PC/Projetos/contato-sessao/src/main/contatos.txt");
		InputStreamReader entFormatada = new InputStreamReader(entrada);
		BufferedReader entString = new BufferedReader(entFormatada);
		String linha = entString.readLine();
		while( linha != null )
		{
			linha = entString.readLine();
			contador++;
		}
		return contador;
	}
	
	public void addContato(String nome, String contato, String telefone) throws IOException{
		Path path = Paths.get( "C:/Users/Rodrigo-PC/Projetos/contato-sessao/src/main/contatos.txt" );
		String conteudo = nome+";"+contato+";"+telefone+";id="+linhaConta()+"\n";
		Files.write( path, conteudo.getBytes(), StandardOpenOption.APPEND );
	}
	
}
