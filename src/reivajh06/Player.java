package reivajh06;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

	public final String value;
	public List<String> previousMoves = new ArrayList<>();

	public Player(String value) {
		this.value = value;
	}

	public abstract void playTurn(Board board, Player rival);
}
