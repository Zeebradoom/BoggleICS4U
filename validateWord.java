import java.util.Stack;

/**
 * Object that checks if the word entered is a valid English word by checking
 * the dictionary text file as well as recursively finding the word in the
 * board.
 * 
 * @param guess                 -> the guessed word the user makes (string)
 * @param board                 --> the current boggle board (String 1D array)
 * @param startLetterDictionray --> the position of each letter (int array)
 * @param dictionary            --> holds the dictionary text file (String
 *                              array)
 * @param minLength             --> the minimum length of words
 * @return validate --> returns if the word is valid in the dictionary and the
 *         board
 */
public class validateWord {

	private String word;
	private String board[];
	private int startLetterDictionary[];
	private String dictionary[];
	private int minLength;
	private int posLetters[];

	public validateWord(String word, String board[], int startLetterDictionary[], String dictionary[], int minLength) {
		this.word = word;
		this.board = board;
		this.startLetterDictionary = startLetterDictionary;
		this.dictionary = dictionary;
		this.minLength = minLength;
		posLetters = new int[this.word.length()];
	}

	// returns if the word is a valid english word in the dictionary text file and
	// if it is a long enough word
	public boolean validateDicLen() {
		int startLetter = word.charAt(0) - 'A';
		System.out.println(startLetter);
		if (word.length() < minLength)
			return false;
		return recursivelyValidateWord(dictionary, word, startLetterDictionary[startLetter],
				startLetterDictionary[startLetter + 1]);
	}

	// recursive program that returns if the word is found in the dictionary or not
	public boolean recursivelyValidateWord(String[] dictionary, String target, int Min, int Max) {
		int middle = (Min + Max) / 2;
		if (Max < Min)
			return false;
		if (dictionary[middle].equals(target)) {
			return true;
		} else if (dictionary[middle].compareTo(target) > 0)
			return recursivelyValidateWord(dictionary, target, Min, middle - 1);
		else
			return recursivelyValidateWord(dictionary, target, middle + 1, Max);
	}

	private static Stack<Integer> stack = new Stack<Integer>(); // holds previous indexes called
	int totalLetters = 0;
	private static boolean directions = false;
	private static int index = 1;

	// recursively validates if the word is on the board and also returns the
	// position in the board array of the letters
	public int[] validateBoard() {
		index = 1;
		for (int i = 0; i < word.length(); i++) {
			posLetters[i] = -1;
		}

		for (int i = 0; i < 25; i++) {
			if (board[i].equals(word.substring(0, 1))) {
				posLetters[0] = i;
				System.out.println("board index: " + i);
				stack.clear();
				stack.push(i);
				if (recursiveBoard(i, i)) {
					posLetters[0] = i;
					System.out.println(i);
					return posLetters;
				}
				index = 1;
			}
		}
		return posLetters;
	}

	public boolean recursiveBoard(int i, int startI) {
	//	if (posLetters[word.length() - 1] != -1) {
	//		return true;
	//	}
		
		if(index >= word.length()) {
			System.out.println("test valid");
			return true;
		}
		
		System.out.println(word.charAt(index) + " index: " + index);
		
		// up + left
		if (i - 6 >= 0 && i % 5 != 0 && board[i-6].charAt(0) == word.charAt(index)) { // checks if the up & left direction is valid, and then checks if the letter in that spot is equal to the current index of the word
			System.out.println("up left from " + i + " to " + board[i-6]);
			posLetters[index] = i-6; // adds the new valid index to posLetters
			if(index >= word.length()-1) { // checks if we are checking the last index of the original word, then the word is found
				return true;
			} else {
				index++; // continue to next letter
				directions = true;
				stack.push(i);
				return recursiveBoard(i - 6, startI);
			}
		}
		// up
		if (i - 5 >= 0 && board[i-5].charAt(0) == word.charAt(index)) {
			System.out.println("up from " + i + " to " + board[i-5]);
			posLetters[index] = i-5;
			if(index >= word.length()-1) {
				return true;
			} else {
				index++; directions = true; stack.push(i);
				return recursiveBoard(i - 5, startI);
			}
		}
		// up + right
		if (i - 4 >= 0 && (i+1) % 5 != 0 && board[i-4].charAt(0) == word.charAt(index)) {
			System.out.println("up right from " + i + " to " + board[i-4]);
			posLetters[index] = i-4;
			if(index >= word.length()-1) {
				return true;
			} else {
				index++; directions = true; stack.push(i);
				return recursiveBoard(i - 4, startI);
			}
		}
		// left
		if (i - 1 >= 0 && i % 5 != 0 && board[i-1].charAt(0) == word.charAt(index)) {
			System.out.println("left from " + i + " to " + board[i-1]);
			posLetters[index] = i-1;
			if(index >= word.length()-1) {
				return true;
			} else {
				index++; directions = true; stack.push(i);
				return recursiveBoard(i - 1, startI);
			}
		}
		// right
		if (i + 1 <= 24 && (i + 1) % 5 != 0 && board[i+1].charAt(0) == word.charAt(index)) {
			System.out.println("right from " + i + " to " + board[i+1]);
			posLetters[index] = i+1;
			if(index >= word.length()-1) {
				return true;
			} else {
				index++; directions = true; stack.push(i);
				return recursiveBoard(i + 1, startI);
			}
		}
		// down + left
		if (i + 4 <= 24 && i % 5 != 0 && board[i+4].charAt(0) == word.charAt(index)) {
			System.out.println("down left from " + i + " to " + board[i+4]);
			posLetters[index] = i+4;
			if(index >= word.length()-1) {
				return true;
			} else {
				index++; directions = true; stack.push(i);
				return recursiveBoard(i + 4, startI);
			}
		}
		// down
		if (i + 5 <= 24 && board[i+5].charAt(0) == word.charAt(index)) {
			System.out.println("up right from " + i + " to " + board[i+5]);
			posLetters[index] = i+5;
			if(index >= word.length()-1) {
				return true;
			} else {
				index++; directions = true; stack.push(i);
				return recursiveBoard(i + 5, startI);
			}
		}
		// down + right
		if (i + 6 <= 24 && (i+1) % 5 != 0 && board[i+6].charAt(0) == word.charAt(index)) {
			System.out.println("up right from " + i + " to " + board[i+6]);
			posLetters[index] = i+6;
			if(index >= word.length()-1) {
				return true;
			} else {
				index++; directions = true; stack.push(i);
				return recursiveBoard(i + 6, startI);
			}
		}
		if (!directions) {
			if (i == startI) {
				return false;
			} else {
				System.out.println("pop " + stack.peek());
				for(int k : stack) {
					System.out.println("stack: " + k);
				}
				stack.pop();
				recursiveBoard(stack.peek(), startI);
			}
		}
		return false;
	}

}