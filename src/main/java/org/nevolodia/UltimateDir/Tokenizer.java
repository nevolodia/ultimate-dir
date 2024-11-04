package org.nevolodia.UltimateDir;

import java.util.List;

public interface Tokenizer
{
	/**
	 * Tokenizer function for splitting string into list of strings.
	 * @param input input to tokenize
	 * @return tokenized list of strings
	 */
	List<String> tokenize(String input);
}
