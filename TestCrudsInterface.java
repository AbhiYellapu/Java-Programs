// Testing database interface

import java.util.Scanner;
import java.io.*;

class TestCrudsInterface
{
	static iCruds oRecord;
	static Scanner read;
	static String dbName, tableName, className;

	public static void main(String args[]) throws Exception
	{
		if (args.length != 0)
		{
			className = args[0];
		}
		else
		{
			className = getClassName();
		}
		getDatabaseConfig();
		oRecord = (iCruds) Class.forName(className).getDeclaredConstructor(String.class, String.class).newInstance(dbName, tableName);
		menu();
	}

	static void getMenu() throws IOException
	{
		File fpMenu = new File("Menu.config");
		read = new Scanner(fpMenu);
		while (read.hasNext())
		{
			System.out.println(read.nextLine().trim());
		}
	}

	static void getDatabaseConfig() throws IOException
	{
		try
		{
			File fpDatabase = new File("db.config");
			read = new Scanner(fpDatabase);
			dbName = read.nextLine().trim();
			tableName = read.nextLine().trim();
			read.close();
		}
		catch(Exception error)
		{
			System.out.println(error);
		}
	}

	static String getClassName() throws IOException
	{
		File fpClassName = new File("Class.config");
		read = new Scanner(fpClassName);
		return read.nextLine().trim();
	}
	static void menu() throws Exception
	{
		while (true)
		{
			getMenu();
			read = new Scanner(System.in);
			cUserInterface record = new cUserInterface();
			System.out.print("Enter your option: ");
			int option = Integer.parseInt(read.nextLine());

			switch (option)
			{
				case 1: record.readFieldValues();
						oRecord.addRecord(record);
						record.showInsertedMessage(1);
						break;
				case 2: record.show(oRecord.fetchAll());
						break;
				case 0:	System.exit(0);
						break;
				default:System.out.println("Invalid input!");
			}
		}
	}

}
