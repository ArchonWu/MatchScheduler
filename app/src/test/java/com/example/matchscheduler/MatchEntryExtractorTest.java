package com.example.matchscheduler;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MatchEntryExtractorTest {
    MatchEntryExtractor matchEntryExtractor;
    String neededText;
    String searchedPlayer;

    @Before
    public void init() throws IOException {
        searchedPlayer = "Neeb";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("testParseOneMatchEntry.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        neededText = new String(buffer, StandardCharsets.UTF_8);
        matchEntryExtractor = new MatchEntryExtractor(searchedPlayer, neededText);
    }

    @Test
    public void testTrimText() {
        String test = matchEntryExtractor.getTrimmedInfoText();
        String expected = "";
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("testAfterTrim.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            expected = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException exception) {
        }
        assertEquals(expected, test);
    }

    @Test
    public void testFilterMatchDateTime() {
        String test = "";
        String expected = "September 7, 2021 - 14:00";
        try {
            test = matchEntryExtractor.filterMatchDateTime(neededText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(expected, test);
    }
}
