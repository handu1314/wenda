package cn.edu.bjut.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nowcoder on 2016/7/3.
 */
public class WendaUtil {
    private static final Logger logger = LoggerFactory.getLogger(WendaUtil.class);

    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("生成MD5失败", e);
            return null;
        }
    }

    /**
     * 检测邮箱地址是否合法
     * @param email
     * @return true合法 false不合法
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 检测弱密码
     * @param password
     * @return true合法 false不合法
     */
    public static String isPowerfulPass(String password){
        String msg = null;
        Pattern p1 =  Pattern.compile("[A-Z]+");//必须包含大写字母
        Pattern p2 =  Pattern.compile("[a-z]+");//必须包含小写字母
        Pattern p3 =  Pattern.compile("[0-9]+");//必须包含数字
        if (password.length() < 8){
            msg = new String("密码长度不能小于8位");
            return msg;
        }
        if(!p1.matcher(password).find()){
            msg = msg = new String("密码必须包括至少一个大写字母");
            return msg;
        }
        if(!p2.matcher(password).find()){
            msg = msg = new String("密码必须包括至少一个小写字母");
            return msg;
        }
        if(!p3.matcher(password).find()){
            msg = msg = new String("密码必须包括至少一个数字");
            return msg;
        }
       return msg;
    }
}
