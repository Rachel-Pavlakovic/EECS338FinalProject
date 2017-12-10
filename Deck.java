import java.util.Random;

/**
 * 
 * @author Andrew Leppo and Rachel Pavlakovic
 *
 */
public class Deck{
  
  private int size;
  private int[] cards;
  private int missing = 0;
  private Random rand = new Random();
  
  /**
   * The constructor for the Deck
   * @param size
   */
  public Deck(int size){
    this.size = size;
    cards = new int[52*size];
    
    for(int i = 0; i < size*52; i++){
      cards[i] =(i%13)+1;
    }
  }
  
  /**
   * 
   * @param x
   */
  private void swap(int x){
    int temp = cards[x];
    cards[x] = cards[52*size-1-missing];
    cards[52*size-1-missing] = temp;
  }
  
  /**
   * 
   */
  public void shuffle(){
    missing = 0;
  }
  
  /**
   * Draws a new card from the deck
   * @return int the card drawn 
   */
  public int draw(){
    if (52*size-1-missing == 0){
      missing++;
      return cards[0];
    }
    else if(52*size-1-missing < 0){
      this.shuffle();
    }
    int pos = rand.nextInt((52*size-1-missing));
    int card = cards[pos];
    swap(pos);
    missing++;
    return card;
  }
  
}