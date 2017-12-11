/**
 * 
 * @author Andrew Leppo and Rachel Pavlakovic
 *
 */
public class  AutoDealer{
  
  private Hand hand;
  private Deck deck;
  
  /**
   * Constructor for AutoDealer
   * @param deck
   */
  public AutoDealer(Deck deck){
    this.deck = deck;
    this.hand = new Hand();
  }
  
  /**
   * 
   * @return int 
   */
  public int startRound(){
    hand.addCard(deck.draw());
    return hand.getCards()[1];
  }
  
  /**
 * @return the hand
 */
public Hand getHand() {
	return hand;
}

/**
   * 
   */
  public void play(){
    while(hand.getScore() < 17){
      hand.addCard(deck.draw());
    }
  }
  
  /**
   * 
   */
  public void clearHand() {
	  hand.clear();
  }
  
  /**
   * 
   * @param pScore
   * @return boolean whether or not the player won the game
   */
  public boolean didPlayerWin(int playerScore){
    if (playerScore > 21 ||(playerScore < hand.getScore() && hand.getScore() < 22))
      return false;
    else
      return true;
  }
  
  /**
   * 
   * @param pScore
   * @return boolean whether or not the player tied with the dealer
   */
  public boolean didPlayerTie(int playerScore){
    if ((playerScore > 21 && hand.getScore() > 21) ||(playerScore == hand.getScore()))
      return true;
    else
      return false;
  }
}