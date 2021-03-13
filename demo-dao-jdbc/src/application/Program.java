package application;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		Scanner sc = new Scanner(System.in);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		dataAcessSellerTest(sc, sellerDao);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		dataAcessDepartmentTest(sc, departmentDao);
		
		sc.close();
		
	}

	private static void dataAcessDepartmentTest(Scanner sc, DepartmentDao departmentDao) {
		System.out.println("===== TEST 1: department Insert =====");
		departmentDao.insert(new Department(7, "Música"));
		System.out.println("Inserted Department!");
		
		System.out.println();
		System.out.println("===== TEST 2: department FindByID =====");
		System.out.println(departmentDao.findById(1));

		System.out.println();
		System.out.println("===== TEST 3: department DeleteByID =====");
		System.out.print("Enter id for delete Department: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Department deleted!");
		
		System.out.println();
		System.out.println("===== TEST 4: department UpdateByID =====");
		departmentDao.update(new Department(3, "Toys"));
		System.out.println("Update completed!");

		System.out.println();
		System.out.println("===== TEST 5: department FindAll =====");
		List<Department> departments = departmentDao.findAll();
		for (Department department : departments) {
			System.out.println(department);
		}
		System.out.println("Process completed!");
	}

	private static void dataAcessSellerTest(Scanner sc, SellerDao sellerDao) {
		System.out.println("===== TEST 1: seller findById =====");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println();
		System.out.println("===== TEST 2: seller findByDepartment =====");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println();
		System.out.println("===== TEST 3: seller findAll =====");
		list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println();
		System.out.println("===== TEST 4: seller insert =====");
		Seller newSeller = new Seller(null, "Greg", "greg@", new Date(), 4000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = "+ newSeller.getId());

		System.out.println();
		System.out.println("===== TEST 5: seller update =====");
		seller = sellerDao.findById(2);
		seller.setName("Marta Waine");
		sellerDao.update(seller);
		System.out.println("Update completed!");

		System.out.println();
		System.out.println("===== TEST 6: seller delete =====");
		System.out.print("Enter id for delete Seller: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete completed!");
	}

}
