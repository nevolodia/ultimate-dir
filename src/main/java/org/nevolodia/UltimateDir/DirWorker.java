package org.nevolodia.UltimateDir;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DirWorker
{
	/**
	 * Returns whether path exists.
	 * @param pathString path
	 * @return does path exist
	 */
	public static boolean dirExists(String pathString)
	{
		Path path = Paths.get(pathString);
		return Files.isDirectory(path);
	}
	
	/**
	 * Indexes filepath and returns list of files in it.
	 * @param pathString path
	 * @return list of files in filepath
	 */
	public static List<String> indexDir(String pathString)
	{
		List<String> fileNameList = new ArrayList<>();
		
		if (!dirExists(pathString))
			return fileNameList;
		
		Path path = Paths.get(pathString);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path))
		{
			for (Path entry : stream) {
				if (Files.isRegularFile(entry))
				{
					fileNameList.add(entry.getFileName().toString());
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return fileNameList;
		}
		
		return fileNameList;
	}
	
	/**
	 * Tokenizes file at given path and returns result as set of strings.
	 * @param path path to the file
	 * @param tokenizer tokenized
	 * @return tokenized file as set of strings
	 */
	private static Set<String> tokenizeFile(String path, Tokenizer tokenizer)
	{
		Set<String> tokens = new HashSet<>();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null)
			{
				tokens.addAll(tokenizer.tokenize(line));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return tokens;
	}
	
	/**
	 * Returns map of file path corresponding to its tokenized content.
	 * @param path path to the dir
	 * @param tokenizer tokenized
	 * @return map of file path corresponding to its tokenized content.
	 */
	public static Map<String, Set<String>> tokenizeFilesInDir(String path, Tokenizer tokenizer)
	{
		Map<String, Set<String>> tokenizedFiles = new HashMap<>();
		
		List<String> files = indexDir(path);
		if (files == null)
		{
			return tokenizedFiles;
		}
		
		for (String file : files)
		{
			String fullPath = Paths.get(path, file).toString();
			Set<String> tokens = tokenizeFile(fullPath, tokenizer);
			if (tokens != null)
			{
				tokenizedFiles.put(file, tokens);
			}
		}
		
		return tokenizedFiles;
	}
}
