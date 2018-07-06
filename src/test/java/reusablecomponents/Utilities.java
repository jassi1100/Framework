package reusablecomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.FrameworkException;
import config.RemoteConfigurations;

public class Utilities {

	static Properties props = new Properties();
	static String strFileName = "config.properties";
	static String strValue;

	static String content[][] = null;
	static FileInputStream file = null;
	static XSSFWorkbook workbook = null;
	static XSSFSheet sheet = null;

	/**
	 * Function to read config parameter from Configuration file.
	 * 
	 * @param strKey
	 *            - Configuration name
	 * @return - string value with configuration name, returns null in case
	 *         configuration parameter not found.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static String getProperty(String strKey) throws FrameworkException {
		try {
			File f = new File(strFileName);
			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				props.load(in);
				strValue = props.getProperty(strKey);
				in.close();
			} else
				throw new FrameworkException("Configuration File not found.");
		} catch (Exception e) {
			throw new FrameworkException("Unknown Error encountered while reading " + strKey
					+ " from configuration file. ---" + e.getClass() + "---" + e.getMessage());
		}
		if (strValue != null) {
			return strValue;
		} else {

			throw new FrameworkException(
					"Value '" + strKey + "' not configured in config file. Contact automation team");
		}

	}

	/**
	 * Get timestamp im yyyymmdd_hhmmss format.
	 * 
	 * @param timeZone
	 *            - local or utc
	 * @return timestamp in desired format.
	 */
	public static String getTimeStamp(String timeZone) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		if (timeZone.toLowerCase().equals("utc")) {
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		} else {
			dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
		}

