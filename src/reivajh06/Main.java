package reivajh06;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		Map<String, List<String>> boardContent = new LinkedHashMap<>();

		List<String> row1 = new ArrayList<>(List.of("X", "O", "X"));

		List<String> row2 = new ArrayList<>(List.of("X", "x", "O"));

		List<String> row3 = new ArrayList<>(List.of("X", "O", "O"));

		boardContent.put("a", row1);
		boardContent.put("b", row2);
		boardContent.put("c", row3);

		Board board = new Board(boardContent);

		System.out.println(board.checkBoard());
	}
}
