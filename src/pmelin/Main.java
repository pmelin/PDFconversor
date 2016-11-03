package pmelin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class Main {

	private static File ORIGIN_DIR;
	private static File DESTINATION_DIR;
	private static BufferedReader br = null;
	private static DateFormat SDF = new SimpleDateFormat("yyyy-MM-dd_HH:mm");

	/*
	 * Creates a PDF file
	 * 
	 * @param args no arguments needed
	 */
	public static void main(String[] args) {

		try {
			String originPath = "/origin/";
			String destinationPath = "/pdfs/";

			// Checks if the directories exist

			ORIGIN_DIR = new File(originPath);
			if (!ORIGIN_DIR.exists()) {
				throw new IllegalArgumentException(
						"The origin directory does not exists: " + originPath);
			}

			DESTINATION_DIR = new File(destinationPath);
			if (!DESTINATION_DIR.exists()) {
				throw new IllegalArgumentException(
						"The destination directory does not exists: "
								+ destinationPath);
			}

			// Lists files from origin directory
			File[] TXTFiles = ORIGIN_DIR.listFiles();
			List<File> originFiles = Arrays.asList(TXTFiles);

			for (File file : originFiles) {
				createPdf(file, destinationPath);
			}

		} catch (Exception e) {
			throw new RuntimeException("Error while converting to PDF", e);
		}
	}

	/*
	 * Creates a PDF document
	 * 
	 * @param file - the file that will be converted
	 * 
	 * @param destinationPath - the path to the new PDF document
	 * 
	 * @throws DocumentException
	 * 
	 * @throws IOException
	 */
	private static void createPdf(File file, String destinationPath)
			throws DocumentException, IOException {
		Document pdfDoc = new Document(PageSize.A4);
		String outputFile = destinationPath	+ file.getName().replace(".TXT", ".PDF");
		PdfWriter.getInstance(pdfDoc, new FileOutputStream(outputFile)).setPdfVersion(PdfWriter.VERSION_1_7);
		pdfDoc.open();
		BaseFont courier = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED);
		Font myfont = new Font(courier);
		myfont.setStyle(Font.NORMAL);
		myfont.setSize(8);

		br = new BufferedReader(new FileReader(file));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			Paragraph paragraph = new Paragraph(strLine + "\n", myfont);
			pdfDoc.add(paragraph);
		}
		log(" Converted file "+ file.getName()+ " to pdf.");
		pdfDoc.close();
	}
	
	private static void log(String message){
		System.out.println("CONVERSOR " + SDF.format(new Date()) + message);
	}
}
