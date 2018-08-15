package common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class NameReader {

	private static NameReader nameReader = new NameReader();

	private static ThreadLocal<Random> threadLocal = new ThreadLocal<>();

	private static int NAMELISTSIZE = 0;

    private ArrayList<String>  namelist;

	private NameReader(){
		namelist = new ArrayList<>();
	}

	static {
        getInstance().read("src/main/resources/data/name");
        NAMELISTSIZE = getInstance().namelist.size();
    }

	public static NameReader getInstance(){
		return nameReader;
	}
	public void read(String path){
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			String oneLine = "";
			String[] names = null;
			while((oneLine = br.readLine())!=null){
				if(oneLine.length()==0) {
                    continue;
                }
				names = oneLine.split("[ ]+");
				for(String string : names){
                    if(string.length() >1){
                        namelist.add(string);
                    }
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

    public String getName(){
	    if(threadLocal.get() == null){
	        threadLocal.set(new Random());
        }
	    return namelist.get(threadLocal.get().nextInt(NAMELISTSIZE));
    }
}
