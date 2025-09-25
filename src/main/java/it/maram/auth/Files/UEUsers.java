package it.maram.auth.Files;

import it.maram.auth.EncriptionAlgs;
import it.maram.auth.SymmetricDecryptor;
import it.maram.auth.SymmetricEncryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class UEUsers {

    public static byte[] decryptAndRead(final File file, final PublicKey key)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(file == null || !file.exists()) throw new FileNotFoundException();
        final SymmetricDecryptor sd = new SymmetricDecryptor(key, EncriptionAlgs.RSAECBPKCS1);
        final byte[] bytes = Files.readAllBytes(file.toPath());
        return sd.decrypt(bytes);
    }
    public static boolean encryptAndWrite(final File file, final PrivateKey key, final byte[] toEncrypt) {
        try {
            Files.deleteIfExists(file.toPath());
            final SymmetricEncryptor sd = new SymmetricEncryptor(key, EncriptionAlgs.RSAECBPKCS1);
            final byte[] bytes = sd.encrypt(toEncrypt);
            Files.createFile(file.toPath());
            Files.write(file.toPath(), bytes);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
