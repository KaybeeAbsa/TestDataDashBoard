package co.za.absa.TestDataDashBoard.Methods;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentDate {
    public int getTodayDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();
        return Integer.parseInt((dtf.format(now)));
    }
}
