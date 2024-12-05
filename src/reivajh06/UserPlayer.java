package reivajh06;

import java.util.List;
import java.util.Scanner;

public class UserPlayer extends Player{

	public static Scanner scanner = new Scanner(System.in);

	public UserPlayer(String value) {
		super(value);
	}

	public void playTurn(Board board, Player rival) {
		System.out.println("Where do you wanna place your next value? (row name with column position index)");
		String userResponse = scanner.nextLine();
		board.assignValue(userResponse, this.value);
		previousMoves.add(userResponse);
		System.out.println(board);
	}
}
