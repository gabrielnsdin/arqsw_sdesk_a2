package br.usjt.arqsw.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



import br.usjt.arqsw.entity.Usuario;


@Repository
public class UsuarioDAO {
	private Connection conn;
	
	@Autowired
	public UsuarioDAO(DataSource dataSource) throws IOException {
		try {
			this.conn = dataSource.getConnection();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}
	
	public Usuario validarUsuario(Usuario usuarioLogin) throws IOException {
		Usuario usuario;
		
		String query = "select id_usuario, username, password from usuario "
				+ "where username=? and password=?";
				
		try(PreparedStatement pst = conn.prepareStatement(query);){
			pst.setString(1,usuarioLogin.getUsername());
			pst.setString(2,usuarioLogin.getPassword());
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id_usuario"));
				usuario.setUsername(rs.getString("username"));
				usuario.setPassword(rs.getString("password"));
			}else {
				usuario = null;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return usuario;
		
	}
	
	
}
