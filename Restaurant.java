package swiggy;

import java.sql.*;
import java.util.*;

import com.mysql.cj.xdevapi.Result;

import dao.Food;

public class Restaurant {

	static Scanner sc = new Scanner(System.in);
	static ResultSet rs1;
	static int id = 0;
	static String bill = "";
	static double bill_cost = 0;

	public static void show() {
		System.out.println(
				"What Type of Food You Want to Order \n1 - Enter 1 for Veg \n2 - Enter 2 For Non-Veg \n3 - Enter 3 to go Back \n0 - Enter 0 to Exit");
		String opt = sc.next();
		if (opt.equals("1")) {
			showVeg();
		} else if (opt.equals("2")) {
			showNonveg();
		} else if (opt.equals("3"))
			BuyerLoginSignup.buyer();
		else if (opt.equals("0")) {
			System.out.println("Thank You Visit Again");
			System.exit(0);
		} else {
			System.out.println("Invalid Input Please Try Again");
			show();
		}
	}

	public static void showVeg() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/swiggy?createDatabaseIfNotExist=true", "root", "root");
			PreparedStatement ps = con.prepareStatement("select * from veg_restaurants");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getDouble(5));
			}
			System.out.println("Enter the Restaurant Id to See the Menu : ");
			System.out.println("Enter 1 to go Back \nEnter 0 to Exit");
			String opt = sc.next();
			int resId = 0;
			for (int i = 0; i < opt.length(); i++) {
				if (opt.charAt(i) >= '0' && opt.charAt(i) <= '9') {
					resId = Integer.parseInt(opt);
				} else {
					System.out.println("Invalid Input Please Try Again");
					showVeg();
				}
			}
			PreparedStatement ps1 = con.prepareStatement("select * from veg_restaurants where rcode=?");
			ps1.setInt(1, resId);
			rs1 = ps1.executeQuery();
			if (Restaurant.rs1.next()) {
				showMenu();
			} else if (opt.equals("1")) {
				show();
			} else if (opt.equals("0")) {
				System.out.println("Thank You Visit Again");
				System.exit(0);
			} else {
				System.out.println("Invalid Input Please Try Again");
				showVeg();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showNonveg() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/swiggy?createDatabaseIfNotExist=true", "root", "root");
			PreparedStatement ps = con.prepareStatement("select * from nonveg_restaurants");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getDouble(5));
			}
			System.out.println("Enter the Restaurant Id to See the Menu : ");
			System.out.println("Enter 1 to go Back \nEnter 0 to Exit");
			String opt = sc.next();
			int resId = 0;
			for (int i = 0; i < opt.length(); i++) {
				if (opt.charAt(i) >= '0' && opt.charAt(i) <= '9') {
					resId = Integer.parseInt(opt);
				} else {
					System.out.println("Invalid Input Please Try Again");
					showNonveg();
				}
			}
			PreparedStatement ps1 = con.prepareStatement("select * from nonveg_restaurants where rcode=?");
			ps1.setInt(1, resId);
			rs1 = ps1.executeQuery();
			if (rs1.next()) {
				showMenu();
			} else if (opt.equals("1")) {
				show();
			} else if (opt.equals("0")) {
				System.out.println("Thank You Visit Again");
				System.exit(0);
			} else {
				System.out.println("Invalid Input Please Try Again");
				showNonveg();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showMenu() {
		try {
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/swiggy?createDatabaseIfNotExist=true", "root", "root");
			PreparedStatement ps2 = con.prepareStatement("select * from " + rs1.getString(2));
			ResultSet rs2 = ps2.executeQuery();
			while (rs2.next()) {
				System.out.println(rs2.getInt(1) + "\t" + rs2.getString(2) + "\t" + rs2.getDouble(3) + "\t"
						+ rs2.getInt(4) + "\t" + rs2.getInt(5));
			}
			System.out.println("Enter The Food Id You Want to Order : ");
			System.out.println("Enter 99 to go Back to Previous Menu \nEnter 0 to Exit");
			String opt1 = sc.next();
			for (int i = 0; i < opt1.length(); i++) {
				if (opt1.charAt(i) >= '0' && opt1.charAt(i) <= '9') {
					id = Integer.parseInt(opt1);
				} else {
					System.out.println("Invalid Input Please Try Again");
					showMenu();
				}
			}
			PreparedStatement ps3 = con.prepareStatement("select * from " + rs1.getString(2) + " where id=?");
			ps3.setInt(1, id);
			ResultSet rs3 = ps3.executeQuery();
			if (rs3.next()) {
				bill();
			} else if (opt1.equals("99")) {
				show();
			} else if (opt1.equals("0")) {
				System.out.println("Thank You Visit Again");
				System.exit(0);
			} else {
				System.out.println("Invalid Input Please Try Again");
				showMenu();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void bill() {
		try {
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/swiggy?createDatabaseIfNotExist=true", "root", "root");
			PreparedStatement ps = con.prepareStatement("select * from " + rs1.getString(2));
			ResultSet rs = ps.executeQuery();
			List<swiggy.Food> f = new ArrayList<>();
			while (rs.next()) {
				swiggy.Food f1 = new swiggy.Food();
				f1.setId(rs.getInt(1));
				f1.setName(rs.getString(2));
				f1.setCost(rs.getDouble(3));
				f1.setQnty(rs.getInt(4));
				f1.setDiscount(rs.getInt(5));
				f.add(f1);
			}
			swiggy.Food f2 = f.get(id - 1);
			System.out.println("Enter the Quantity");
			String qnty = sc.next();
			double qnt = 0;
			boolean b = true;
			while (b) {
				for (int i = 0; i < qnty.length(); i++) {
					if (qnty.charAt(i) >= '0' && qnty.charAt(i) <= '9') {
						b = false;
						qnt = Double.parseDouble(qnty);
					} else {
						System.out.println("Invalid Input Please Try Again");
					}
				}
			}
			bill += f2.getName() + "\t:\t" + f2.getCost() + " * " + qnty + "\t=\t" + (f2.getCost() * qnt) + "\n";
			bill_cost = (f2.getCost() * qnt) - ((f2.getCost() * qnt) / f2.getDiscount());
			bill += "Discount of " + f2.getDiscount() + "% \tTotal Cost\t:\t" + bill_cost + "\n\n";
			System.out.println("Do you want to Order any other Item");
			System.out.println("Enter 1 to add the Items \nEnter 2 to Proceed to Payment");
			String inp = sc.next();
			if (inp.equals("1"))
				showMenu();
			else if (inp.equals("2")) {
				System.out.println(bill);
				System.out.println("Your Order Has Been Placed Successfully.....");
			} else {
				System.out.println("Invalid Input Please Try Again");
				showMenu();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
