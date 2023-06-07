package co.za.absa.TestDataDashBoard.commonMethods;

import co.za.absa.TestDataDashBoard.Methods.ClientCreationJagacy;
import co.za.absa.TestDataDashBoard.model.Users;
import com.jagacy.util.JagacyException;

import java.awt.*;
import java.util.ArrayList;

public class MainFrameCommonMethods {
    private ClientCreationJagacy jagacyMethods = null;
    private String jagacyMessage;

    ArrayList<Users> usersList = null;

    private boolean addFicLock(String clientCode,String reEnviroment, ArrayList<Users> usersList) throws InterruptedException, JagacyException, AWTException {

        boolean approved = false;
        String password,username;

       // usersList = users.findUsers();

        for (int i = 0; i < usersList.size(); i++) {

            if (usersList.get(i).getUsername().equalsIgnoreCase("abks580") || usersList.get(i).getUsername().equalsIgnoreCase("ab0158h")) {
                password = usersList.get(i).getPassword();
                username = usersList.get(i).getUsername();

            } else {
                password = "DD";
                username = "DD";
                continue;
            }

            jagacyMethods = new ClientCreationJagacy();
            jagacyMethods.open();

            //Login on Jagacy(3270) Mainframe
            jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

            System.out.println("Client Code: "+ clientCode);
            jagacyMessage = jagacyMethods.addFicHold("FICN", clientCode);

            if (jagacyMessage.equalsIgnoreCase("RISK LOCK ALREADY PLACED") || jagacyMessage.equalsIgnoreCase("< Data entry too short")) {

                jagacyMethods.close();
                approved = false;
                continue;
            }else {
                jagacyMethods.close();
                approved = true;
                jagacyMethods.close();
                break;
            }

        }
        return approved;
    }

    private boolean addCifHold(String clientCode,String reEnviroment, String holdType, ArrayList<Users> usersList) throws InterruptedException, JagacyException, AWTException {

        boolean approved = false;
        String password,username;

       // ArrayList<Users> usersList = users.findUsers();

        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUsername().equalsIgnoreCase("abks580") || usersList.get(i).getUsername().equalsIgnoreCase("ab0158h")) {
                password = usersList.get(i).getPassword();
                username = usersList.get(i).getUsername();

            } else {
                password = "DD";
                username = "DD";
                continue;
            }
            jagacyMethods = new ClientCreationJagacy();
            jagacyMethods.open();

            //Login on Jagacy(3270) Mainframe
            jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

            jagacyMethods.addCIFHold("CLF", clientCode,holdType);

            jagacyMethods.close();
            approved = true;
            jagacyMethods.close();
            break;
        }
        return approved;
    }
}
