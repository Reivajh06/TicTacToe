package reivajh06;

import reivajh06.ai.Ollama;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AiPlayer extends Player{

	public AiPlayer(String value) {
		super(value);
	}

	public void playTurn(Board board, Player rival) {
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
				
				
					 YOUR PREVIOUS MOVE WERE: {previous_moves}
				
					 THE RIVAL'S PREVIOUS MOVES WERE: {rival_previous_moves}
					
					You have to answer in this format:
					
					 Selected position:
					 your final selected position. Must be one of {available_positions}
					
					 Reasoning:
					 your reasoning here step by step
					
					"""
				.replace("{value}", value)
				.replace("{board}", board.toMap().toString())
				.replace("{available_positions}", board.availablePositions().toString())
				.replace("{previous_moves}", previousMoves.toString())
				.replace("{rival_previous_moves}", rival.previousMoves.toString());


		String response = Ollama.generate("qwen2.5:3b-instruct-q4_K_M", prompt, 0.5);

		System.out.println(response);

		Pattern pattern = Pattern.compile("\\b[a-c][1-3]\\b");

		Matcher matcher = pattern.matcher(response);

		String positionToModify = matcher.results().toList().getLast().group();

		previousMoves.add(positionToModify);

		board.assignValue(positionToModify, value);

		System.out.println(board);

	}
}
