package it.maram.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.HashMap;

public class QRCode {
    protected final HashMap<EncodeHintType, Object> hints = new HashMap<>();
    public static final MatrixToImageConfig DEFAULT_CONFIG = new MatrixToImageConfig();
    protected final String text;
    protected Writer writer;
    protected MatrixToImageConfig matrixToImageConfig;
    protected int width = 125, height = 125;
    protected String imageType;
    protected QRCode(String text){
        this.matrixToImageConfig = DEFAULT_CONFIG;
        this.text = text;
        this.writer = new QRCodeWriter();
        this.imageType = "PNG";
    }
    public static QRCode qrCodeFrom(String text){
        return new QRCode(text);
    }
    public QRCode withCharset(String charset){
        return this.withHint(EncodeHintType.CHARACTER_SET, charset);
    }
    public QRCode withHint(EncodeHintType type, Object value){
        this.hints.put(type, value);
        return this;
    }
    protected void writeToStream(OutputStream stream) throws WriterException, IOException {
        MatrixToImageWriter.writeToStream(this.createMatrix(this.text), this.imageType, stream, this. matrixToImageConfig);
    }
    public BitMatrix createMatrix(String text) throws WriterException {
        return this.writer.encode(text, BarcodeFormat.QR_CODE, this.height, this.height, this.hints);
    }
    public static Image generateQRCode(String value) throws IOException, WriterException {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        qrCodeFrom(value).withCharset("UTF-8").writeToStream(stream);
        final InputStream is = new ByteArrayInputStream(stream.toByteArray());
        Image image = null;
        image = ImageIO.read(is);
        return image;
    }
}
