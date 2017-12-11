/**
 * 
 * @author Andrew Leppo and Rachel Pavlakovic
 *
 */
public class Hand{
  
  private int[] cards;
  private int numSoft;
  private int score;
  private int numCards;
  
  /**
   * Constructor for the Hand
   */
  public Hand() {
    cards = new int[16];
    numSoft = 0;
    score = 0;
    numCards = 0;
  }
  
  /**
   * 
   * @return int[] the array holding the cards in the hand
   */
  public int[] getCards() {
    return this.cards;
  }
  
  /**
   * 
   * @return int the score of the hand
   */
  public int getScore() {
    return this.score;
  }
  
  /**
   * 
   * @return String a string representation of the hand
   */
  public String toString() {
	  StringBuilder hand = new StringBuilder();
	  for(int i = 0; i < numCards; i++) {
		  switch(cards[i]) {
		  case 1:
			  hand.append("A, ");
			  break;
		  case 11:
			  hand.append("J, ");
			  break;
		  case 12:
			  hand.append("Q, ");
			  break;
		  case 13:
			  hand.append("K, ");
			  break;
		  default:
			  hand.append(cards[i] + ", ");
			  break;  
		  }
	  }
	  hand.deleteCharAt(hand.length() - 2);
	  return hand.toString();
  }
    
  /**
   * Adds a new card to the hand
   * @param card
   */
  public void addCard(int card) {
    cards[numCards] = card;
    numCards++;
    
    if (card == 1) {
      score = score + 11;
      numSoft++;
    }
    else if(card == 11 || card == 12 || card == 13) {
      score = score + 10;
    }
    else {
      score = score + card;
    }
    
    //Decreases any aces from 11 to 1 if the score is above 21
    while(numSoft > 0 && score > 21){
      score = score - 10;
      numSoft--;
    }
  }
  
  /**
   * clears the hand
   */
  public void clear() {
	  cards = new int[16];
  }
  
}