package main.java.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.java.gui.Stamp;

public class ObjectWriteExperiment <V>{
	public static void main(String args[]) {

		//ObjectWriteExperiment obj = new ObjectWriteExperiment();

		/*
		Address address = new Address();
		address.setStreet("wall street");
		address.setCountry("united state");

		obj.serializeAddressJDK7(address);
		*/
	}
	
	public static Stamp[][] inputObject(){
		FileInputStream fin = null;
		ObjectInputStream oin = null;
		
		try{
			
			fin = new FileInputStream(System.getProperty("user.dir")+"\\stamps.txt");
			oin = new ObjectInputStream(fin);
			
			Object obj = oin.readObject();
			
			if(obj instanceof Stamp[][]){
				if(fin != null){
					fin.close();
				}
				if(oin != null){
					oin.close();
				}
				return (Stamp[][])obj;
			}
			
		}catch(Exception ex){
			
		}
		return null;
	}

	public static void serializeAddress(Stamp[][] stamps) {

		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		try {

			System.out.println(System.getProperty("user.dir"));
			
			fout = new FileOutputStream(System.getProperty("user.dir")+"\\stamps.txt");
			oos = new ObjectOutputStream(fout);
			oos.writeObject(stamps);

			System.out.println("Done");

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void serializeAddressJDK7(Stamp[][] stamps) {

		try (ObjectOutputStream oos = 
				new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir")+"\\stamps.txt"))) {

			oos.writeObject(stamps);
			System.out.println("Done");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
