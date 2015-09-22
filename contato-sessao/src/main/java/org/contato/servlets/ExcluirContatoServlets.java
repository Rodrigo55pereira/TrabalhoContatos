package org.contato.servlets;

import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/excluiContato")
public class ExcluirContatoServlets extends HttpServlet{

	private static final long	serialVersionUID = 1L;
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	

		
		String[] cod = req.getParameterValues("contato");
		
		
		if( cod == null )
		{
			PrintWriter r = resp.getWriter();
			LoginServlets contato = new LoginServlets();
			r.write(contato.showContato((String)req.getSession().getAttribute("usuario")));
		
		}else{					    
			deletaContatos(cod);
			PrintWriter r = resp.getWriter();
			LoginServlets contato = new LoginServlets();
			r.write(contato.showContato((String)req.getSession().getAttribute("usuario")));
		}
	}
	
	// método para deletar os contatos 
	private void deletaContatos( String[] cod ) throws IOException
	{
		File entrada = new File("C:/Users/Rodrigo-PC/Projetos/contato-sessao/src/main/contatos.txt");
		FileReader entFormatada = new FileReader(entrada);
		BufferedReader entString = new BufferedReader(entFormatada);
		
		int i = 0;
		
		String linha = entString.readLine();
		ArrayList<String> contatosArrays = new ArrayList<String>();
		
		while(linha != null) // verifica todo o arquivo
		{
			if( i < cod.length && linha.contains( "id="+cod[i]) ) 
			{	
				linha = entString.readLine();
				i++;
			}else{
				contatosArrays.add(linha); 
				linha = entString.readLine();
			}
		}
		
		entFormatada.close(); // fecha
		entString.close(); // fecha
		FileWriter f = new FileWriter(entrada, true);
		f.close(); // fecho
		
		FileWriter f2 = new FileWriter(entrada);
		BufferedWriter b = new BufferedWriter(f2);
		
		for ( i = 0; i < contatosArrays.size(); i++ ) 
		{
			b.write( contatosArrays.get(i) );
			b.newLine();
		}
		
		b.close(); // fecha 
		f2.close(); // fecha
	}

}
