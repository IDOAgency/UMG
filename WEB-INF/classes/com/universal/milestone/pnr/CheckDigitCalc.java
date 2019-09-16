package WEB-INF.classes.com.universal.milestone.pnr;

public class CheckDigitCalc {
  public static int getCheckDigitUPC(String inStr) {
    int checkDigit = 0;
    int odd = 0;
    int even = 0;
    int check10 = 0;
    char[] aChar = new char[inStr.length()];
    try {
      aChar = inStr.toCharArray();
      for (int i = 0; i < inStr.length(); i++) {
        if (Character.isDigit(aChar[i]))
          if (i % 2 == 0) {
            odd += Character.getNumericValue(aChar[i]);
          } else {
            even += Character.getNumericValue(aChar[i]);
          }  
      } 
      check10 = odd * 3 + even;
      int rmd = check10 % 10;
      if (rmd == 0) {
        checkDigit = 0;
      } else {
        checkDigit = 10 - rmd;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return checkDigit;
  }
  
  public static int getCheckDigitUPCold(String upc) {
    int checkDigit = 0;
    int odd = 0;
    int even = 0;
    int check10 = 0;
    char[] cnum = new char[upc.length()];
    try {
      cnum = upc.toCharArray();
      odd += Character.getNumericValue(cnum[0]);
      odd += Character.getNumericValue(cnum[2]);
      odd += Character.getNumericValue(cnum[4]);
      odd += Character.getNumericValue(cnum[6]);
      odd += Character.getNumericValue(cnum[8]);
      odd += Character.getNumericValue(cnum[10]);
      odd += Character.getNumericValue(cnum[12]);
      even += Character.getNumericValue(cnum[1]);
      even += Character.getNumericValue(cnum[3]);
      even += Character.getNumericValue(cnum[5]);
      even += Character.getNumericValue(cnum[7]);
      even += Character.getNumericValue(cnum[9]);
      even += Character.getNumericValue(cnum[11]);
      check10 = odd * 3 + even;
      int rmd = check10 % 10;
      if (rmd == 0) {
        checkDigit = 0;
      } else {
        checkDigit = 10 - rmd;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return checkDigit;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\pnr\CheckDigitCalc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */