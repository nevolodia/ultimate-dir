package org.nevolodia;

import org.nevolodia.UltimateDir.Tokenizer;
import org.nevolodia.UltimateDir.DirScanner;
import org.nevolodia.UltimateDir.WordTokenizer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Tokenizer tokenizer = new WordTokenizer();
		Scanner scanner = new Scanner(System.in);
		DirScanner dirScanner = null;
		
		// Get starting path
		while (true)
		{
			System.out.print("Please, input starting path: ");
			String path = scanner.nextLine();
			dirScanner = new DirScanner(
					path,
					tokenizer
			);
			
			if (dirScanner == null || !dirScanner.doesExist())
			{
				System.out.println("Invalid path... Please, retry...");
			}
			else
			{
				break;
			}
		}
		
		// Ask user for command and process it
		printMenu();
		while (true)
		{
			String input = scanner.nextLine();
			
			if (input.startsWith("cd "))
			{
				dirScanner = navigateToNewPath(input, tokenizer, dirScanner);
			}
			else if (input.startsWith("contains "))
			{
				listFilesContaining(dirScanner, input);
			}
			else if (input.equals("this"))
			{
				System.out.println(dirScanner.getPath());
			}
			else if (input.equals("../"))
			{
				dirScanner = navigateToParentPath(dirScanner, tokenizer);
			}
			else if (input.equals("list"))
			{
				listFiles(dirScanner);
			}
			else if (input.equals("exit"))
			{
				return;
			}
			else
			{
				System.out.println("Invalid command....\n");
				printMenu();
			}
		}
	}
	
	private static void listFiles(DirScanner dirScanner)
	{
		dirScanner.indexDirectory();
		List<String> files = dirScanner.getFiles();
		
		if (files.isEmpty())
		{
			System.out.println("No files in this directory...");
			return;
		}
		
		for (String file : files)
		{
			System.out.println(file);
		}
	}
	
	private static void listFilesContaining(DirScanner dirScanner, String input)
	{
		dirScanner.indexFiles();
		List<String> files = dirScanner.getFilesContaining(input.substring(9));
		
		if (files.isEmpty())
		{
			System.out.println(
					"No files containing \"" +
					input.substring(9) +
					"\"in this directory..."
			);
			return;
		}
		
		for (String file : files)
		{
			System.out.println(file);
		}
	}
	
	private static DirScanner navigateToParentPath(DirScanner dirScanner, Tokenizer tokenizer)
	{
		Path parentPath = Paths.get(dirScanner.getPath()).getParent();
		if (parentPath != null)
		{
			DirScanner newDirScanner = new DirScanner(parentPath.toString(), tokenizer);
			if (newDirScanner.doesExist())
			{
				dirScanner = newDirScanner;
			}
			else
			{
				System.out.println("Invalid path...");
			}
		}
		else
		{
			System.out.println("Already in home directory...");
		}
		return dirScanner;
	}
	
	private static DirScanner navigateToNewPath(String input, Tokenizer tokenizer, DirScanner dirScanner)
	{
		DirScanner newDirScanner = new DirScanner(input.substring(3), tokenizer);
		if (newDirScanner.doesExist())
		{
			dirScanner = newDirScanner;
		}
		else
		{
			System.out.println("Invalid path...");
		}
		return dirScanner;
	}
	
	private static void printMenu()
	{
		System.out.println(
				"Supported commands are:\n" +
				"\tcd - select a new path,\n" +
				"\tlist - list files in the directory,\n" +
				"\tthis - print current directory,\n" +
				"\tcontains x - list files containing word x,\n" +
				"\t../ - navigate to parent directory,\n" +
				"\texit - exit the application.\n"
		);
	}
}