package lk.ijse.gdse67.springposbackend.util;

import java.util.UUID;

public class AppUtil {
    public static String generateCustomerId() {return "C-"+ UUID.randomUUID();}
    public static String generateItemId() {return "I-"+ UUID.randomUUID();}
}
