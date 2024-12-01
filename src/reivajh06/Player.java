package reivajh06;

import reivajh06.ai.Ollama;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {

	public static Scanner scanner = new Scanner(System.in);

	public final String value;
	public final boolean isAi;
	public List<String> previousMoves;

	public Player(String value, boolean isAi) {
		this.value = value;
		this.isAi = isAi;
		this.previousMoves = new ArrayList<>();
	}

	public void playTurn(Board board, Player rival) {
		if(!this.isAi) {
			System.out.println("Where do you wanna place your next value? (row name with column position index)");
			String userResponse = scanner.nextLine();
			board.assignValue(userResponse, this.value);
			previousMoves.add(userResponse);

		} else {
			String prompt = """
					You're playing tic tac toe with a user.
				
					 Given the current state of the board and considering you're using {value} as your values:
				
					 {board}
				
					 And also considering the available_positions: {available_positions}
				
					 Instructions:
					 - Each row is labeled from a to c and each column from 1 to 3
				
					 - Select which position do you want to assign an {value}
				
					 - To select said position simply answer with the row and column in question, for example: a1 (notice the row
					 and the column must be concatenated, without any other characters)
				
					 - Select one of the positions in {available_positions}
				
					 - ANSWER ONLY WITH THE ROW AND COLUMN VALUES, WITHOUT THE USE OF "" OR ''
				
					 - Try to win the game as fast as you can
				
					 You have to answer in this format:
				
					 Reasoning:
					 your reasoning here step by step
				
					 Selected position:
					 your final selected position. Must be one of {available_positions}
				
					 YOUR PREVIOUS MOVE WERE: {previous_moves}
				
					 THE RIVAL'S PREVIOUS MOVES WERE: {rival_previous_moves}
					
					"""
					.replace("{value}", value)
					.replace("{board}", board.toMap().toString())
					.replace("{available_positions}", board.availablePositions().toString())
					.replace("{previous_moves}", previousMoves.toString())
					.replace("{rival_previous_moves}", rival.previousMoves.toString());


			String response = Ollama.generate("qwen2.5:14b", prompt, 0.5);

			System.out.println(response);

			Pattern pattern = Pattern.compile("\\b[a-c][1-3]\\b");

			Matcher matcher = pattern.matcher(response);

			String positionToModify = matcher.results().toList().getLast().group();

			previousMoves.add(positionToModify);

			board.assignValue(positionToModify, value);
		}
	}

}
