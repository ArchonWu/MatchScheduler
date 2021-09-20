package com.example.matchscheduler;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MatchEntryExtractorTest {
    MatchEntryExtractor matchEntryExtractor1, matchEntryExtractor2;
    String neededText1, neededText2;
    String searchedPlayer1, searchedPlayer2;

    @Before
    public void init() throws IOException {
        searchedPlayer1 = "Neeb";
        InputStream is1 = this.getClass().getClassLoader().getResourceAsStream("testParseOneMatchEntry.json");
        int size1 = is1.available();
        byte[] buffer1 = new byte[size1];
        is1.read(buffer1);
        is1.close();
        neededText1 = new String(buffer1, StandardCharsets.UTF_8);
        matchEntryExtractor1 = new MatchEntryExtractor(searchedPlayer1, neededText1);

        searchedPlayer2 = "Dream (Korean Terran player)";
        InputStream is2 = this.getClass().getClassLoader().getResourceAsStream("testParseTwoMatchEntry.json");
        int size2 = is2.available();
        byte[] buffer2 = new byte[size2];
        is2.read(buffer2);
        is2.close();
        neededText2 = new String(buffer2, StandardCharsets.UTF_8);
        matchEntryExtractor2 = new MatchEntryExtractor(searchedPlayer2, neededText2);
    }

    @Test
    public void testTrimText() {
        String test = "";
        String expected = "";
        try {
            test = matchEntryExtractor1.getTrimmedUpcomingText();
            expected = "";
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("testAfterTrim.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            expected = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException | ProcessingDataException exception) {
        }
        assertEquals(expected, test);
    }

    @Test
    public void testFilterMatchDateTime() {
        String test = "";
        String expected = "September 7, 2021 - 14:00";
        try {
            test = matchEntryExtractor1.filterMatchDateTime(neededText1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(expected, test);
    }

    @Test
    public void testGetTotalUpcomingMatches() {
        int test = matchEntryExtractor1.getTotalUpcomingMatches();
        int expected = 1;
        assertEquals(expected, test);

        int test2 = matchEntryExtractor2.getTotalUpcomingMatches();
        int expected2 = 2;
        assertEquals(expected2, test2);
    }

    @Test
    public void testGetDividedUpcomingMatches() {
        int test1 = matchEntryExtractor1.getDividedUpcomingMatches().length;
        int expected1 = 1;
        assertEquals(expected1, test1);

        int test2 = matchEntryExtractor2.getDividedUpcomingMatches().length;
        int expected2 = 2;
        assertEquals(expected2, test2);
    }

    @Test
    public void testGetOpponentNames() {
        String testName1 = matchEntryExtractor2.getOpponentNames()[0];
        String expected1 = "ByuL";
        String testName2 = matchEntryExtractor2.getOpponentNames()[1];
        String expected2 = "Zest";
        assertEquals(expected1, testName1);
        assertEquals(expected2, testName2);
    }

    @Test
    public void testGetTournamentNames() {
        String testName1 = matchEntryExtractor2.getTournamentNames()[0];
        String expected1 = "ITaX Super Series #63";
        String testName2 = matchEntryExtractor2.getTournamentNames()[1];
        String expected2 = "2021 GSL S3: Code S";
        assertEquals(expected1, testName1);
        assertEquals(expected2, testName2);
    }
}
