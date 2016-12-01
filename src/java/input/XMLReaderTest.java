package input;

import static org.junit.Assert.*;

import org.junit.Test;

import configuration.Settings;

public class XMLReaderTest {


	@Test
	public void testReadSettings() {
		XMLReader ml = new XMLReader();
		ml.readSetting("./files/settings.xml");
		Settings set = Settings.getInstance();
		assertEquals(set.getSAPDataFileFolder(), "E:/FTPROOT/FTPOSCDEV/Customer");
	}

}
