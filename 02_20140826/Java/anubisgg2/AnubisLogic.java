package anubisgg2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnubisLogic {

	public static void main(String[] args) {
		String method;
		if (args.length == 0) {
			method = "2";
		} else {
			method = args[0];
		}

		Scanner scanner = getScannerByFile("Data.csv");
		if (scanner == null) {
			return;
		}
		//		Scanner scanner = getScannerByStdin();
		scanner.nextLine(); // skip header
		AnubisObject ano = AnubisObject.anubisize(scanner);
		scanner.close();

		if ("1".equals(method)) {
			Anubisizable an = new Anubisizer1();
			an.write(ano);
		} else if ("2".equals(method)) {
			Anubisizable an = new Anubisizer2();
			an.write(ano);
		}
	}

	private static Scanner getScannerByFile(String path) {
		File f = new File(path);
		Scanner scanner;
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return scanner;
	}

	private static Scanner getScannerByStdin() {
		return new Scanner(System.in);
	}
}
