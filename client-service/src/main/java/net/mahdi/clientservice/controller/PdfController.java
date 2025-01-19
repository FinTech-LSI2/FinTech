package net.mahdi.clientservice.controller;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import net.mahdi.clientservice.models.Client;
import net.mahdi.clientservice.models.Compte;
import net.mahdi.clientservice.service.ClientService;
import net.mahdi.clientservice.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.http.ResponseEntity;
import java.io.ByteArrayOutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/client/pdf")
public class PdfController {
    @Autowired
    CompteService compteService ;
    @Autowired
    ClientService clientService ;
    @GetMapping("/pdf/generate/{rib}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable String rib) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Compte compte = compteService.findOne(rib);

            if (compte == null) {
                throw new RuntimeException("Aucun compte trouvé pour le RIB : " + rib);
            }

            // Accéder directement au client associé au compte
            Client client = compte.getClient();
            if (client == null) {
                throw new RuntimeException("Aucun client associé trouvé pour le compte avec le RIB : " + rib);
            }

            // Générer une chaîne représentant le numéro de compte
            String numCompteStr = String.format("%016d", compte.getId());

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            ImageData logo = ImageDataFactory.create(getClass().getResource("/images/img.png"));
            Image img = new Image(logo)
                    .setWidth(150) // Augmentez la largeur (exemple : 150 pixels)
                    .setHeight(75) // Augmentez la hauteur (exemple : 75 pixels)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);
            Paragraph title = new Paragraph("Attestation Bancaire")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(16)
                    .setMarginBottom(20);
            document.add(title);
            document.add(new Paragraph("Nous soussignés BMCE BANK, Agence Marrakech Grand Atlas, "
                    + "attestons par la présente que Monsieur/Madame "+ client.getFirstname() +" "+ client.getLastname() + "" +
                    ", titulaire d'une pièce d'identité numéro "+ client.getCIN() + " , est détenteur d’un compte de chèque ordinaire ouvert sur nos livres sous le numéro "+ numCompteStr +", dont le RIB (IBAN) est :")
                    .setFontSize(12)
                    .setMarginBottom(15));
            document.add(new Paragraph("BANQUE : BMCE BANK").setFontSize(12).setMarginBottom(5));
            document.add(new Paragraph("ADRESSE : Agence ENT Grand Atlas Avenue Abdelkrim Khattabi, Résidence Sofia, Palmeraie 2, Marrakech")
                    .setFontSize(12).setMarginBottom(5));
            document.add(new Paragraph("TEL : +212 524 44 68 35/36").setFontSize(12).setMarginBottom(5));
            document.add(new Paragraph("IBAN : MA64" + compte.getRib()).setFontSize(12).setMarginBottom(5));
            document.add(new Paragraph("RIB : "+ compte.getRib()).setFontSize(12).setMarginBottom(5));
            document.add(new Paragraph("BIC : CNCAMAMR").setFontSize(12).setMarginBottom(15));
            document.add(new Paragraph("La présente attestation est délivrée à l'intéressé(e) sur sa demande pour servir et valoir ce que de droit.")
                    .setFontSize(12).setTextAlignment(TextAlignment.JUSTIFIED));
            document.close();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "Attestation_Bancaire.pdf");
            return ResponseEntity.ok().headers(headers).body(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

}