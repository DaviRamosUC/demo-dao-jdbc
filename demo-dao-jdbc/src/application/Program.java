package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {

//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		Scanner sc = new Scanner(System.in);

		Department obj = new Department(1, "Books");
		System.out.println(obj);

		Seller obj2 = new Seller(1, "João", "joão@", new Date(), 2000.00, obj);
		System.out.println(obj2);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
//		sc.close();
	}

}
