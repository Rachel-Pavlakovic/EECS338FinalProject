import java.util.Random;

public class Deck{
  
  public int size;
  
  public int[] cards;
  
  public int missing = 0;
  
  public Random rand = new Random();
  
  public Deck(int in){
    size = in;
    cards = new int[52*size];
    
    for(int i = 0; i < size*52; i++){
      cards[i] =(i%13)+1;
    }
  }
  
  private void swap(int x){
    int temp = cards[x];
    cards[x] = cards[52*size-1-missing];
    cards[52*size-1-missing] = temp;
  }
  
  public void shuffle(){
    missing = 0;
  }
  
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