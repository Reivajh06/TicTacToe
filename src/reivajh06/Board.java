package reivajh06;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board {

	private static final String[] keys = {"a", "b", "c"};

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

	public String checkRows() {
		for(List<String> row : board.values()) {
			String rowElements = row.get(0) + row.get(1) + row.get(2);

			if(rowElements.equals("XXX")) {
				return "X";

			} else if(rowElements.equals("OOO")) {
				return "O";
			}

		}

		return null;
		
	}

	public String checkColumns(int column) {
		if(column == 3) {
			return null;
		}

		String value1 = board.get("a").get(column);
		String value2 = board.get("b").get(column);
		String value3 = board.get("c").get(column);
		String columnValues = value1 + value2 + value3;

		if(columnValues.equals("XXX")) {
			return "X";
		} else if(columnValues.equals("OOO")) {
			return "O";
		}

		return checkColumns(column + 1);

	}

	public String checkDiagonal() {
		String diagonal = "";

		for(int i = 0; i < board.size(); i++) {
			diagonal += board.get(keys[i]).get(i);

		}

		if(diagonal.equals("XXX")) {
			return "X";
		} else if(diagonal.equals("OOO")) {
			return "O";
		}

		return null;
	}

	public String checkReversedDiagonal() {
		String reversedDiagonal = "";

		for(int i = 2; i > board.size(); i--) {
			reversedDiagonal += board.get(keys[i]).get(i);
		}

		if(reversedDiagonal.equals("XXX")) {
			return "X";

		} else if(reversedDiagonal.equals("OOO")) {
			return "O";

		}

		return null;
	}

	public List<String> availablePositions() {
		List<String> positionsWithoutValue = new ArrayList<>();

		for(Map.Entry<String, List<String>> entry : board.entrySet()) {
			List<String> values = entry.getValue();

			for(int i = 0; i < values.size(); i++) {
				if(values.get(i).equals(" ")) {
					positionsWithoutValue.add(entry.getKey() + (i + 1));
				}
			}
		}

		return positionsWithoutValue;
	}

	public BoardResults checkBoard() {
		String rowsResult = checkRows();

		if(rowsResult != null) {
			if(rowsResult.equals("X")) {
				return BoardResults.XPLAYERWINS;

			} else if(rowsResult.equals("O")) {
				return BoardResults.OPLAYERWINS;
			}
		}


		String columnsResult = checkColumns(0);

		if(columnsResult != null) {
			if(columnsResult.equals("X")) {
				return BoardResults.XPLAYERWINS;

			} else if(columnsResult.equals("O")) {
				return BoardResults.OPLAYERWINS;
			}
		}


		String diagonalResult = checkDiagonal();

		if(diagonalResult != null) {
			if(diagonalResult.equals("X")) {
				return BoardResults.XPLAYERWINS;

			} else if(diagonalResult.equals("O")) {
				return BoardResults.OPLAYERWINS;
			}
		}

		String reversedDiagonalResult = checkReversedDiagonal();

		if(reversedDiagonalResult != null) {
			if(reversedDiagonalResult.equals("X")) {
				return BoardResults.XPLAYERWINS;

			} else if(reversedDiagonalResult.equals("O")) {
				return BoardResults.OPLAYERWINS;
			}
		}


		if(availablePositions().isEmpty()) {
			return BoardResults.DRAW;
		}

		return null;
 	}

	@Override
	public String toString() {
		return replacePositionsWithValues();
	}
}
