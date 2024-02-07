package com.example.gfgapp.pdf;

import com.example.gfgapp.modal.CourseModal;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

// ...

public class PdfGenerator {

//    public static void createPDF(String filePath, String content) {
//        Document document = new Document();
//
//        try {
//            PdfWriter.getInstance(document, new FileOutputStream(filePath));
//            document.open();
//
//            document.add(new Paragraph(content));
//
//        } catch (DocumentException | FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            document.close();
//        }
//    }

    public static void createPDF(String filePath, String name, double mathCore, double math, double englishCore, double english, double scienceCore, double science, double historyCore, double history, double peCore, double pe, double extraCore, double extra, double total) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Add Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Paragraph title = new Paragraph("Student Hour's", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add Paragraphs
            Font paragraphFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
            Paragraph paragraph1 = new Paragraph("\n\n"+name+" has done great so far, he has a total of: "+ total+". ", paragraphFont);
            Paragraph paragraph2 = new Paragraph("\n Here is a list of all the core and non-core hours so far\n\n\n", paragraphFont);
            document.add(paragraph1);
            document.add(paragraph2);

//            // Add Image
//            Image image = Image.getInstance("logo.png");
//            image.setAlignment(Element.ALIGN_CENTER);
//            document.add(image);

            // Add Table
            PdfPTable table = new PdfPTable(3);
            PdfPCell cell = new PdfPCell(new Phrase("Subject"));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
            table.addCell("Core");
            table.addCell("Non-Core");

            table.addCell("Math");
            table.addCell(String.valueOf(mathCore));
            table.addCell(String.valueOf(math));

            table.addCell("English");
            table.addCell(String.valueOf(englishCore));
            table.addCell(String.valueOf(english));

            table.addCell("Science");
            table.addCell(String.valueOf(scienceCore));
            table.addCell(String.valueOf(science));

            table.addCell("History");
            table.addCell(String.valueOf(historyCore));
            table.addCell(String.valueOf(history));

            table.addCell("Pe");
            table.addCell(String.valueOf(peCore));
            table.addCell(String.valueOf(pe));

            table.addCell("Extracurriculars");
            table.addCell(String.valueOf(extraCore));
            table.addCell(String.valueOf(extra));

            document.add(table);

            document.close();
            System.out.println("PDF created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}