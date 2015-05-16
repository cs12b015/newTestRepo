import java.util.*;
import java.io.*;

public class posts {
	public int likes_count; 
	public String post_story;
	public int comments_count;
	public int share_count;
	
	posts(){
		likes_count=0;
		post_story=null;
		comments_count=0;
		share_count=0;
	}
	posts(int lc, int cc,int sc, String stry){
		likes_count=lc;
		post_story=stry;
		comments_count=cc;
		share_count=sc;
		
	}	
	
}