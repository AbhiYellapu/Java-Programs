// UI for CRUDS

import java.util.Scanner;
import java.io.*;
import java.util.*;

public class cUserInterface
{
	String fieldNames[];
	String fieldValues[];
	int fieldsCount;
	Scanner read;

	public cUserInterface() throws Exception
	{
		read = new Scanner(System.in);
		getFieldNames();
		fieldValues = new String[fieldsCount];
	}

	void printLine(int lineLength)
	{
		System.out.println("-".repeat(lineLength));
	}
	public void show(cUserInterface[] records)
	{
		printLine(fieldsCount * 20);
		for (int columnCounter = 0; columnCounter < fieldsCount; columnCounter++)
		{
			System.out.printf("%-20s ", fieldNames[columnCounter]);
		}
		System.out.println();
		printLine(fieldsCount * 20);
		for (int rowCounter = 0; rowCounter < records.length; rowCounter++)
		{
			for (int columnCounter = 0; columnCounter < fieldsCount; columnCounter++)
			{
				System.out.printf("%-20s ", records[rowCounter].fieldValues[columnCounter]);
			}
			System.out.println();
		}
	}

	int getFieldsCount(String fieldsFileName) throws Exception
	{
		int fieldsCount = 0;
		File fpFields = new File(fieldsFileName);
		Scanner readCount = new Scanner(fpFields);
		while (readCount.hasNextLine()) 
		{
            readCount.nextLine();
            fieldsCount++;
        }
        return fieldsCount;
	}

	void getMenu() throws IOException
	{
		File fpMenu = new File("Menu.config");
		read = new Scanner(fpMenu);
		while (read.hasNext())
		{
			System.out.println(read.nextLine().trim());
		}
	}


	void getFieldNames() throws Exception
	{
		String fieldsFileName = "Fields.config";
		fieldsCount = getFieldsCount(fieldsFileName);
		fieldNames = new String[fieldsCount];

		File fpFields = new File(fieldsFileName);
		Scanner readFields = new Scanner(fpFields);
		int counter = 0;
		while (readFields.hasNextLine()) 
		{
            fieldNames[counter] = readFields.nextLine();
            counter++;
        }
	}

	public void readFieldValues()
	{
		for (int counter = 0; counter < fieldsCount; counter++)
		{
			System.out.print("Enter " + fieldNames[counter] + ": ");
			fieldValues[counter] = read.nextLine();
		}
	}

	public void showInsertedMessage(int insertedStatus)
	{
		if (insertedStatus == 1)
		{
			System.out.println(this.fieldValues[1] + " inserted successfully!");
		}
		else
		{
			System.out.println(this.fieldValues[1] + " failed to insert!");
		}
	}
}
