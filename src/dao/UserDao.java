package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import beans.Role;
import beans.User;

public class UserDao {

	
	
	//read users from file
	public static ArrayList<User> loadUsers(){
		ArrayList<User> allUsers=new ArrayList<>();
		SimpleDateFormat sdf=new SimpleDateFormat("dd.mm.yyyy");
		BufferedReader in=null;
		try {
			File file=new File(Constants.KEY_USERS_FILE);
			in=new BufferedReader(new FileReader(file));
			String line;
			while((line=in.readLine())!=null) {
				String tokens[]=line.split("\\|");
				String id=tokens[0];
				String username=tokens[1];
				String password=tokens[2];
				String name=tokens[3];
				String lastname=tokens[4];
				String role=tokens[5];
				String number=tokens[6];
				String email=tokens[7];
				Date date=sdf.parse(tokens[8]);
				
				User user=new User(Long.parseLong(id),username,password,name,lastname,Role.valueOf(role), number,email,date);
				
				allUsers.add(user);
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
		return allUsers;
	}
	
	
	//get User by username
	
	public static User getUserByUsername(String username) {
		ArrayList<User> allUsers=loadUsers();
		
		for(User u:allUsers) {
			if(u.getUsername().equals(username)){
				return u;
			}
		}
		//nema usera sa tim usernameom vrati null
		return null;
		
		
	}


	public static void save(User userFront) {
		System.out.println("save register");
		// TODO Auto-generated method stub
		ArrayList<User> allUsers=loadUsers();
		int size=allUsers.size();
		size=size+1;
		userFront.setId(Long.valueOf(size));
		System.out.println("ov je broj int"+userFront.getId());
		userFront.setRole(Role.kupac);
		userFront.setDateRegistration(new Date());
		allUsers.add(userFront);
		saveUserList(allUsers);
		
	}
	
	//save user to file
	public static void saveUserList(ArrayList<User> allUsers) {
		BufferedWriter  writer= null;
		try {
			writer =new BufferedWriter(new FileWriter(Constants.KEY_USERS_FILE));
			String save="";
			for(User u:allUsers) {
				save+=getUserFileFormat(u);
			}
			writer.write(save);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

		public static String getUserFileFormat(User user){
		
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			// mika|sifra2|mika|mikic|administrator|1255666|mika@gmail.com|12.05.2001|1
			
			return 		user.getId() + Constants.KEY_DB_SEPARATOR + 
					user.getUsername() + Constants.KEY_DB_SEPARATOR + 
						user.getPassword() + Constants.KEY_DB_SEPARATOR + 
						user.getName() + Constants.KEY_DB_SEPARATOR + 
						user.getLastname() + Constants.KEY_DB_SEPARATOR + 
						user.getRole() + Constants.KEY_DB_SEPARATOR + 
						user.getNumber() + Constants.KEY_DB_SEPARATOR + 
						user.getEmail()  + Constants.KEY_DB_SEPARATOR + 
						sdf.format(user.getDateRegistration()) + Constants.KEY_DB_SEPARATOR +  "\n";
		}


		public static void changeRole(String username, String role) {
			// TODO Auto-generated method stub
		
			ArrayList<User> allUsers=loadUsers();
			for(User u:allUsers) {
				if(u.getUsername().equals(username)) {
					System.out.println("OVO JE ROLA"+role);
					switch(role) {
					case "0": u.setRole(Role.kupac);
						break;
					case "1":  u.setRole(Role.administrator);
						break;
					case "2": u.setRole(Role.dobavljac);
						break;
					
					}
					saveUserList(allUsers);
				}
			}
			
			
		}


		
	
	
}
