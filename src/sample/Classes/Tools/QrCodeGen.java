package sample.Classes.Tools;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class QrCodeGen {

    /*
    This method takes the text to be encoded, the width and height of the QR Code,
    and returns the QR Code in the form of a byte array.
    */
    public byte[] GenerateQRCode(String text) throws WriterException, IOException
    {
        /*
        height and width of
        the qr code image
        */
        final int height = 350;
        final int width = 350;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        return pngData;
    }

    public Image ConvertByteToImage(byte[] data) throws IOException
    {
        Image img = new Image(new ByteArrayInputStream(data));
        return img;
    }

    public String Build(String fname, String lname, String number, String Address, Double balance)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Customer Name : " + fname + " " + lname + "\n");
        builder.append("Address       : " + Address + "\n");
        builder.append("Mobile Number : " + number + " \n");
        builder.append("Balance       : " + balance + "\n");

        return builder.toString();
    }

}
