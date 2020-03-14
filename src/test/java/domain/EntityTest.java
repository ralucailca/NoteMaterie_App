package domain;

import junit.framework.TestCase;

public class EntityTest extends TestCase {
    private Entity<Integer> el=new Entity<>();
    private Entity<String> es=new Entity<>();

    public void testSetId() {
        el.setId(5);
        assertEquals(el.getId(),(Integer)5);
    }

    public void testGetId() {
        es.setId("12");
        assertEquals(es.getId(),"12");
    }
}