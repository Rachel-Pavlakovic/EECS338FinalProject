public class  AutoDealer{
  
  private Hand hand;
  
  private Deck deck;
  
  public AutoDealer(Deck d){
    this.deck = d;
    this.hand = new Hand();
  }
  
  public int startRound(){
    hand.draw(deck.draw());
    return hand.getCards()[1];
  }
  
  public void play(){
    while(hand.getScore() < 17){
      hand.draw(deck.draw());
    }
  }
  
  public void clearHand() {
	  hand.clear();
  }
  
  public boolean didPlayerWin(int pScore){
    if (pScore > 21 ||(pScore < hand.getScore() && hand.getScore() < 22))
      return false;
    else
      return true;
  }
  
  public boolean didPlayerTie(int pScore){
    if ((pScore > 21 && hand.getScore() > 21) ||(pScore == hand.getScore()))
      return true;
    else
      return false;
  }
}