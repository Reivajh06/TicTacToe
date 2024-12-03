package reivajh06;

import java.util.*;

public class Main {

	public static Scanner SCANNER = new Scanner(System.in);

	public static void main(String[] args) {
		runTicTacToe();
	}

	public static void runTicTacToe() {
		Board board = createBoard();

		System.out.println("Do you want to play with Xs or Os?");

		String userResponse = SCANNER.nextLine().toUpperCase();

		if(!userResponse.equals("X") && !userResponse.equals("O")) {
			throw new RuntimeException("Unknown tic tac toe value, expected X/O, got %s".formatted(userResponse));
		}

		Player user = new Player(userResponse, false);
		Player ai = new Player(userResponse.equals("X")? "O" : "X", true);

		BoardResults matchResult = board.checkBoard();

		while(matchResult == null) {
			ai.playTurn(board, user);
			matchResult = board.checkBoard();

			if(matchResult != null) {
				System.out.printf("%s%n", matchResult);
				break;
			}

			user.playTurn(board, ai);
			matchResult = board.checkBoard();

			if(matchResult != null) {
				System.out.printf("%s%n", matchResult);
				break;
			}

		}
	}

	public static Board createBoard() {
		Map<String, List<String>> boardContent = new LinkedHashMap<>();

		List<String> row1 = new ArrayList<>(List.of(" ", " ", " "));
		List<String> row2 = new ArrayList<>(List.of(" ", " ", " "));
		List<String> row3 = new ArrayList<>(List.of(" ", " ", " "));

		boardContent.put("a", row1);
		boardContent.put("b", row2);
		boardContent.put("c", row3);

		return new Board(boardContent);
	}
}
