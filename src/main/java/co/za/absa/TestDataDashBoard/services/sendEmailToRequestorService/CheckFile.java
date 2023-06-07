package co.za.absa.TestDataDashBoard.services.sendEmailToRequestorService;

import java.io.File;

public class CheckFile {
    public boolean checkIfFileExist(String file) {
        File file1 = new File(file);
        if(file1.exists())
        {
            return true;
        }else
        {
            return false;
        }
    }
}
