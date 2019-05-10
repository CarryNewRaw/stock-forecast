package com.ls.stockforecast.utils.account;

import com.ls.stockforecast.utils.Digests;
import org.apache.shiro.codec.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yaochenglong
 * @date 2018/10/29
 */
public class PasswordUtils {

    private static Logger log = LoggerFactory.getLogger(PasswordUtils.class);

    public static final int SALT_SIZE = 8;
    private static int HASH_ITERATIONS = 1024;      //迭代次数

    /**
     * 生成随机的盐值
     * @return
     */
    public static String createRandomSalt(){
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        return Hex.encodeToString(salt);
    }

    /**
     * 生成加密的密码
     * @param plainPassword
     * @param salt
     * @return
     */
    public static String generateEncryptPassword(String plainPassword, String salt){
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt.getBytes(), HASH_ITERATIONS);
        return Hex.encodeToString(hashPassword);
    }


}
