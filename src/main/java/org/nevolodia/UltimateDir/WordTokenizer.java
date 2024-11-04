package org.nevolodia.UltimateDir;

import java.util.Arrays;
import java.util.List;

public class WordTokenizer implements Tokenizer
{
	/**
	 * Tokenizes input string to list of strings by non-word characters.
	 * @param input input to tokenize
	 * @return list of words
	 */
	@Override
	public List<String> tokenize(String input)
	{
		return Arrays.stream(input.split("\\W+")).toList();
	}
}
