package ru.meridor.steve.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.util.Arrays;

@RunWith(Parameterized.class)
public class CronParserImplIncorrectValuesTest {

    private final String cronString;

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
                //Completely invalid values
                {null},
                {"invalid"},
                {"* * * *"},
                {"invalid * * * *"},
                {"invalid * * * * *"},
                {"* invalid * * * *"},
                {"* * invalid * * *"},
                {"* * * invalid * *"},
                {"* * * * invalid *"},
                {"* * * * * invalid"},

                //Invalid dates
                {"-1 * * * * *"},
                {"70 * * * * *"},
                {"10-2 * * * * *"},
                {"10-2-4 * * * * *"},
                {"25,71 * * * * *"},
                {"-1/30 * * * * *"},
                {"*/0 * * * * *"},
                {"*//2 * * * * *"},
                
                {"* -1 * * * *"},
                {"* 70 * * * *"},
                {"* 10-2 * * * *"},
                {"* 10-2-4 * * * *"},
                {"* 25,71 * * * *"},
                {"* -1/30 * * * *"},
                {"* */0 * * * *"},
                {"* *//2 * * * *"},
                
                {"* * -1 * * *"},
                {"* * 24 * * *"},
                {"* * 22-1 * * *"},
                {"* * 22-1-31 * * *"},
                {"* * 1,25 * * *"},
                {"* * -1/10 * * *"},
                {"* * */0 * * *"},
                {"* * *//10 * * *"},
                
                {"* * * -1 * *"},
                {"* * * 32 * *"},
                {"* * * 15-4 * *"},
                {"* * * 15-2-34 * *"},
                {"* * * 0,32 * *"},
                {"* * * -1/15 * *"},
                {"* * * */0 * *"},
                {"* * * *//5 * *"},
                
                {"* * * * 0 *"},
                {"* * * * 13 *"},
                {"* * * * 11-5 *"},
                {"* * * * 0-12 *"},
                {"* * * * 1-13 *"},
                {"* * * * 1-13-1 *"},
                {"* * * * 2,3,13 *"},
                {"* * * * -1/4 *"},
                {"* * * * */0 *"},
                {"* * * * *//3 *"},
        });
    }

    public CronParserImplIncorrectValuesTest(String cronString) {
        this.cronString = cronString;
    }

    @Test(expected = ParseException.class)
    public void testParseExceptionThrown() throws ParseException {
        new CronParserImpl().parse(cronString);
    }

}
