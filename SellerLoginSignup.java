package swiggy;

import java.sql.*;
import java.util.*;

public class SellerLoginSignup {
	static Scanner sc = new Scanner(System.in);
	static int c = 1;

	public static void seller() {
		System.out.println(
				"1 - Enter 1 to Login \n2 - Enter 2 to SignUp \n3 - Enter 3 to go to Previous Menu \n0 - Enter 0 to Exit");
		String opt = sc.next();
		if (opt.equals("1"))
			login();
		else if (opt.equals("2"))
			signup();
		else if (opt.equals("3"))
			Home.main(null);
		else if (opt.equals("0")) {
			System.out.println("Thank You Visit Again");
			System.exit(0);
		} else {
			System.out.println("Invalid Input Please Try Again");
			seller();
		}
	}

	public static void signup() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/swiggy?createDatabaseIfNotExist=true", "root", "root");
			PreparedStatement ps = con.prepareStatement("insert into seller_details values(?,?,?,?,?,?,?)");
			System.out.println("Enter the Following Details to Sign Up : ");
			System.out.println("Enter Your Id : ");
			int id = sc.nextInt();
			System.out.println("Enter Your Restaurant Name : ");
			sc.nextLine();
			String res_name = sc.nextLine();
			System.out.println("Enter Address : ");
			String add = sc.nextLine();
			System.out.println("Enter Email Id : ");
			String email = sc.next();
			System.out.println("Enter Mobile Number : ");
			String phno = sc.next();
			System.out.println("Enter New Password : ");
			String pwd1 = sc.next();
			System.out.println("Confirm Password : ");
			String pwd2 = sc.next();
			System.out.println("Security Question : What is Your Pet Name ?");
			String sq = sc.next();
			PreparedStatement ps1 = con.prepareStatement("select * from seller_details where emailid=?");
			ps1.setString(1, email);
			ResultSet rs = ps1.executeQuery();
			if (!rs.next()) {
				ps.setInt(1, id);
				if (Validation.validateName(res_name)) {
					ps.setString(2, res_name);
				} else {
					System.out.println("Invalid Name - Your Name Should Not Contain Any Digits \nPlease Try Again");
					signup();
				}
				ps.setString(3, add);
				if (Validation.validateEmail(email)) {
					ps.setString(4, email);
				} else {
					System.out
							.println("Invalid Email - Your Email Id must Contains @gmail.com at Last Please Try Again");
					signup();
				}
				if (Validation.validatePhno(phno)) {
					ps.setString(5, phno);
				} else {
					System.out.println(
							"Invalid Phone Number - Your Phone Number Must Contains 10 Digits Please Try Again");
					signup();
				}
				if (Validation.validatePwd(pwd1)) {
					ps.setString(6, pwd1);
				} else {
					System.out.println(
							"Invalid Password - Password must Contain atleast 1 Special character, UpperCase Alphabet, LowerCase Alphabet, and a Digit \nPlease Try Again");
					signup();
				}
				ps.setString(7, sq);
				if (!pwd1.equals(pwd2)) {
					System.out.println("Password Not Matched Please Try Again");
					signup();
				} else {
					ps.execute();
					System.out.println("Account Created Successfully \nPlease Login to Continue.....");
					login();
				}
			} else {
				System.out.println(
						"Account with this email Already Exists \nEnter 1 to Login \nEnter 2 to Create New Account");
				String opt = sc.next();
				if (opt.equals("1")) {
					login();
				} else if (opt.equals("2")) {
					signup();
				} else {
					System.out.println("Invalid Input Please Try Again");
					seller();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void login() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/swiggy?createDatabaseIfNotExist=true", "root", "root");
			PreparedStatement ps = con.prepareStatement("select * from seller_details where emailid=?");
			System.out.println("Enter the Following Details to Proceed : ");
			System.out.println("Enter your EmailId : ");
			String email = sc.next();
			System.out.println("Enter Password : ");
			String pwd = sc.next();
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (!pwd.equals(rs.getString(6))) {
					System.out.println("Incorrect Password \nEnter 1 to Reset Password \nEnter 2 to Try Again");
					String opt = sc.next();
					if (opt.equals("1"))
						resetPwd();
					else if (opt.equals("2"))
						login();
					else {
						System.out.println("Invalid Input Please Try Again");
						login();
					}
				} else {
					System.out.println("Login Successfull");
				}
			} else {
				System.out.println(
						"Account Doesn't Exist With this Email \nEnter 1 to Login \nEnter 2 to Create New Account");
				String opt = sc.next();
				if (opt.equals("1")) {
					login();
				} else if (opt.equals("2")) {
					signup();
				} else {
					System.out.println("Invalid Input Please Try Again");
					seller();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void resetPwd() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/swiggy?createDatabaseIfNotExist=true", "root", "root");
			PreparedStatement ps = con.prepareStatement("select * from seller_details where emailid=?");
			System.out.println("Enter the Following Details to Proceed : ");
			System.out.println("Enter Your Registered Email ID : ");
			String email = sc.next();
			System.out.println("Security Question : What is Your Pet Name ?");
			String sq = sc.next();
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (c < 3) {
					if (sq.equals(rs.getString(7))) {
						while (true) {
							PreparedStatement ps1 = con
									.prepareStatement("update seller_details set password=? where emailid=?");
							System.out.println("Enter New Password : ");
							String new_pwd1 = sc.next();
							System.out.println("Confirm Password : ");
							String new_pwd2 = sc.next();
							if (new_pwd1.equals(new_pwd2)) {
								ps1.setString(1, new_pwd2);
								ps1.setString(2, email);
								ps1.execute();
								break;
							} else {
								System.out.println("Password Doesn't Match, Please try Again");
							}
						}
						System.out.println("Password updated Successfully Please Login to Continue.....");
						login();
					} else {
						System.out.println("Wrong Answer! Please Try Again");
						c++;
						resetPwd();
					}
				} else {
					PreparedStatement ps2 = con.prepareStatement("delete from seller_details where emailid=?");
					ps2.setString(1, email);
					ps2.execute();
					System.out.println(
							"Your Account Has Been Blocked Due to Multiple Wrong Attempts \nPlease Create New Account to Continue.....");
					signup();
				}
			} else {
				System.out.println(
						"Account Doesn't Exists With This Email Id \nEnter 1 to Try Again \nEnter 2 to go Back");
				String opt = sc.next();
				if (opt.equals("1"))
					resetPwd();
				else if (opt.equals("2"))
					seller();
				else {
					System.out.println("Invalid Input Please Try Again");
					seller();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
