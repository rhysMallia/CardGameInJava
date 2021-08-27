package model;

import model.interfaces.Player;

public class SimplePlayer implements Player {
	private String id, name;
	private int chips, bet, result;
	
	public SimplePlayer(String id, String name, int chips) {
		this.id = id;
		this.name = name;
		this.chips = chips;
	}

	@Override
	public String getPlayerName() {
		return name;
	}

	@Override
	public void setPlayerName(String playerName) {
		name = playerName;
	}

	//I called them chips because points made me think of the slot machines
	
	@Override
	public int getPoints() {
		return chips;
	}

	@Override
	public void setPoints(int points) {
		chips = points;
	}

	@Override
	public String getPlayerId() {
		return id;
	}
	
	// this is called from the set bet method, and removes said chips before placing them into the bet holder
	
	private void takeBet(int bet) {
		this.bet = bet;
		chips -= bet;
	}

	@Override
	public boolean setBet(int bet) {
		if(bet == 0)
			resetBet();
		else if(bet <= getPoints()) {
			takeBet(bet);
			return true;
		}
		else
			throw new IllegalArgumentException("Unable to place bet! :(");
		return false;
	}

	@Override
	public int getBet() {
		return bet;
	}

	@Override
	public void resetBet() {
		bet = 0;
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public boolean equals(Player player) {
		return(id.equals(player.getPlayerId()) && name.equals(player.getPlayerName()));
	}
	
	@Override
	public boolean equals(Object player) {
		if(player instanceof Player) {
			Player nicePlayer = (Player) player;
			return (id.equals(nicePlayer.getPlayerId()) && name.equals(nicePlayer.getPlayerName()));
		}
		return false;
	}

	@Override
	public int compareTo(Player player) {
		return id.compareTo(player.getPlayerId());
	}
	
	@Override
	public String toString() {
		return String.format("Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %d", getPlayerId(), 
				getPlayerName(), getBet(), getPoints(), getResult());
	}
	
	public int hashCode() {
		return(id.hashCode() + name.hashCode());
	}

}
