package reivajh06;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board {

	public Map<String, List<String>> board;


	public Board(Map<String, List<String>> board) {
		this.board = board;

	}

	public void assignValue(String row, int column, String value) {
		board.get(row).add(column, value);
	}

	public String getBoard() {
		try {
			return Files.readString(Path.of("src/reivajh06/board.txt"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String replacePositionsWithValues() {
		String boardState = getBoard();

		Pattern positionPattern = Pattern.compile("\\{([a-c])([1-3])}");

		Matcher matcher = positionPattern.matcher(boardState);

		while(matcher.find()) {
			String row = matcher.group(1);
			int column = Integer.parseInt(matcher.group(2)) - 1;
			boardState = boardState.replace(matcher.group(), board.get(row).get(column));
		}

		return boardState;
	}

	@Override
	public String toString() {
		return replacePositionsWithValues();
	}
}
