/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.Selection;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectionFlArtistComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 14 */       Selection sel1 = (Selection)o1;
/* 15 */       Selection sel2 = (Selection)o2;
/*    */       
/* 17 */       String artist1 = sel1.getFlArtist().trim();
/* 18 */       String artist2 = sel2.getFlArtist().trim();
/*    */       
/* 20 */       return artist1.compareTo(artist2);
/*    */     }
/* 22 */     catch (Exception e) {
/*    */       
/* 24 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionFlArtistComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */