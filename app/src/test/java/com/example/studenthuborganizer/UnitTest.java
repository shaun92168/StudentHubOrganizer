package com.example.studenthuborganizer;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.studenthuborganizer.Models.SHUBORecord;

public class UnitTest {
    private SHUBORecord testRecord = new SHUBORecord(1, "Assignment 1",
            "Assignment 1 description", "COMP7901", false);
    @Test
    public void SHUBORecordTitle() {
        assertEquals("Assignment 1", testRecord.GetTitle());
    }

    @Test
    public void SHUBORecordDescription() {
        assertEquals("Assignment 1 description", testRecord.GetDescription());
    }

    @Test
    public void SHUBORecordCourse() {
        assertEquals("COMP7901", testRecord.GetCourseName());
    }
}
