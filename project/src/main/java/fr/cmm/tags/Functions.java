package fr.cmm.tags;

import org.apache.commons.lang3.StringEscapeUtils;

public class Functions {

    public static String text (String myString){

        return StringEscapeUtils.escapeXml10(myString).replace("\n","<br>");
    }
}
