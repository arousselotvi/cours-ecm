package fr.cmm.tags;
import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionsTest {
    @Test
    public void textTest (){
        assertEquals("a", Functions.text("a"));
        assertEquals("a<br>", Functions.text("a\n"));
        assertEquals("&amp;a", Functions.text("&a"));
    }

}