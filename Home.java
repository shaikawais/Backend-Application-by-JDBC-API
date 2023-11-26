package swiggy;

import java.util.*;

public class Home {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println(
				"----- Are you a Buyer or Seller -----\n\nEnter 1 if you are a Buyer\nEnter 2 if you are a Seller\nEnter 0 to Exit");
		String opt = sc.next();
		if (opt.equals("1"))
			BuyerLoginSignup.buyer();
		else if (opt.equals("2"))
			SellerLoginSignup.seller();
		else if (opt.equals("0")) {
			System.out.println("Thank You Visit Again");
			System.exit(0);
		} else {
			System.out.println("Invalid Input Please Try Again \n");
			main(null);
		}
	}
}
