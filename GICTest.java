import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

public class GICTest {

    @Test
    public void testValidDate() {
        String validDate = "2023-07-04";
        boolean result = GIC.isDateValid(validDate);
        Assert.assertFalse(result);
    }

    @Test
    public void testInvalidDate1() {
        String validDate = "2023-07";
        boolean result = GIC.isDateValid(validDate);
        Assert.assertTrue(result);
    }

    @Test
    public void testInvalidDate2() {
        String validDate = "2023-07-04-01";
        boolean result = GIC.isDateValid(validDate);
        Assert.assertTrue(result);
    }

    @Test
    public void testInvalidDate3() {
        String validDate = "";
        boolean result = GIC.isDateValid(validDate);
        Assert.assertTrue(result);
    }

    @Test
    public void testInvalidDate4() {
        String validDate = "asdfasdf";
        boolean result = GIC.isDateValid(validDate);
        Assert.assertTrue(result);
    }

    @Test
    public void testInvalidDate5() {
        String validDate = "2023-13-01";
        boolean result = GIC.isDateValid(validDate);
        Assert.assertTrue(result);
    }

    @Test
    public void testInvalidDate6() {
        String validDate = "2023-12-32";
        boolean result = GIC.isDateValid(validDate);
        Assert.assertTrue(result);
    }

    @Test
    public void testIsWeekendWeekday() {
        Calendar c = Calendar.getInstance(); //
        c.set(2023,Calendar.JULY, 4);
        Assert.assertFalse(GIC.isWeekend(c));
    }

    @Test
    public void testIsWeekendSaturday() {
        Calendar c = Calendar.getInstance(); //
        c.set(2023,Calendar.JULY, 1);
        Assert.assertTrue(GIC.isWeekend(c));
    }

    @Test
    public void testIsWeekendSunday() {
        Calendar c = Calendar.getInstance(); //
        c.set(2023,Calendar.JULY, 2);
        Assert.assertTrue(GIC.isWeekend(c));
    }

}