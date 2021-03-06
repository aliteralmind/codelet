package  com.github.aliteralmind.codelet.examples.adder;
/**
   <p>Adds zero or more integers.</p>
 **/
public class Adder  {
   private final int sum;

   public Adder(int... numbers_to_add)  {
      int sum2 = 0;
      for(int i : numbers_to_add)  {
         sum2 += i;
      }
      sum = sum2;
   }

   public int getSum()  {
      return  sum;
   }
}
