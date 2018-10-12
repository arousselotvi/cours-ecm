package fr.cmm.helper;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void getPages(){
        Pagination pagination=new Pagination();
        pagination.setCount(250);
        pagination.setPageSize(50);
        List<Integer> myTestList= Arrays.asList(1,2,3,4,5);
        Assert.assertEquals(myTestList,pagination.getPages());
    }
}