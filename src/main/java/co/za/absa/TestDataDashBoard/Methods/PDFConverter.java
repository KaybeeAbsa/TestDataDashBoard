package co.za.absa.TestDataDashBoard.Methods;

import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import be.quodlibet.boxable.*;
import be.quodlibet.boxable.line.LineStyle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
public class PDFConverter {

    private final PDFont FONT = PDType1Font.HELVETICA;
    private final float FONT_SIZE = 12;
    private final float LEADING = -1.5f * FONT_SIZE;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    LocalDateTime now = LocalDateTime.now();

    public void test(String Department, String requesterName, int quantity, String email, String brand) throws IOException {


        String quan = Integer.toString(quantity);
        double total = 0;
        int unitPrice=0;
        String unitValue="";
        String totalPrice = "";


        String brandCode="";
        String supplier="";

        if(brand.equalsIgnoreCase("2630")){
            brandCode="AVDC";
        }
        else if(brand.equalsIgnoreCase("2148")){
            brandCode="CSCD";
            supplier="idemia";
        }
        else if(brand.equalsIgnoreCase("2626")){
            brandCode="LSDC";
        }
        else if(brand.equalsIgnoreCase("2812")){
            brandCode="VASA";
        }
        else if(brand.equalsIgnoreCase("2632")){
            brandCode="LSVD";
        }
        else if(brand.equalsIgnoreCase("2132")){
            brandCode="VSDC";
        }
        else if(brand.equalsIgnoreCase("2847")){
            brandCode="VMDC";
        }
        else if(brand.equalsIgnoreCase("2147")){
            brandCode="CMCD";
            supplier="idemia";
        }
        else if(brand.equalsIgnoreCase("2149")){
            brandCode="RMCD";
            supplier="idemia";
        }
        else if(brand.equalsIgnoreCase("2186")){
            brandCode="UGOC";
            supplier="idemia";
        }
        else if(brand.equalsIgnoreCase("2324")){
            brandCode="VPRC";
        }
        else if(brand.equalsIgnoreCase("2189")){
            brandCode="UPDC";
            supplier="idemia";
        }
        else if(brand.equalsIgnoreCase("2826")){
            brandCode="VATD";
        }
        else if(brand.equalsIgnoreCase("2830")){
            brandCode="VAPD";
        }
        else if(brand.equalsIgnoreCase("2698")){
            brandCode="DGDC";
            supplier="Gemalto";
        }
        else if(brand.equalsIgnoreCase("2699")){
            brandCode="DPDC";
            supplier="Idemia";
        }
        else if(brand.equalsIgnoreCase("2810")){
            brandCode="VGBA";
        }
        else if(brand.equalsIgnoreCase("2629")){
            brandCode="LTDC";
        }
        else if(brand.equalsIgnoreCase("2141")){
            brandCode="PVDC";
            supplier="Idemia";
        }
        else if(brand.equalsIgnoreCase("2822")){
            brandCode="VPBE";
        }
        else if(brand.equalsIgnoreCase("2350")){
            brandCode="VWDC";
        }
        else if(brand.equalsIgnoreCase("2618")){
            brandCode="LGDC";
            supplier="Idemia";
        }
        else if(brand.equalsIgnoreCase("2623")){
            brandCode="PIDC";
        }
        else if(brand.equalsIgnoreCase("2836")){
            brandCode="VPBG";
        }
        else if(brand.equalsIgnoreCase("2198")){
            brandCode="RTDC";
            supplier="Gemalto";
        }
        else if(brand.equalsIgnoreCase("2152")){
            brandCode="RSCD";
            supplier="Idemia";
        }
        else if(brand.equalsIgnoreCase("2993")){
            brandCode="RACF";
            supplier="Idemia";
        }
        else if(brand.equalsIgnoreCase("2841")){
            brandCode="VHDC";
        }
        else if(brand.equalsIgnoreCase("2785")){
            brandCode="RHDC";
        }
        else if(brand.equalsIgnoreCase("2399")){
            brandCode="RMDC";
        }
        else if(brand.equalsIgnoreCase("2627")){
            brandCode="LYDC";
        }
        else if(brand.equalsIgnoreCase("2846")){
            brandCode="RMYD";
        }
        else if(brand.equalsIgnoreCase("2861")){
            brandCode="RHDA";
        }
        else if(brand.equalsIgnoreCase("2983")){
            brandCode="RVAV";
        }
        else if(brand.equalsIgnoreCase("2159")){
            brandCode="VPBG";
        }
        else if(brand.equalsIgnoreCase("2836")){
            brandCode="FDVF";
        }
        else if(brand.equalsIgnoreCase("2145")){
            brandCode="MVCC";
            supplier="Gemalto";
        }
        else if(brand.equalsIgnoreCase("2811")){
            brandCode="VAPC";
        }
        else if(brand.equalsIgnoreCase("2836")){
            brandCode="VPBG";
        }
        else if(brand.equalsIgnoreCase("2975")){
            brandCode="RFVF";
        }
        else if(brand.equalsIgnoreCase("2144")){
            brandCode="VPBG";
        }
        else if(brand.equalsIgnoreCase("2836")){
            brandCode="FSCC";
            supplier="Idemia";
        }
        else if(brand.equalsIgnoreCase("2500")){
            brandCode="PAAA";
        }
        else if(brand.equalsIgnoreCase("2506")){
            brandCode="LVPM";
        }
        else if(brand.equalsIgnoreCase("2505")){
            brandCode="LVPV";
        }
        else if(brand.equalsIgnoreCase("1565")){
            brandCode="CBZV";
        }
        else if(brand.equalsIgnoreCase("2157")){
            brandCode="UBCH";
        }
        else if(brand.equalsIgnoreCase("2619")){
            brandCode="LFDC";
            supplier="Idemia";
        }
        else if(brand.equalsIgnoreCase("2162")){
            brandCode="IYDC";
        }
        else if(brand.equalsIgnoreCase("2196")){
            brandCode="RYDC";
        }
        else if(brand.equalsIgnoreCase("2628")){
            brandCode="LYIC";
        }
        else if(brand.equalsIgnoreCase("2820")){
            brandCode="PBBA";
        }
        else if(brand.equalsIgnoreCase("1566")){
            brandCode="CCZM";
        }
        else if(brand.equalsIgnoreCase("2979")){
            brandCode="RIVF";
        }
        else if(brand.equalsIgnoreCase("2896")){
            brandCode="IYVC";
        }
        else if(brand.equalsIgnoreCase("2122")){
            brandCode="UBBQ";
        }
        else if(brand.equalsIgnoreCase("2503")){
            brandCode="PAAB";
        }
        else if(brand.equalsIgnoreCase("2806")){
            brandCode="NQQR";
        }
        else if(brand.equalsIgnoreCase("2845")){
            brandCode="VMYD";
        }
        else if(brand.equalsIgnoreCase("1553")){
            brandCode="CBZB";
        }
        else if(brand.equalsIgnoreCase("2827")){
            brandCode="RATD";
        }
        else if(brand.equalsIgnoreCase("2815")){
            brandCode="RAPC";
        }
        else if(brand.equalsIgnoreCase("2813")){
            brandCode="RASA";
        }
        else if(brand.equalsIgnoreCase("2832")){
            brandCode="VAYD";
        }
        else if(brand.equalsIgnoreCase("2988")){
            brandCode="IAAS";
        }
        else if(brand.equalsIgnoreCase("2897")){
            brandCode="PEMC";
            supplier="Idemia";
        }


        if(supplier.equalsIgnoreCase("Idemia")){
            unitPrice=80;
            if(quantity<=10){
            total=quantity*unitPrice;
                totalPrice = decfor.format(total);
            }
            else{
                int additional=quantity-10;
                int base=10*unitPrice;
                double additionalPrice=additional*13.04;
                total=base+additionalPrice;
                totalPrice = decfor.format(total);
            }
            unitValue=Integer.toString(unitPrice);
        }
        else{
            unitPrice=89;
             total = quantity * unitPrice;
            totalPrice = decfor.format(total);
            unitValue=Integer.toString(unitPrice);
        }




        String args[] = new String[0];

        String outputFileName = "C:/Temp/Quotation" + email.split("@")[0] + ".pdf";


        if (args.length > 0)
            outputFileName = args[0];


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");

        // Create a new font object selecting one of the PDF base fonts
        PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;

        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        // PDRectangle.LETTER and others are also possible
        PDRectangle rect = page.getMediaBox();
        // rect can be used to get the page width and height
        document.addPage(page);

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream cos = new PDPageContentStream(document, page);


    PDRectangle mediaBox = page.getMediaBox();
        PDImageXObject imageXObject = PDImageXObject.createFromFile("src/main/resources/static/css/absalogo1.png", document);
        int widt = imageXObject.getWidth();
        int hei = imageXObject.getHeight();


        System.out.println(widt + "size of hei: " + hei);
        System.out.println(mediaBox.getWidth());
        System.out.println(mediaBox.getHeight());
        //float startX = (mediaBox.getWidth() - imageXObject.getWidth()) / 10;
        float startX = (mediaBox.getWidth() - imageXObject.getWidth()) / 100;
        float startY = (mediaBox.getHeight() - imageXObject.getHeight()) / 1;

        cos.drawImage(imageXObject, startX, startY);


       /* //Begin the Content stream
        cos.beginText();

        //Setting the font to the Content stream
        cos.setFont(PDType1Font.TIMES_ROMAN, 12);


        //Setting the position for the line
        cos.newLineAtOffset(25, 700);

        String text = "Hi!!! This is the multiple text content example.";*/
      /*  String Line1 = "Here, we discussed about how to add text content in the pages of the PDF document.";
        String Line2 = "We do this by using the ShowText() method of the ContentStream class";*/

        //Adding text in the form of string
        //cos.showText(text);
        //cos.newLine();
    /*    cos.showText(Line1);
        cos.newLine();
        cos.showText(Line2);*/

        //Ending the content stream
        //cos.endText();


        //Dummy Table
        float margin = 50;
        // starting y position is whole page height subtracted by top and bottom margin
        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
        // we want table across whole page width (subtracted by left and right margin ofcourse)
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;
        // y position is your coordinate of top left corner of the table
        float yPosition = 500;

        BaseTable table = new BaseTable(yPosition, yStartNewPage,
                bottomMargin, tableWidth, margin, document, page, true, drawContent);


        // the parameter is the row height
        Row<PDPage> headerRow1 = table.createRow(50);
        // the first parameter is the cell width
        Cell<PDPage> cell = headerRow1.createCell(100, "Requestor Name: " + requesterName +"<br><br>Department: "+Department+"<br><br>Quotation Date: "+date.format(now)+"");
        cell.setFontSize(10);
        // vertical alignment
        cell.setValign(VerticalAlignment.MIDDLE);
        // border style
        cell.setTopBorderStyle(new LineStyle(Color.BLACK, 10));
        table.addHeaderRow(headerRow1);



        // the parameter is the row height
        Row<PDPage> headerRow = table.createRow(50);
        // the first parameter is the cell width
        Cell<PDPage> cell1 = headerRow.createCell(100, "On behalf of (" + supplier + ")");
        cell1.setFont(fontBold);
        cell1.setFontSize(30);
        // vertical alignment
        cell1.setValign(VerticalAlignment.MIDDLE);
        // border style
        cell1.setTopBorderStyle(new LineStyle(Color.BLACK, 10));
        table.addHeaderRow(headerRow);


        Row<PDPage> rowCon = table.createRow(20);
        cell = rowCon.createCell(100, "Quotation is only valid for 60 days.");
        cell.setTextColor(Color.RED);
        cell.setFontSize(15);
        cell.setFont(fontItalic);
        cell.setAlign(HorizontalAlignment.CENTER);
        // horizontal alignment
        cell.setBottomBorderStyle(new LineStyle(Color.BLACK, 5));


        Row<PDPage> row = table.createRow(20);
        cell = row.createCell(33, "Quantity");
        cell.setFontSize(15);
        cell.setFont(fontBold);
        cell = row.createCell(33, "Description");
        cell.setFontSize(15);
        cell.setFont(fontBold);
        cell = row.createCell(34, "Unit Price");
        cell.setFontSize(15);
        cell.setFont(fontBold);


        row = table.createRow(20);
        cell = row.createCell(33, quan);
        cell.setFontSize(15);
        // rotate the text

        cell.setAlign(HorizontalAlignment.LEFT);
        cell.setValign(VerticalAlignment.MIDDLE);
        // long text that wraps
        cell = row.createCell(33,brandCode );
        cell.setFontSize(12);
        // long text that wraps, with more line spacing
        cell = row.createCell(34, "R " + unitValue + ".00");
        cell.setFontSize(12);

        row = table.createRow(20);
        cell = row.createCell(33, "");
        cell.setFontSize(15);
        // rotate the text
        cell.setTextRotated(true);
        cell.setAlign(HorizontalAlignment.RIGHT);
        cell.setValign(VerticalAlignment.MIDDLE);
        // long text that wraps
        cell = row.createCell(33, "Total (excl VAT)");
        cell.setFontSize(12);
        // long text that wraps, with more line spacing
        cell = row.createCell(34, "R " + totalPrice);
        cell.setFontSize(12);


        row = table.createRow(20);
        cell = row.createCell(100, "Term and Conditions <br>Offer is subject to (insert embosser name) General Sales Conditions<br>Payment Term: Cards will only be generated once a purchase order has been received<br>Currency of the quote: ZAR<br>Currency of invoicing: ZAR<br>The purchase order and this quote should be emailed to: xxx@absa.africa");
        cell.setTextColor(Color.BLACK);
        cell.setFontSize(10);
        //cell.setFont(fontItalic);
        cell.setAlign(HorizontalAlignment.LEFT);
        // horizontal alignment
        cell.setBottomBorderStyle(new LineStyle(Color.BLACK, 5));


        table.draw();

        float tableHeight = table.getHeaderAndDataHeight();
        System.out.println("tableHeight = " + tableHeight);

        // close the content stream
        cos.close();

        // Save the results and ensure that the document is properly closed:
        document.save(outputFileName);
        document.close();

    }
}
