package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class SavingsCalcTest {

    @TempDir
    static Path tempDir;

    @BeforeAll
    public static void init() throws IOException {
        Files.createDirectory(tempDir.resolve("data"));
    }

    
    @Test
    @DisplayName("test savings calculator with no monthly amount")
    public void testSavingsCalculator1() {
        long c1 = SavingsCalc.calculation(0, 1000, 1, 10.0);
        assertEquals(c1, 1100);

        long c2 = SavingsCalc.calculation(0, 1000, 5, 10.0);
        assertEquals(c2, 1610);

        long c3 = SavingsCalc.calculation(0, 123, 8, 7.5);
        assertEquals(c3, 219);
    }


    @Test
    @DisplayName("test savings calculator with no lump amount")
    public void testSavingsCalculator2() {
        long c1 = SavingsCalc.calculation(100, 0, 1, 10.0);
        assertEquals(c1, 1265);

        long c2 = SavingsCalc.calculation(2500, 0, 4, 5.0);
        assertEquals(c2, 132805);

        long c3 = SavingsCalc.calculation(700, 0, 8, 6.6);
        assertEquals(c3, 87987);
    }

    @Test
    @DisplayName("test savings calculator with lump amount and monthly amount")
    public void testSavingsCalculator3() {
        long c1 = SavingsCalc.calculation(100, 1000, 1, 2.0);
        assertEquals(c1, 2233);

        long c2 = SavingsCalc.calculation(1000, 100, 9, 5.5);
        assertEquals(c2, 139261);
    }

    @Test
    @DisplayName("test savings calculator with no lump amount and no monthly amount")
    public void testSavingsCalculator4() {
        long c1 = SavingsCalc.calculation(0, 0, 25, 10.0);
        assertEquals(c1, 0);
    }

    @Test
    @DisplayName("test savings calculator with period equal to 0")
    public void testSavingsCalculator5() {
        long c1 = SavingsCalc.calculation(100, 1000, 0, 2.0);
        assertEquals(c1, 2233);
    } 


    @Test
    @DisplayName("test savings calculator with output out of range")
    public void testSavingsCalculator6() {
        long c1 = SavingsCalc.calculation(10000000, 1000000000, 30000000, 152.0);
        assertEquals(c1, -1);
    } 
}
