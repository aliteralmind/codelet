package  com.github.aliteralmind.codelet.examples.adder;
/**
	<p>Adds zero or more integers.</p>

{@.codelet.and.out com.github.aliteralmind.codelet.examples.adder.AdderDemo%lineRange(1, false, "Adder adder", 2, false, "println(adder.getSum())", "^      ")}
 **/
public class AdderWithCodelet  {
	private final int sum;

	public AdderWithCodelet(int... numbers_to_add)  {
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