		return dateFormat.format(new Date());
	}

	/**
	 * Set Configuration parameter in Configuration file.
	 * 
	 * @param strKey
	 *            - Key to be written
	 * @param strValue
	 *            - Value for key
	 * @throws FrameworkException
	 *             in case of error
	 */
	public static void setProperty(String strKey, String strValue) throws FrameworkException {
		try {
			File f = new File(strFileName);
			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				props.load(in);
				props.setProperty(strKey, strValue);
				props.store(new FileOutputStream(strFileName), null);

				in.close();
			} else {
				throw new FrameworkException("Configuration File not found.");
			}
		} catch (Exception e) {
			throw new FrameworkException("Unknown Error encountered while writing " + strKey
					+ " from configuration file. ---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/*
	 * public void removeProperty(String strKey) { try { File f = new
	 * File(strFileName); if (f.exists()) { FileInputStream in = new
	 * FileInputStream(f); props.load(in); props.remove(strKey); props.store(new
	 * FileOutputStream(strFileName), null); in.close(); } else
	 * System.out.println("File not found!"); } catch (Exception e) {
	 * System.out.println(e); }
	 * 
	 * }
	 * 
	 * // return environmental details public static String getHostName() throws
	 * UnknownHostException { InetAddress addr = InetAddress.getLocalHost(); String
	 * hostname = addr.getHostName();
	 * 
	 * return hostname; }
	 * 
	 * public void clean() { props.clear(); }
	 */

	/**
	 * This function is defined to read content from spreadsheet. This will return
	 * content only and will not return headers.
	 * 
	 * @param fileName
	 *            - Complete path of file.
	 * @param sheetName
	 *            - Sheet from where data needs to be retrieved.
	 * @return The content from spreadsheet
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static String[][] Read_Excel(String fileName, String sheetName) throws FrameworkException {
		try {
			file = new FileInputStream(new File(fileName));

			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(sheetName);
			int rowNum = sheet.getLastRowNum();
			workbook.close();
			content = Read_Excel(fileName, sheetName, rowNum);
			return content;
		} catch (FileNotFoundException e) {
			throw new FrameworkException("File " + fileName + " not found for reading.");
		} catch (IOException e) {
			throw new FrameworkException("Exception occured while reading " + fileName);
		} catch (Exception e) {
			throw new FrameworkException("Unknown Exception while reading " + fileName + "&" + sheetName + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * This function is defined to read content from spreadsheet till particular row
	 * number. This will return content only and will not return headers.
	 * 
	 * @param fileName
	 *            - Complete path of file.
	 * @param sheetName
	 *            - Sheet from where data needs to be retrieved.
	 * @param rowNum
	 *            - RowNumber till data needs to be retrieved.
	 * @return The content from spreadsheet
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static String[][] Read_Excel(String fileName, String sheetName, int rowNum) throws FrameworkException {
		try {
			String content[][] = null;
			FileInputStream file = null;
			XSSFWorkbook workbook = null;
			XSSFSheet sheet = null;
			int colNum = 0;

			file = new FileInputStream(new File(fileName));

			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(sheetName);
			colNum = sheet.getRow(0).getLastCellNum();
			content = new String[rowNum][colNum];

			for (int i = 0; i < rowNum; i++) {
				XSSFRow row = sheet.getRow(i + 1);
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = row.getCell(j);
					String value;
					if (cell != null) {
						value = cell.getStringCellValue();
						content[i][j] = value;
					} else {
						content[i][j] = "";
					}
				}
			}

			workbook.close();
			return content;
		} catch (FileNotFoundException e) {
			throw new FrameworkException("File " + fileName + " not found for reading.");
		} catch (IOException e) {
			throw new FrameworkException("Exception occured while reading " + fileName);
		} catch (Exception e) {
			throw new FrameworkException("Unknown Exception while reading " + fileName + "&" + sheetName + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * 
	 * This is to write logs/messages in excel file.
	 * 
	 * @param fileName
	 *            - Complete path of file where error needs to be logged.
	 * @param sheetName
	 *            - Sheet Name from the file, where message needs to be logged.
	 * @param rowNum
	 *            - Row Number where message needs to be written.
	 * @param colNum
	 *            - Column Number where message needs to be written.
	 * @param Message
	 *            - Message/Log
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public void Write_Excel(String fileName, String sheetName, int rowNum, int colNum, String Message)
			throws FileNotFoundException, IOException {
		try {
			FileInputStream file = new FileInputStream(new File(fileName));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(rowNum);
			row.createCell(colNum).setCellValue(Message);
			file.close();
			FileOutputStream outFile = new FileOutputStream(new File(fileName));
			workbook.write(outFile);
			outFile.flush();
			outFile.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			throw new FrameworkException("File " + fileName + " not found for writing.");
		} catch (IOException e) {
			throw new FrameworkException("Exception occured while writing on " + fileName);
		} catch (Exception e) {
			throw new FrameworkException("Unknown Exception while writing on " + fileName + "&" + sheetName + "---"
					+ e.getClass() + "---" + e.getMessage());
		}

	}

	/**
	 * Generates dynamic xpath based on parent path, child path and index.
	 * 
	 * @param parentpath
	 *            - Parent path
	 * @param childpath
	 *            - Child Path
	 * @param i
	 *            - Index
	 * @return xpath
	 */
	public static String generate_xpath(String parentpath, String childpath, int i) {
		return parentpath + "[" + i + "]" + childpath;
	}

	/**
	 * Returns Next Week Date.
	 * 
	 * @return Next week Date.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static String getNextWeekDate() throws FrameworkException {
		return getNextWeekDateFromDate(getCurrentDate());
	}

	/**
	 * Sets configuration based on environment and device. Reads configuration from
	 * Test Data file.
	 * 
	 * @param environment
	 *            - Expected Environment - web or mobile
	 * @param deviceOrBrowser
	 *            - Expected Device or Browser
	 * @throws FrameworkException
	 *             in case of error
	 */
	public static void setConfigurations(String environment, String deviceOrBrowser) throws FrameworkException {
		String[][] remoteConfigurations = Read_Excel(Utilities.getProperty("TEST_DATA_LOCATION"), "Configuration");
		for (int i = 0; i <= remoteConfigurations.length; i++) {
			if (environment.equalsIgnoreCase("web")) {
				if (remoteConfigurations[i][3].equalsIgnoreCase(deviceOrBrowser)) {
					RemoteConfigurations.OS = remoteConfigurations[i][1];
					RemoteConfigurations.OS_VERSION = remoteConfigurations[i][2];
					RemoteConfigurations.BROWSER = remoteConfigurations[i][3];
					RemoteConfigurations.BROWSER_VERSION = remoteConfigurations[i][4];
					break;
				}
			} else {
				if (remoteConfigurations[i][1].equalsIgnoreCase(deviceOrBrowser)) {
					RemoteConfigurations.OS_VERSION = remoteConfigurations[i][2];
					RemoteConfigurations.BROWSER = remoteConfigurations[i][3];
					RemoteConfigurations.BROWSER_VERSION = remoteConfigurations[i][4];
					RemoteConfigurations.PLATFORM = remoteConfigurations[i][5];
					break;
				}
			}
		}
	}

	/**
	 * Returns current date in MM/DD/YYYY format.
	 * 
	 * @return Returns current date.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static String getCurrentDate() throws FrameworkException {
		int month, date, year;
		// String strDate, strMonth;
		String[] Date = LocalDateTime.now().toString().split("T")[0].split("-");
		date = Integer.parseInt(Date[2]);
		month = Integer.parseInt(Date[1]);
		year = Integer.parseInt(Date[0]);

		return month + "/" + date + "/" + year;
	}

	/**
	 * Returns Next Week Date from date entered.
	 * 
	 * @param fromDate
	 *            - From date where Next Week date is required.
	 * @return
	 * @throws FrameworkException
	 */
	public static String getNextWeekDateFromDate(String fromDate) throws FrameworkException {
		int month, dayOfMonth, year;
		String dayOfWeek = null;
		try {

			// String strDate, strMonth;

			String[] Date = fromDate.replace("/", ":").split(":");
			dayOfMonth = ((Integer.parseInt(Date[1])) + 7) % 30;
			month = Integer.parseInt(Date[0]);
			year = Integer.parseInt(Date[2]);

			if (dayOfMonth <= 8 && Date[1] != "1") {
				if (dayOfMonth == 0) {
					dayOfMonth = dayOfMonth + 7;
				}
				month++;

			}

			DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date) parser.parse(year + "-" + month + "-" + dayOfMonth);
			dayOfWeek = new SimpleDateFormat("EE").format(date);

			while (dayOfWeek.toLowerCase().equals("sat") || dayOfWeek.toLowerCase().equals("sun")) {
				dayOfMonth++;
				date = (Date) parser.parse(year + "-" + month + "-" + dayOfMonth);
				dayOfWeek = new SimpleDateFormat("EE").format(date);
				if (dayOfMonth > 29) {
					dayOfMonth = 1;
					month++;
				}
			}
			if (month % 12 == 0) {
				month = 12;
			}
			if (month > 12) {
				year++;
			}
			month = month % 12;
		} catch (Exception e) {
			throw new FrameworkException("Unknown error occured while generating next week date.---" + e.getClass()
					+ "---" + e.getMessage());
		}
		return month + "/" + dayOfMonth + "/" + year;
	}

	public static void sendEmail(String subjectLine, String messageBody, String attachment) throws FrameworkException {
		final String username = getProperty("EMAIL_USER_NAME");
		final String password = getProperty("EMAIL_PASSWORD");
		try {
			Properties props = new Properties();
			/*
			 * // props.put("mail.smtp.auth", "true");
			 * //props.put("mail.smtp.starttls.enable", "true"); //
			 * props.put("mail.smtp.host", "smtp.gmail.com"); //props.put("mail.smtp.port",
			 * "587"); props.put("mail.smtp.host", "smtp.gmail.com");
			 * props.put("mail.smtp.socketFactory.port", "465");
			 * props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			 * props.put("mail.smtp.auth", "true"); props.put("mail.smtp.port", "465"); //
			 * props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			 * // props.put("mail.smtp.socketFactory.fallback", "false");
			 */
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "465");
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(getProperty("EMAIL_USER_NAME")));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(Utilities.getProperty("EMAIL_SEND_TO")));
			// Set Subject: header field
			message.setSubject(subjectLine);

			// message.setText(messageBody);
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText(messageBody);

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			if (attachment != "") {

				DataSource source = new FileDataSource(attachment);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName("Report.html");
				multipart.addBodyPart(messageBodyPart);
			}

			// Send the complete message parts
			message.setContent(multipart);

			Transport.send(message);
		} catch (Exception e) {
			System.out.println("Exception encountered while sending email.---" + e.getClass() + "---" + e.getMessage());
		}

	}

	/**
	 * Returns the current date in (MM/DD/YYYY) format.
	 * 
	 * @param Date
	 * @return Date in in (MM/DD/YYYY) format.
	 * @throws FrameworkException
	 */
	public static String formatDateMMDDYYYY(String Date) {
		String DATE_FORMAT = "MM/dd/yyyy", serviceStatusTilePath;
		int date_Day, date_Month, date_Year;
		try {
			date_Day = Integer.parseInt(Date.split("/")[1]);
			date_Month = Integer.parseInt(Date.split("/")[0]) - 1;
			date_Year = Integer.parseInt(Date.split("/")[2]) - 1900;

			Date convertedServiceDate = new Date(date_Year, date_Month, date_Day);
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Date = sdf.format(convertedServiceDate);
			return Date;
		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown Error encountered while formatting. ---" + e.getClass() + "---" + e.getMessage());
		}
	}

}
