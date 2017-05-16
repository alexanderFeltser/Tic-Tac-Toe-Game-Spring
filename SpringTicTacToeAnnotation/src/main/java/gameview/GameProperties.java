package gameview;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import model.CellType;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class- stores names of the used icon files Icons of X- cell,
 * 0-cell, empty cell, win icon. May be default game configuration...
 *
 */
@Configuration
public class GameProperties {
	private String xIconFileName;
	private String oIconFileName;
	private String emptyIconeFileName;
	private String winIconeFileName;

	public GameProperties() throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("Source/config.properties");
		// load a properties file
		prop.load(input);
		input.close();
		xIconFileName = prop.getProperty("xIconFileName");
		oIconFileName = prop.getProperty("0IconFileName");
		emptyIconeFileName = prop.getProperty("emptyIconeFileName");
		winIconeFileName = prop.getProperty("winIconeFileName");
	}

	public final String getxIconFileName() {
		return xIconFileName;
	}

	public final String getZeroIconFileName() {
		return oIconFileName;
	}

	/**
	 * @return
	 */
	public final String getEmptyIconeFileName() {
		return emptyIconeFileName;
	}

	public String getIconFileByCellType(CellType cellType) {
		if (cellType == CellType.X) {
			return getxIconFileName();
		}
		if (cellType == CellType.Zero) {
			return getZeroIconFileName();
		}
		if (cellType == CellType.WIN) {
			return getWinIconFileName();
		}
		return getZeroIconFileName();

	}

	public String getWinIconFileName() {

		return winIconeFileName;
	}

	public static void main(String[] args) throws IOException {

		GameProperties prop = new GameProperties();
		// get the property value and print it out
		System.out.println("EmptyIconeFileName: " + prop.getEmptyIconeFileName());
		System.out.println("oIconFileName: " + prop.getZeroIconFileName());
		System.out.println("xIconFileName: " + prop.getxIconFileName());

	}

}
