import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Assignment2Test {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }


  @Test
  void testIsPrimeEmpty() {
    Assertions.assertThrows(NumberFormatException.class, () -> Assignment2.checkPrime(""));
  }

  @Test
  void testIsPrimeString() {
    Assertions.assertThrows(NumberFormatException.class, () -> Assignment2.checkPrime("COMP3355"));
  }

  @Test
  void testIsPrimeSmallNumber() {
    Assignment2.checkPrime("123");
    assertEquals("123 is not a prime number\n", outContent.toString());
  }

  @Test
  void testIsPrimeSmallNumber2() {
    Assignment2.checkPrime("2");
    assertEquals("2 is a prime number\n", outContent.toString());
  }

  /*@Test took longer then 10 minutes to run
  void testIsPrimeBigNumber() {
    Assignment2.checkPrime("71755440315342536873");
    assertEquals("71755440315342536873 is a prime number\n", outContent.toString());
  }

  @Test
  void testIsPrimeBigNumber4() {
    Assignment2.checkPrime("32361122672259149");
    assertEquals("32361122672259149 is a prime number\n", outContent.toString());
  }
  */

  @Test
  void testIsPrimeBigNumber3() {
    Assignment2.checkPrime("688846502588399");
    assertEquals("688846502588399 is a prime number\n", outContent.toString());
  }

  @Test
  void testIsPrimeBigNumber2() {
    Assignment2.checkPrime("717554390687");
    assertEquals("717554390687 is a prime number\n", outContent.toString());
  }

  @Test
  void testInverseModString() {
    Assertions.assertThrows(NumberFormatException.class, () -> Assignment2.inverseMod("COMP3355", "COMP3355"));
  }

  @Test
  void testInverseModEmpty() {
    Assertions.assertThrows(NumberFormatException.class, () -> Assignment2.inverseMod("", ""));
  }

  @Test
  void testInverseMod() {
    Assignment2.inverseMod("20999311111111111135", "33332322667876");
    assertEquals("13204983474883\n", outContent.toString());
  }

  @Test
  void testInverseMod2() {
    Assignment2.inverseMod("209993111111111111352321212", "89849348384934");
    assertEquals("17963410520665\n", outContent.toString());
  }

  @AfterEach
  void teardown() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

}
