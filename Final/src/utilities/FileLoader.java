package utilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Frederik (FM)
 * @since 11/11/2017 13:13 (FM): created method
 */
public class FileLoader {

	/**
	 * @author Frederik (FM)
	 * @param filename
	 *            Name of textfile to read.
	 * @return Return String[] with each text line pr. array element so: line 1 =
	 *         array[0] etc.
	 * @since 11/11/2017 13:13 (FM): created method
	 */
	// Det er denne l�sning jeg er g�et med:
	// https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar
	// https://stackoverflow.com/questions/8258244/accessing-a-file-inside-a-jar-file
	public String[] LoadTextFile(String filename) {

		InputStream in = getInputStream(filename);
		ArrayList<String> list = new ArrayList<String>();

		try {
			// N�r man laver br p� denne m�de s� lukkes resourcerne automatisk - ikke
			// noget med br.close() - se evt. link ovenfor!
			try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

				String currentLine = br.readLine();
				while (currentLine != null) {
					list.add(currentLine);

					currentLine = br.readLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Problably overkill here but :)
		list.trimToSize();
		
		String[] array = new String[list.size()];
		return list.toArray(array);
	}

	/**
	 * @author Frederik (FM)
	 * @param filename
	 *            Name of file to load
	 * @return InputStream that contains the loaded file.
	 * @since 11/11/2017 13:13 (FM): created method
	 */
	private InputStream getInputStream(String filename) {
		return getClass().getResourceAsStream(filename);
	}

	/**
	 * @author Frederik (FM)
	 * @param filename
	 *            Name of file to load
	 * @return InputStream that contains the loaded file.
	 * @since 11/11/2017 13:13 (FM): created method
	 */
	public Document LoadXmlFile(String filename) {

		InputStream in = getInputStream(filename);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Document doc = null;

		dbf = DocumentBuilderFactory.newInstance();

		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(in);
			in.close();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		return doc;
	}
}
