package fr.cmm.helper;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PaginationTest {

    @Test
    public void getPageCount() {
        Pagination pagination = new Pagination();
        pagination.setPageSize(50);
        pagination.setCount(20);
        Assert.assertEquals(1, pagination.getPageCount());
        pagination.setCount(150);
        Assert.assertEquals(3, pagination.getPageCount());
        pagination.setCount(0);
        Assert.assertEquals(1, pagination.getPageCount());

    }
}