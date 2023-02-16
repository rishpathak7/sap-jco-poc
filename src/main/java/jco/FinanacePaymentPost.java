package jco;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.json.XML;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class FinanacePaymentPost {
    static String   IP="10.241.163.214", //IP or HOST
            USER="khsarkar", // user name of SAP
            PASSWORD=")js5Rm8M#nhy)U1P!*<r", // password of SAP
            CLIENT="200", //mandant in sap
            SYSNR="01", // instance number
            LANG="en", // language (es or en)
            AUTH_TYPE="Basic"; //auth type

    public static void main(String[] args) {
        System.out.println("SAP Test is running");

        try {
            // This will create a file called mySAPSystem.jcoDestination
            String DESTINATION_NAME1 = "Z_POST_INVOICE_PAYMENT_FULL";
            Properties connectProperties = new Properties();
            connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,   IP);
            connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,    SYSNR);
            connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,   CLIENT);
            connectProperties.setProperty(DestinationDataProvider.JCO_USER,     USER);
            connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,   PASSWORD);
            connectProperties.setProperty(DestinationDataProvider.JCO_LANG,     LANG);
            connectProperties.setProperty(DestinationDataProvider.JCO_AUTH_TYPE, AUTH_TYPE);
            connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
            connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,    "10");

            createDestinationDataFile(DESTINATION_NAME1,connectProperties);

            JCoDestination destination = JCoDestinationManager.getDestination("Z_POST_INVOICE_PAYMENT_FULL");

            JCoFunction function = destination.getRepository().getFunction("Z_POST_INVOICE_PAYMENT_FULL");


            JCoParameterList importParameterList = function.getImportParameterList();
            importParameterList.setValue("I_AUGLV","EINGZAHL");
            importParameterList.setValue("I_TCODE","FB06");
            importParameterList.setValue("I_SGFUNCT","C");

            JCoTable tFtclear = function.getTableParameterList().getTable("T_FTCLEAR");
            tFtclear.appendRow();
            tFtclear.setValue("AGKOA","");
            tFtclear.setValue("AGKON","");
            tFtclear.setValue("AGBUK","US01");
            tFtclear.setValue("XNOPS","X");
            tFtclear.setValue("XFIFO","");
            tFtclear.setValue("AGUMS","");
            tFtclear.setValue("AVSID","");
            tFtclear.setValue("SELFD","BELNR");
            tFtclear.setValue("SELVON","0000000001");
            tFtclear.setValue("SELBIS","");

            JCoTable tFtpost = function.getTableParameterList().getTable("T_FTPOST");
            tFtpost.appendRow();
            tFtpost.setValue("STYPE","K");
            tFtpost.setValue("COUNT","001");
            tFtpost.setValue("FNAM","BKPF-BUKRS");
            tFtpost.setValue("FVAL","US01");
            tFtpost.appendRow();
            tFtpost.setValue("STYPE","K");
            tFtpost.setValue("COUNT","001");
            tFtpost.setValue("FNAM","BKPF-BLART");
            tFtpost.setValue("FVAL","AB");
            tFtpost.appendRow();
            tFtpost.setValue("STYPE","K");
            tFtpost.setValue("COUNT","001");
            tFtpost.setValue("FNAM","BKPF-WAERS");
            tFtpost.setValue("FVAL","USD");
            tFtpost.appendRow();
            tFtpost.setValue("STYPE","K");
            tFtpost.setValue("COUNT","001");
            tFtpost.setValue("FNAM","BKPF-BLDAT");
            tFtpost.setValue("FVAL","03/16/2020");
            tFtpost.appendRow();
            tFtpost.setValue("STYPE","K");
            tFtpost.setValue("COUNT","001");
            tFtpost.setValue("FNAM","BKPF-BUDAT");
            tFtpost.setValue("FVAL","03/16/2020");
            tFtpost.appendRow();
            tFtpost.setValue("STYPE","K");
            tFtpost.setValue("COUNT","001");
            tFtpost.setValue("FNAM","BSEG-HKONT");
            tFtpost.setValue("FVAL","113000");
            tFtpost.appendRow();
            tFtpost.setValue("STYPE","K");
            tFtpost.setValue("COUNT","001");
            tFtpost.setValue("FNAM","RF05A-NEWBS");
            tFtpost.setValue("FVAL","40");
            tFtpost.appendRow();
            tFtpost.setValue("STYPE","K");
            tFtpost.setValue("COUNT","001");
            tFtpost.setValue("FNAM","BSEG-WRBTR");
            tFtpost.setValue("FVAL","1000.00");

            function.execute(destination);

            String output = function.getExportParameterList().toXML();
            System.out.println(XML.toJSONObject(output));

        } catch(Exception ex) {
            System.out.println("exception "+ex.toString());
        }
    }

    private static void createDestinationDataFile(String destinationName, Properties connectProperties) {
        File destCfg = new File(destinationName+".jcoDestination");
        try {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "for tests only !");
            fos.close();
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to create the destination files", e);
        }
    }
}
