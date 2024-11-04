package org.nevolodia.UltimateDir;

import java.util.*;

public class DirScanner
{
	private final String path;
	private final Tokenizer tokenizer;
	private Map<String, Set<String>> tokenizedFiles = new HashMap<>();
	private List<String> fileList;
	
	/**
	 * Constructor which initializes UltimateDir with given path.
	 * and runs initial path and file indexing.
	 * @param path path
	 * @param tokenizer which tokenizes strings
	 */
	public DirScanner(String path, Tokenizer tokenizer)
	{
		this.path = path;
		this.tokenizer = tokenizer;
	}
	
	/**
	 * Returns current path.
	 * @return path
	 */
	public String getPath()
	{
		return this.path;
	}
	
	/**
	 * Returns whether path exist.
	 * @return does path exist
	 */
	public boolean doesExist()
	{
		return DirWorker.dirExists(path);
	}
	
	/**
	 * Indexes path and returns does new file list in path differ from previous indexing.
	 * @return does new file list in path differ from previous indexing
	 */
	public boolean indexDirectory()
	{
		List<String> oldFileList = fileList;
		fileList = DirWorker.indexDir(path);
		return oldFileList != null && oldFileList.equals(fileList);
	}
	
	/**
	 * Indexes files in dir.
	 */
	public void indexFiles()
	{
		tokenizedFiles = DirWorker.tokenizeFilesInDir(path, tokenizer);
	}
	
	/**
	 * Returns list of files in path.
	 * @return list of files in path
	 */
	public List<String> getFiles()
	{
		return fileList;
	}
	
	/**
	 * Returns list of file names which contain given string.
	 * @param content word to match
	 * @return list of file names which contain given string
	 */
	public List<String> getFilesContaining(String content)
	{
		List<String> matchingFiles = new ArrayList<>();
		
		for (Map.Entry<String, Set<String>> entry : tokenizedFiles.entrySet())
		{
			Set<String> tokens = entry.getValue();
			if (tokens.contains(content))
			{
				matchingFiles.add(entry.getKey());
			}
		}
		
		return matchingFiles;
	}
	
	@Override
	public String toString()
	{
		return "DirScanner{" +
				"path='" + path + '\'' +
				", tokenizer=" + tokenizer +
				", tokenizedFiles=" + tokenizedFiles +
				", fileList=" + fileList +
				'}';
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DirScanner that = (DirScanner) o;
		return Objects.equals(path, that.path);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hashCode(path);
	}
}
