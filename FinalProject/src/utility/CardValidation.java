package utility;

public class CardValidation {
	// Return true if the card number is valid, otherwise returns false, this method
	// is already implemented
	public boolean aValidNumber(final String n) {

		final long number = Long.parseLong(n);
		return (numLength(number) >= 13)
				&& (numLength(number) <= 16) && (prefixCheck(number, 4) || prefixCheck(number, 5)
						|| prefixCheck(number, 6) || prefixCheck(number, 37))
				&& (totalEevenNumbers(number) + totalOddNumbers(number)) % 10 == 0;
	}// end of aValidNumber method

	// get the sum of even places numbers, Starting from the second digit from right
	private int totalEevenNumbers(long number) {
		int sum = 0;
		int count = 0;
		while (number > 0) {
			count++;
			if (count % 2 == 0) {
				final int digit = (int) (number % 10);
				sum += singleDigit((digit * 2));
			}
			number /= 10;
		}

		return sum;
	}// end of totalEevenNumbers method

	// Return the same number if it is a single digit, otherwise, return the sum of
	// the two digits in this number
	private int singleDigit(int number) {
		int sum = 0;
		while (number != 0) {
			sum += number % 10;
			number /= 10;
		}
		return sum;
	} // end of singleDigit method

	// Return the sum of odd place digits in number
	private int totalOddNumbers(long number) {

		int result = 0;
		int count = 0;
		while (number > 0) {
			count++;
			if (count % 2 != 0) {
				final int digit = (int) (number % 10);
				result += digit;
			}
			number /= 10;
		}

		return result;

	}// end of totalOddNumbers method

	// Return true if the digit d is a prefix for number
	private boolean prefixCheck(final long number, final int d) {
		if (d / 10 == 0)
			return numPrefix(number, 1) == d;
		else
			return numPrefix(number, 2) == d;

	}// end of prefixCheck method

	// Return the number of digits in this number parameter
	private int numLength(long number) {
		int length = 0;
		while (number > 0) {
			number /= 10;
			length++;
		}
		return length;
	}// end of numLength method

	// Return the first k number of digits from number, which is either a first
	// digit or first two digits
	// Depending on the card type
	private long numPrefix(final long number, final int k) {
		final long divisor = (long) Math.pow(10, numLength(number) - k);
		final long r = number / divisor;
		return r;
	}// end of numPrefix method
}
