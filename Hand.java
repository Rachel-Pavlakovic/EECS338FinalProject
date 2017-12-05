public class Hand{
  
  private int[] cards;
  private int numSoft;
  private int score;
  private int numCards;
  
  public Hand(){
    cards = new int[16];
    numSoft = 0;
    score = 0;
    numCards = 0;
  }
  
  public int[] getCards(){
    return this.cards;
  }
  
  public int getScore(){
    return this.score;
  }
  
  public String stringCards(){
    StringBuilder out = new StringBuilder();
    for(int i = 0; i<16;i++){
      if (cards[i] == 1)
        out.append("A, ");
      else if (cards[i] == 11)
        out.append("J, ");
      else if (cards[i] == 12)
        out.append("Q, ");
      else if (cards[i] == 13)
        out.append("K, ");
      else
        out.append(cards[i] + ", ");
    }
    return out.toString();
  }
  
  public void draw(int card){
    cards[numCards] = card;
    numCards++;
    if (card == 1){
      score = score + 11;
      numSoft++;
    }
    else if(card == 11 || card == 12 || card == 13)
      score = score + 1;
    else
      score = score + card;
    while(numSoft > 0 && score > 21){
      score = score - 10;
      numSoft--;
    }
  }
  
  public void clear() {
	  cards = new int[16];
  }
  
}