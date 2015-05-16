import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import org.json.*;


public class cbr {

	public static LinkedList<posts> userpostslist = new LinkedList<posts>() ;
	
	public static void main(String[] args) throws FileNotFoundException, JSONException ,MalformedURLException , IOException {
		
    	System.out.println("Token taken ");
    	//Scanner scanner = new Scanner(System.in);
    	//String token = scanner.nextLine();
    	String token="CAACEdEose0cBAB1WLbPvZAmk2oIbPXEZCRUUOI10eJZCW5IO31nHUFJpzm0ZAFPoFVsDKfgltuxB1El9FCHUdrKHgv5cijBQKBkIyF2BPPowDYmQE5rIfVHno3hBhOuwJN4rNEB8eKZBy8qUWrP10yVZA3BYyinwd3iOD9RwYmZCDPxSZA1A7ztJmIczdVTton0ZB6Ca44s4Fp96ZA97xsTngMsW60blwaXlUZD";
    	//token = "CAAE134Gqr7YBAEL02eAyVq1HYHmGzH5efEi9j6EkvsQgvZC2HFlNB3OxrJnPXefNosgJia9iAylFwZCp3EgZAqVmLBGPWe05KQjMLWBF5CSEVg7GfQeKOVGZAZBDPidggK9gK5EbyDCal72kEBZBVvJ8GR8P33hoVyMdLcq7d0kDBkfZC3BZBZADctsBm6Meh3wNVCOBiDAlsGifNfMByBOhz";
    	String url1 = "https://graph.facebook.com/me/posts?limit=1000&fields=id%2Cstory%2Cmessage%2Cstatus_type&access_token="+token+"&__mref=message_bubble";
    	String url2="https://graph.facebook.com/me?fields=likes&access_token="+token+"&__mref=message_bubble";
    	
    	
    	DataOutputStream out1 = new DataOutputStream(new FileOutputStream("bagitestpost.txt"));
    	DataOutputStream out2 = new DataOutputStream(new FileOutputStream("bagitestlikes.txt"));
    	
    	String json = null;
		
    	String line;
    	line=get_infromation_from_web(url1);
	    json=line;
	    
	  
	    
	    ArrayList<JSONObject> likearray = new ArrayList<JSONObject>();
	    dothething(url2,likearray);
		int arraylistsize = likearray.size();
		int i;
		
		String[] likeintrest = new String[arraylistsize];
		
		for(i=0;i<arraylistsize;i++)
		{
				out2.writeBytes(i+".----------------------------------------\n");
				out2.writeBytes((String)likearray.get(i).get("category")+"\n");
				out2.writeBytes((String)likearray.get(i).get("name")+"\n");
				likeintrest[i]=(String)likearray.get(i).get("name");	
		}
		
		out2.close();
	    
	    
	    
		//System.out.println(json);
	    //putting json into arrylist
		JSONObject myjson = new JSONObject(json);
		JSONArray the_json_array = myjson.getJSONArray("data");
		int size = the_json_array.length();
		ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
		for (i = 0; i < size; i++) {
	        JSONObject another_json_object = the_json_array.getJSONObject(i);
	            arrays.add(another_json_object);
	    }

		String nexturl=null;
		if(myjson.has("paging"))
		{
			JSONObject pgson = (JSONObject) myjson.get("paging");
			nexturl= (String) pgson.get("next");
			System.out.println(nexturl);
		}
		
		get_all_page_posts(nexturl,json,arrays);
		
		

		//System.out.println(arrays.get(0));
		int arraylistsize1 = arrays.size();
		DataOutputStream out3 = new DataOutputStream(new FileOutputStream("bagitestpostallmessagesonly.txt"));
		//Collecting all the posts(message/story) for textual analysis,i.e., finding all the words.
		for(i=0;i<arraylistsize1;i++)
		{
			if(arrays.get(i).has("story"))
			{
				//System.out.println(arrays.get(i).get("story"));
				//out1.writeBytes((String) arrays.get(i).get("story")+"\n");
			}
			
			if(arrays.get(i).has("message"))
			{
				//System.out.println(arrays.get(i).get("message")  );
				out1.writeBytes(i+".----------------------------------------\n");
				out1.writeBytes((String)arrays.get(i).get("id")+"\n");
				out1.writeBytes((String)arrays.get(i).get("message")+"\n");
				out3.writeBytes((String)arrays.get(i).get("message")+"\n");
				out1.writeBytes((String)arrays.get(i).get("created_time")+"\n"+"\n");
				String countid;
				countid=(String)arrays.get(i).get("id");
				
				String message1 =(String)arrays.get(i).get("message");
				
				//System.out.println((String)arrays.get(i).get("message"));
				
				//getthecount(countid,token,message1);
				String url3="https://api.facebook.com/method/fql.query?query=SELECT%20like_info.like_count,%20share_info.share_count,%20comment_info.comment_count%20FROM%20stream%20WHERE%20post_id%20=%27"+countid+"%27&format=json&access_token="+token+"&__mref=message_bubble";
				String json1;
		    	json1="{\"data\":"+get_infromation_from_web(url3)+"}";
		    	JSONObject myjson1 = new JSONObject(json1);
		    	//System.out.println(myjson1);
		    	JSONArray the_json_array1 = myjson1.getJSONArray("data");
		        JSONObject another_json_object = the_json_array1.getJSONObject(0);
		    	JSONObject comments = (JSONObject) another_json_object.get("comment_info");
		    	JSONObject likes = (JSONObject) another_json_object.get("like_info");
		    	JSONObject shares = (JSONObject) another_json_object.get("share_info");
		    	//System.out.println(message1);
		    	//System.out.println("Pno."+i+" is da post");
		    	//System.out.println(likes.get("like_count"));
		    	//System.out.println(comments.get("comment_count"));
		    	//System.out.println(shares.get("share_count"));
		    	int lc, cc,sc;
		    	String temp = likes.get("like_count").toString();
		    	lc=	Integer.parseInt(temp); 	
		    	String tempa=comments.get("comment_count").toString();
		    	cc=	Integer.parseInt(tempa);
		    	String tempb= shares.get("share_count").toString();
		    	sc=	Integer.parseInt(tempb);
		    	posts pobject= new posts(lc, cc, sc, message1);
		    	userpostslist.add(pobject);				
			}
		}
		
		//Entering only message of posts into files
		FileInputStream in1 = new FileInputStream("bagitestpostallmessagesonly.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in1));
                
        String tmp,po_all=null;        
        tmp=br.readLine();
        po_all=tmp;
        
        while ((tmp = br.readLine()) != null) {
	    	
	    	po_all=po_all+" \n "+tmp;
	    }
        //System.out.println(po_all);
        String[] keysall = po_all.split("[?., \n\t-+;:]");
        String[] uniqkeysall = getUniqueKeys(keysall);
        List<Integer> freqallkeys = new ArrayList<Integer>();
        freqallkeys= lex_freq(po_all);

        //System.out.println(freqallkeys.size());
        //System.out.println(uniqkeysall.length);
        
        //System.out.println("b4 removing dumb terms");
        
        String prepandconj ="about after against along among around as for at before below beside between but by down during except from in inside into like near next of off on onto out over past since through till to toward under unlike until up with and but or nor for yet so";    
        
        String[] delwor = prepandconj.split(" ");
        for(i=0;i<uniqkeysall.length;i++)
        {
             
        for(int j=0;j<delwor.length;j++){
                String temp=delwor[j];
                //System.out.println(temp);
                if(uniqkeysall[i].equals(temp)){
                    freqallkeys.set(i,0);
                    //System.out.println("matched  String :"+temp );
                    break;
                }
            }          
        }
         
         
        System.out.println("\n\n\n\n\n\nRemoving stupid words is dunn\n\n\n\n\n\n");
        
                    
        
        //System.out.println(freqallkeys.size());
        //System.out.println(uniqkeysall.length);
        int c,d,swap;
        for (c = 0;c < freqallkeys.size()-1;c++)
        {
            for (d = 0 ; d < freqallkeys.size() - c - 1; d++)
            {
                if (freqallkeys.get(d) < freqallkeys.get(d+1))
                {
                    swap = freqallkeys.get(d); 
                    freqallkeys.set(d,freqallkeys.get(d+1));
                    freqallkeys.set(d+1,swap);
                                         
                    String temp=uniqkeysall[d];
                    uniqkeysall[d]=uniqkeysall[d+1];
                    uniqkeysall[d+1]=temp;
                     
                }
            }
        }
        
        /*
         * The Keys and counts are sorted
         * 
         */
        
        for (int k = 0; k < uniqkeysall.length; k++) {
        	
            //System.out.println(freqallkeys.get(k));
            //System.out.println(uniqkeysall[k]);
        }
               
        
        
        br.close();
		out1.close();
		out3.close();
	//	scanner.close();
		
		String[] ukeytop= new String[100];		
		for(int j=0;j<100;j++){
			ukeytop[j]=uniqkeysall[j+1] ; 
	        	
	    }
		//String intro=introduction(token);
		//System.out.println(intro);
		
		
		System.out.println("Finally Printing a Post with its word count array;");	
		posts pobj=userpostslist.get(2);
		System.out.println("Got the POST!");
		System.out.println(pobj.post_story);
		int[] sample = pos_word_check(pobj.post_story, ukeytop);
		System.out.println();
		//for(int j=0;j<100;j++)System.out.println("\nkey no:"+j+"\nkey:"+ukeytop[j]+"\noccurance of key in post:"+sample[j]+"\noccurence of key in all posts:"+freqallkeys.get(j+1));
		
		int kill = userpostslist.size();
    	System.out.println(userpostslist.get(kill-1).post_story);
    	
		
    	
    	int n;i=1;
    	int[]userprefarray = new int [userpostslist.size()];
    	Scanner input = new Scanner(System.in);
    	System.out.println(userpostslist.get(0).post_story);
		System.out.println("Is this Auto-Biographic ?? (0/1)");	 
	    while ((n = input.nextInt()) != -99 && i< userpostslist.size()) 
	    {	    	
	    	if(n==0)userprefarray[i-1]=n;
	    	else if (n==1)userprefarray[i-1]=n;	    	
	    	String p1=userpostslist.get(i).post_story;
	    	System.out.println(userpostslist.get(i).post_story);
			System.out.println("Is this Auto-Biographic ?? (0/1)(0==NO / 1==>YES)");	 
			i++;
	    }
	    n = input.nextInt();
	    if(n==0)userprefarray[i-1]=n;
    	else if (n==1)userprefarray[i-1]=n;	 
	 
	    //System.out.println("Out of loop");
    	
    
    	
		
		
		generate_arff(ukeytop, likeintrest, userpostslist);
		
	}
	
	
	public static void getthecount(String id,String token,String message) throws IOException, JSONException
	{
		String url3="https://api.facebook.com/method/fql.query?query=SELECT%20like_info.like_count,%20share_info.share_count,%20comment_info.comment_count%20FROM%20stream%20WHERE%20post_id%20=%27"+id+"%27&format=json&access_token="+token+"&__mref=message_bubble";
		String json;
    	json="{\"data\":"+get_infromation_from_web(url3)+"}";
    	JSONObject myjson = new JSONObject(json);
    	//System.out.println(myjson);
    	JSONArray the_json_array = myjson.getJSONArray("data");
        JSONObject another_json_object = the_json_array.getJSONObject(0);
    	JSONObject comments = (JSONObject) another_json_object.get("comment_info");
    	JSONObject likes = (JSONObject) another_json_object.get("like_info");
    	JSONObject shares = (JSONObject) another_json_object.get("share_info");
    	//System.out.println(message);
    	//System.out.println("------------------------------------------------------------------");
    	//System.out.println(likes.get("like_count"));
    	//System.out.println(comments.get("comment_count"));
    	//System.out.println(shares.get("share_count"));
    	
    	
    	

	}
	
	public static void dothething(String url2,ArrayList<JSONObject> likearray ) throws JSONException, IOException
	{
		
		String nexturl=url2;
	    String likejson =null;
		int check=1;
		
		System.out.println("enter1");
		
		likejson=get_infromation_from_web(nexturl);
		JSONObject obj1 = new JSONObject(likejson);
		JSONObject obje1 = (JSONObject) obj1.get("likes");
		JSONArray obj_array1 = obje1.getJSONArray("data");
		int objsize1=obj_array1.length();
		for (int i = 0; i < objsize1; i++) 
		{
	        JSONObject another_json_object1 = obj_array1.getJSONObject(i);      
				likearray.add(another_json_object1);
	    }
		if(obje1.has("paging"))
		{
			JSONObject pgson1 = (JSONObject) obje1.get("paging");
			if(pgson1.has("next"))
			{
				nexturl = (String) pgson1.get("next");
				System.out.println(nexturl);
			}
			else
			{
				check=2;
			}
		}		
		while(check==1)
		{
			likejson=get_infromation_from_web(nexturl);
			JSONObject obj = new JSONObject(likejson);
			JSONArray obj_array = obj.getJSONArray("data");
			int objsize=obj_array.length();
			for (int i = 0; i < objsize; i++) 
			{
		        JSONObject another_json_object = obj_array.getJSONObject(i);      
					likearray.add(another_json_object);
		    }
			if(obj.has("paging"))
			{
				JSONObject pgson = (JSONObject) obj.get("paging");
				if(pgson.has("next"))
				{
					nexturl = (String) pgson.get("next");
					System.out.println(nexturl);
				}
				else
				{
					check=2;
				}
			}
			
		}		
		

		
		
		
		
		
		
	}

	public static void get_all_page_posts(String nexturl,String json,ArrayList<JSONObject> arrays) throws IOException, JSONException
	{
		int checkpoint=1;
		while(checkpoint==1)
		{
			String line=null;
			line=get_infromation_from_web(nexturl);
			
			JSONObject obj = new JSONObject(line);
			JSONArray obj_array = obj.getJSONArray("data");
			int objsize=obj_array.length();
			for (int i = 0; i < objsize; i++) 
			{
		        JSONObject another_json_object = obj_array.getJSONObject(i);
		            arrays.add(another_json_object);
		    }
			
			if(obj.has("paging"))
			{
				JSONObject pgson = (JSONObject) obj.get("paging");
				nexturl= (String) pgson.get("next");
				System.out.println(nexturl);
			}
			else
			{
				checkpoint= 2;
			}
		}	
	}
	
	public static String introduction(String token) throws IOException, JSONException
	{
		String intro=null;
		String name_url;
		name_url="https://graph.facebook.com/me?fields=name,birthday,hometown,location,work,languages&access_token="+token+"&__mref=message_bubble";
		String name_json = null;
		name_json = get_infromation_from_web(name_url);
		JSONObject name_obj = new JSONObject(name_json);
		String name = (String)name_obj.get("name");
		String bday = (String)name_obj.get("birthday");
		
		JSONObject location_obj  = (JSONObject) name_obj.get("location");
		JSONObject hometown_obj  = (JSONObject) name_obj.get("hometown");
		
		String currliv =(String) location_obj.get("name");
		String hometown = (String) hometown_obj.get("name");
		
		
		
		intro ="Hi Everyone!. My name is "+name+". I was born on "+bday+". I am currently living in "+currliv+". My home town is "+hometown;
		return intro;
	}
	
	public static String get_infromation_from_web(String requrl) throws IOException
	{
		String text=null;
		URL url = new URL(requrl);
	    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	    text = in.readLine();
	    in.close();
		return text;
	}
	
	public static int[] pos_word_check(String p_story, String[] fin_keys){
		String[] pos_keys= p_story.split("[?., \n\t-+;:]");
		int[] post_word_varr=new int[fin_keys.length];
		for(int i=0;i<pos_keys.length;i++){
			for(int j=0;j<post_word_varr.length;j++){
				if(pos_keys[i].equals(fin_keys[j])){
					post_word_varr[j]++;
				}				
			}
		}		
		return post_word_varr;
	}
	
	public static int[] lik_word_check(String p_story, String[] lik_keys){
		String[] pos_keys= p_story.split("[?., \n\t-+;:]");
		int[] post_word_varr=new int[lik_keys.length];
		for(int i=0;i<pos_keys.length;i++){
			for(int j=0;j<post_word_varr.length;j++){
				if(pos_keys[i].equals(lik_keys[j])){
					post_word_varr[j]++;
				}				
			}
		}
		
		return post_word_varr;		
	}
	
	
	public static List<Integer> lex_freq(String a){
        String[] keys = a.split("[?., \n\t-+;:]");
        String[] uniqueKeys;
        int count = 0;int ukeyno=0;
        //System.out.println(a);
        uniqueKeys = getUniqueKeys(keys);
        System.out.println("length of unique keys in lexfreq ="+uniqueKeys.length);
        List<Integer> freq_of_ukeys = new ArrayList<Integer>();
        for(int i=0;i<uniqueKeys.length;i++)freq_of_ukeys.add(0);
        for(String key: uniqueKeys)
        {
            if(null == key)
            {
                break;
            }          
            for(String s : keys)
            {
                if(key.equals(s))
                {
                    count++;
                }              
            }
            //System.out.println("Count of ["+key+"] is : "+count);
            freq_of_ukeys.set(ukeyno,count);
            count=0;
            ukeyno++;
             
        }
        return freq_of_ukeys;
    }

	public static String[] getUniqueKeys(String[] keys)
	    {
	        String[] uniqueKeys = new String[keys.length];

	        uniqueKeys[0] = keys[0];
	        int uniqueKeyIndex = 1;
	        boolean keyAlreadyExists = false;

	        for(int i=1; i<keys.length ; i++)
	        {
	            for(int j=0; j<=uniqueKeyIndex; j++)
	            {
	                if(keys[i].equals(uniqueKeys[j]))
	                {
	                    keyAlreadyExists = true;
	                }
	            }           

	            if(!keyAlreadyExists)
	            {
	                uniqueKeys[uniqueKeyIndex] = keys[i];
	                uniqueKeyIndex++;               
	            }
	            keyAlreadyExists = false;
	        }  
	       
	        String[] uniqueKeysfin = new String[uniqueKeyIndex];
	        
	        for(int j=0;j<uniqueKeyIndex;j++){
	        	uniqueKeysfin[j] = uniqueKeys[j];
	        	
	        }
	        
	        return uniqueKeysfin;
	    }
    
    public static void generate_arff(String[] keys,String[] likes, LinkedList<posts> userpostList) throws IOException, JSONException
    {
    	
    	
    	DataOutputStream boat1 = new DataOutputStream(new FileOutputStream("train.arff"));
    	DataOutputStream boat2 = new DataOutputStream(new FileOutputStream("test.arff"));
    	
    	boat1.writeBytes("@relation autobio\n\n");
    	boat2.writeBytes("@relation autobio\n\n");    	
    	boat1.writeBytes("@attribute likes  numeric\n");
    	boat2.writeBytes("@attribute likes  numeric\n");    	
    	boat1.writeBytes("@attribute comments  numeric\n");
    	boat2.writeBytes("@attribute comments  numeric\n");
    	boat1.writeBytes("@attribute shares  numeric\n");
    	boat2.writeBytes("@attribute shares  numeric\n");    	
    	for(int i=0;i<keys.length;i++)
    	{
			boat1.writeBytes("@attribute keys_"+i+"             "+"numeric\n");
			boat2.writeBytes("@attribute keys_"+i+"             "+"numeric\n");
    	}
    	for(int i=0;i<likes.length;i++)
    	{
			boat1.writeBytes("@attribute keys_"+(keys.length+i)+"             "+"numeric\n");
			boat2.writeBytes("@attribute keys_"+(keys.length+i)+"             "+"numeric\n");
    	}
    	
    	boat1.writeBytes("@attribute class             "+"{-1,1}\n");
		boat2.writeBytes("@attribute class             "+"{-1,1}\n");
    	boat1.writeBytes("\n@data\n");
    	boat2.writeBytes("\n@data\n");  
    	
    	for(int i=0;i<userpostList.size()-1;i++)
    	{
    		
    		posts p1=userpostList.get(i);
    		String p_story = p1.post_story;
    		int[] temp =  pos_word_check(p_story,keys);
    		int[] temp2 = pos_word_check(p_story,likes);
    		
    		if(i%2 == 0){
    			
    			boat1.writeBytes(p1.likes_count+",");
    			boat1.writeBytes(p1.comments_count+",");
    			boat1.writeBytes(p1.share_count+",");
    			for(int k=0;k<temp.length;k++){
    				boat1.writeBytes(temp[k]+",");
    			}
    			for(int k=0;k<temp2.length;k++){
    				boat1.writeBytes(temp2[k]+",");
    			}
    			
    			
    			System.out.println(p_story);
    			System.out.println("Is this Auto-Biographic ?? (y/n)");
    			/*Scanner scan = new Scanner(System.in);
    			String ans = scan.next();
    			
    			if(ans.equals("y"))
    				boat1.writeBytes("1");
    			else if(ans.equals("n"))*/
    				boat1.writeBytes("-1");	
    			
    			boat1.writeBytes("\n");
    			//scan.close();
    		}
    		else{
    			
    			boat2.writeBytes(p1.likes_count+",");
    			boat2.writeBytes(p1.comments_count+",");
    			boat2.writeBytes(p1.share_count+",");
    			for(int k=0;k<temp.length;k++){
    				boat2.writeBytes(temp[k]+",");
    			}
    			for(int k=0;k<temp2.length;k++){
    				boat2.writeBytes(temp2[k]+",");
    			}
    			

    			boat2.writeBytes("-1");
    			boat2.writeBytes("\n");
    		}
    				
    	}
    	  	
    
    	boat1.close();
    	boat2.close();
    	
    }
		
}