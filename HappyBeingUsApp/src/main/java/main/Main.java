package main;
import DAO.UserDao;
import User.User;

public class Main {

	public static void main(String[] args) {
		UserDao dao= new UserDao();
		User user=dao.findUser(1);
		System.out.println(user.getPassword()+user.getUsername()+user.getuId());

	}

}
