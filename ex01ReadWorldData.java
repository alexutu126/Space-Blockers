//WMS3 2015

package com.cryotrax.game;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Gdx;

//----------------------------------------------------------------

@SuppressWarnings("unused")
public class ex01ReadWorldData {
	public FileHandle file;
	public ArrayList<String> list_electrotrp;
	public ArrayList<String> list_electrotrp_elements;
	public ArrayList<CRBlockertType> list_electrotrp_elements_type;
	public BufferedReader reader; 
	public String line = "";
	public String[] elements;
	
	public ex01ReadWorldData(String file_name){
		list_electrotrp = new ArrayList<String>();
		list_electrotrp_elements = new ArrayList<String>();
		list_electrotrp_elements_type = new ArrayList<CRBlockertType>();
		file = Gdx.files.internal(file_name);
		reader = new BufferedReader(file.reader());
		line = "";
		try {
			line = reader.readLine();
		    // process the line.
			if(!line.contains("&")){
			    String[] elements = line.split("#");
			    if(elements[0].equals("ET")){ 
			    	list_electrotrp.add(elements[1]);
			    }		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while( line != null ) {
		   // process the line.
		   if(!line.contains("&")){	
			   elements = line.split("#");
			   if(elements[0].equals("ET")){	
				   list_electrotrp.add(elements[1]);
			   }
			   if(elements[0].equals("ETBLRR")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.RoundRobin);
			   }
			   else if(elements[0].equals("ETBLRO")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.Rotative);
			   }		   			   
			   else if(elements[0].equals("ETBLBO")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.Bouncer);
			   }		   	
			   else if(elements[0].equals("ETBLROBO")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.RotativeBouncer);
			   }
			   else if(elements[0].equals("ETBLRORR")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.RotativeRobin);
			   }			   
			   else if(elements[0].equals("ETBLRRBO")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.RobinBouncer);
			   }			   
		   }
		   try {
			   line = reader.readLine();
		   } catch (IOException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }		   
		}
	}
	
	public void ex01ReadWorldDataReload(String file_name){
		list_electrotrp.clear();
		list_electrotrp_elements.clear();
		list_electrotrp_elements_type.clear();
		file = Gdx.files.internal(file_name);
		try {
			reader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		reader = new BufferedReader(file.reader());
		line = "";
		try {
			line = reader.readLine();
		    // process the line.
			if(!line.contains("&")){
			    elements = line.split("#");
			    if(elements[0].equals("ET")){ 
			    	list_electrotrp.add(elements[1]);
			    }		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while( line != null ) {
		   // process the line.
		   if(!line.contains("&")){	
			   elements = line.split("#");
			   if(elements[0].equals("ET")){	
				   list_electrotrp.add(elements[1]);
			   }
			   if(elements[0].equals("ETBLRR")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.RoundRobin);
			   }
			   else if(elements[0].equals("ETBLRO")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.Rotative);
			   }		   			   
			   else if(elements[0].equals("ETBLBO")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.Bouncer);
			   }		   		
			   else if(elements[0].equals("ETBLROBO")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.RotativeBouncer);
			   }
			   else if(elements[0].equals("ETBLRORR")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.RotativeRobin);
			   }			   
			   else if(elements[0].equals("ETBLRRBO")){	
				   list_electrotrp_elements.add(elements[1]);
				   list_electrotrp_elements_type.add(CRBlockertType.RobinBouncer);
			   }
		   }
		   try {
			   line = reader.readLine();
		   } catch (IOException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }		   
		}
	}
	
	public void Dispose(){
		if(list_electrotrp!=null){
			list_electrotrp.clear();
			list_electrotrp = null;
		}
		if(list_electrotrp_elements!=null){
			list_electrotrp_elements.clear();
			list_electrotrp_elements = null;
		}
		if(list_electrotrp_elements_type!=null){
			list_electrotrp_elements_type.clear();
			list_electrotrp_elements_type = null;
		}
		file = null;
		if(reader!=null){
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			reader = null;
		}
		line = null;
		for(int i=0; i<elements.length; i++){
			elements[i] = null;
		}
		elements = null;
	}
}
