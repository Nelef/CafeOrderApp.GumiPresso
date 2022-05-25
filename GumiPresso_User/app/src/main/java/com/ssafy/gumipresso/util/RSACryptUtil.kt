package com.ssafy.gumipresso.util

import android.R.attr.publicKey
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.common.util.Base64Utils
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher


class RSACryptUtil {
    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(input: String, key: PublicKey): String {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val bytePlain = cipher.doFinal(input.toByteArray())
        return Base64.getEncoder().encodeToString(bytePlain);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(input: String, key: PrivateKey): String {
        val cipher = Cipher.getInstance("RSA_ECB_PKCS1_PADDING")
        var byteEncrypt: ByteArray = Base64.getDecoder().decode(input.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decrypt = cipher.doFinal(byteEncrypt)
        return String(decrypt)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPublicKeyFromBase64Encrypted(base64PublicKey: String): PublicKey{
        return KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey)))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPrivateKeyFromBase64Encrypted(base64PrivateKey: String): PrivateKey{
        return KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey)))
    }


}