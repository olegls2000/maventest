
import static com.bta.MyUtils.getSumm;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MyTest {

    @Test
    public void myTestForSumm() {
        //given
        int firstNum = 1;
        int secondNum = 1;

        //when
        int result = getSumm(firstNum, secondNum);

        //then
        assertEquals(2, result);
    }

    @Test
    public void myTestForSummWithNulls() {
        int result = getSumm(0, 0);
        assertEquals(0, result);
    }

    @Test
    public void myTestForSummWithNegative() {
        int result = getSumm(-1, -1);
        assertEquals(-2, result);
    }
}
