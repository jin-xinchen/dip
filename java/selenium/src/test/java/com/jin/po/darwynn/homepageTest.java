package com.jin.po.darwynn;

import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;

public class homepageTest extends TestCase {

    public void testSearch() {
        Homepage hp = new Homepage();
        try {
            hp.setDriver(0);
            hp.search("on sale");
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

}