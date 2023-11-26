package swiggy;

import java.util.*;

public class Validation {

	public static boolean validatePwd(String s) {
		String sp = "!@#$%^&*( )_-<>?/.,:;";
		boolean cuc = false, clc = false, cd = false, csc = false;
		if (s.length() >= 8 && s.length() <= 16) {
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
					cuc = true;
				if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z')
					clc = true;
				if (s.charAt(i) >= '0' && s.charAt(i) <= '9')
					cd = true;
				if (sp.indexOf(s.charAt(i)) != -1)
					csc = true;
			}
			if (cuc && clc && cd && csc)
				return true;
			else
				return false;
		} else
			return false;
	}

	public static boolean validateName(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
				return false;
			}
		}
		return true;
	}

	public static boolean validatePhno(String s) {
		if (s.length() == 10) {
			for (int i = 0; i < s.length(); i++) {
				if ((s.charAt(i) >= '0' && s.charAt(i) <= '9') && (s.charAt(0) <= '9' && s.charAt(0) >= '6'))
					return true;
			}
			return false;
		} else
			return false;
	}

	public static boolean validateEmail(String s) {
		String email = "@gmail.com";
		if (s.contains(email)) {
			return true;
		}
		return false;
	}
}
