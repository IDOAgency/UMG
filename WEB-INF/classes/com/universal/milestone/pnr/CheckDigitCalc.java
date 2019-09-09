/*     */ package WEB-INF.classes.com.universal.milestone.pnr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CheckDigitCalc
/*     */ {
/*     */   public static int getCheckDigitUPC(String inStr) {
/*  21 */     int checkDigit = 0;
/*     */     
/*  23 */     int odd = 0;
/*  24 */     int even = 0;
/*  25 */     int check10 = 0;
/*     */     
/*  27 */     char[] aChar = new char[inStr.length()];
/*     */ 
/*     */     
/*     */     try {
/*  31 */       aChar = inStr.toCharArray();
/*     */ 
/*     */ 
/*     */       
/*  35 */       for (int i = 0; i < inStr.length(); i++) {
/*     */         
/*  37 */         if (Character.isDigit(aChar[i]))
/*     */         {
/*     */           
/*  40 */           if (i % 2 == 0) {
/*  41 */             odd += Character.getNumericValue(aChar[i]);
/*     */           } else {
/*  43 */             even += Character.getNumericValue(aChar[i]);
/*     */           } 
/*     */         }
/*     */       } 
/*  47 */       check10 = odd * 3 + even;
/*     */       
/*  49 */       int rmd = check10 % 10;
/*     */       
/*  51 */       if (rmd == 0)
/*  52 */       { checkDigit = 0; }
/*     */       else
/*  54 */       { checkDigit = 10 - rmd; } 
/*  55 */     } catch (Exception e) {
/*     */       
/*  57 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  62 */     return checkDigit;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCheckDigitUPCold(String upc) {
/*  67 */     int checkDigit = 0;
/*     */     
/*  69 */     int odd = 0;
/*  70 */     int even = 0;
/*  71 */     int check10 = 0;
/*     */     
/*  73 */     char[] cnum = new char[upc.length()];
/*     */     
/*     */     try {
/*  76 */       cnum = upc.toCharArray();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  81 */       odd += Character.getNumericValue(cnum[0]);
/*  82 */       odd += Character.getNumericValue(cnum[2]);
/*  83 */       odd += Character.getNumericValue(cnum[4]);
/*  84 */       odd += Character.getNumericValue(cnum[6]);
/*  85 */       odd += Character.getNumericValue(cnum[8]);
/*  86 */       odd += Character.getNumericValue(cnum[10]);
/*  87 */       odd += Character.getNumericValue(cnum[12]);
/*     */ 
/*     */       
/*  90 */       even += Character.getNumericValue(cnum[1]);
/*  91 */       even += Character.getNumericValue(cnum[3]);
/*  92 */       even += Character.getNumericValue(cnum[5]);
/*  93 */       even += Character.getNumericValue(cnum[7]);
/*  94 */       even += Character.getNumericValue(cnum[9]);
/*  95 */       even += Character.getNumericValue(cnum[11]);
/*     */       
/*  97 */       check10 = odd * 3 + even;
/*     */       
/*  99 */       int rmd = check10 % 10;
/*     */       
/* 101 */       if (rmd == 0)
/* 102 */       { checkDigit = 0; }
/*     */       else
/* 104 */       { checkDigit = 10 - rmd; } 
/* 105 */     } catch (Exception e) {
/*     */       
/* 107 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 112 */     return checkDigit;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\pnr\CheckDigitCalc.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */