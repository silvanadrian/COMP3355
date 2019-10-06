import java.math.BigInteger;

class Assignment2 {

  private static BigInteger two = new BigInteger("2");
  private static BigInteger three = new BigInteger("3");

  static void checkPrime(String str) {
    try {
      BigInteger number = new BigInteger(str);
      if (checkPrime(number)) {
        System.out.println(number + " is a prime number");
      } else {
        System.out.println(number + " is not a prime number");
      }

    } catch (Exception e) {
      throw e;
    }
  }

  private static boolean checkPrime(BigInteger n) {
    if (n.compareTo(BigInteger.ONE) == 0 || n.compareTo(two) == 0) {
      return true;
    }
    BigInteger sqrRoot = appproxSqrRoot(n);

    for (BigInteger i = three; i.compareTo(sqrRoot) <= 0; i = i.nextProbablePrime()) {
      if (n.mod(i).equals(BigInteger.ZERO)) {
        return false;
      }
    }
    return true;
  }

  private static BigInteger appproxSqrRoot(final BigInteger n) {
    BigInteger half = n.shiftRight(1);
    while (half.multiply(half).compareTo(n) > 0) {
      half = half.shiftRight(1);
    }
    return half.shiftLeft(1);
  }

  static void inverseMod(String stringC, String stringN) {
    try  {
      BigInteger c = new BigInteger(stringC);
      BigInteger n = new BigInteger(stringN);

      BigInteger [] result = extendedEuclid(c,n);

      if (result[1].compareTo(BigInteger.ZERO) > 0) {
        System.out.println(result[1]);
      } else {
        System.out.println(result[1].add(n));
      }
    } catch (Exception e) {
      throw e;
    }
  }

  private static BigInteger [] extendedEuclid(BigInteger c, BigInteger n){
    BigInteger [] result = new BigInteger[3];
    BigInteger ax, yN;

    if (n.equals(BigInteger.ZERO)) {
      result[0] = c;
      result[1] = BigInteger.ONE;
      result[2] = BigInteger.ZERO;
      return result;
    }

    result = extendedEuclid (n, c.mod (n));
    ax = result[1];
    yN = result[2];
    result[1] = yN;
    BigInteger temp = c.divide(n);
    temp = yN.multiply(temp);
    result[2] = ax.subtract(temp);
    return result;
  }

}
