import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.swing.Spring;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XmlParser2 {

	public String parse(String input) {
		StringBuffer output = new StringBuffer ();
		try {
			File f = new File(input);
			Scanner s = new Scanner(f);
			while (s.hasNextLine()) {
				output.append("\n"+s.nextLine());

			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output.toString();
	}
}
