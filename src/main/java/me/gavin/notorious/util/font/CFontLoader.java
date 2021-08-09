package me.gavin.notorious.util.font;

import java.awt.Font;

public class CFontLoader {

    public static final Font MULI_SEMIBOLD = getFontByName("muli-semibold").deriveFont(18.0F);
    public static final Font COMFORTAA = getFontByName("comfortaa").deriveFont(18.0F);
    public static final Font HELVETICA_BOLD = getFontByName("helvetica-bold").deriveFont(18.0F);
    public static final Font HELVETICA = getFontByName("helvetica").deriveFont(18.0F);

    public static Font getFontByName(String name) {
        return name.equalsIgnoreCase("muli-semibold") ? getFontFromInput("/assets/notorious/Muli-SemiBold.ttf") : (name.equalsIgnoreCase("Comfortaa") ? getFontFromInput("/assets/notorious/Comfortaa-Regular.ttf") : (name.equalsIgnoreCase("helvetica-bold") ? getFontFromInput("/assets/notorious/Helvetica-Bold-Font.ttf") : (name.equalsIgnoreCase("helvetica") ? getFontFromInput("/assets/notorious/Helvetica.ttf") : null)));
    }

    public static Font getFontFromInput(String path) {
        try {
            Font e = Font.createFont(0, CFontLoader.class.getResourceAsStream(path));

            return e;
        } catch (Exception exception) {
            return null;
        }
    }
}
