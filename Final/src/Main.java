
import java.io.IOException;

import entities.field.Field;
import utilities.FieldLoader;
import utilities.FileLoader;

public class Main {
	
	public static void main(String[] args) throws IOException {

		
		
		FieldLoader fl = new FieldLoader();
		
		for (Field field : fl.getFields()) {
			
			
			System.out.println(field);
		}
		
		
		/*
		GameController gc = new GameController();

		gc.play();*/
	}

}
