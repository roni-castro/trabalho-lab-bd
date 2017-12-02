package vendaVendedor;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDF {

    Document documentoPDF = new Document();
    PdfPTable pdftbl = new PdfPTable(2);
    PdfWriter instance;

    PDF(int qtdColunas) {
        pdftbl = new PdfPTable(qtdColunas);
        pdftbl.setWidthPercentage(100);
        pdftbl.setSpacingBefore(0f);
        pdftbl.setSpacingAfter(0f);

    }

    public void gerarPF(String nomeRelatorio, String titulo) {
        try {
            //criar instancia e dar nome ao PDF
            instance = PdfWriter.getInstance(documentoPDF, new FileOutputStream("..\\Relatorios\\" + nomeRelatorio + ".pdf"));
            documentoPDF.open();
            documentoPDF.setPageSize(PageSize.A4);
            documentoPDF.setMargins(36, 72, 108, 180);
            documentoPDF.setMarginMirroring(true);

            

            //adicinando primeiro paragrafo
            String quebraLinha = "--------------------------------";
            documentoPDF.add(new Paragraph(titulo, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.RED)));
            documentoPDF.add(new Paragraph(quebraLinha + quebraLinha + quebraLinha + quebraLinha));

            documentoPDF.add(pdftbl);

        } catch (DocumentException ex) {
            System.out.println("Erro ao criar Documento PDF: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro de E/S: " + ex.getMessage());
        } finally {
            documentoPDF.close();
        }
    }

    public void adicionarCelulaTabela(String celula) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA,8);
        PdfPCell pdfCell = new PdfPCell(new Phrase(celula,font));
        pdftbl.addCell(pdfCell);        
               
    }

    public void adicionarColunaTabela(String[] col) {
        for (String coluna : col) {
            PdfPCell pdfTitulo = new PdfPCell(new Paragraph(coluna));
            pdfTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTitulo.setBackgroundColor(BaseColor.LIGHT_GRAY);
            pdftbl.addCell(pdfTitulo);
        }
    }
}
