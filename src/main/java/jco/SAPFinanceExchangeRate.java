package jco;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import jco.model.GetExchangeRate;
import org.json.XML;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

// Reference xml file
// https://github.com/Deloitte/mule-sap-finance-invoice-sapi/blob/develop/src/main/mule/postInvoicesImpl.xml
public class SAPFinanceExchangeRate {
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
            String DESTINATION_NAME1 = "BAPI_EXCHANGERATE_GETDETAIL";
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

            JCoDestination destination = JCoDestinationManager.getDestination("BAPI_EXCHANGERATE_GETDETAIL");
            destination.ping();
            JCoFunction function = destination.getRepository().getFunction("BAPI_EXCHANGERATE_GETDETAIL");
            JCoParameterList input = function.getImportParameterList();
            System.out.println(input.getListMetaData());
            input.setValue("DATE","20010101");
            input.setValue("FROM_CURR","USD");
            input.setValue("RATE_TYPE","M");
            input.setValue("TO_CURRNCY","INR");
            System.out.println(input.getListMetaData());

            function.execute(destination);

            String output = function.getExportParameterList().toXML();
//            System.out.println(function.getException("error_writing_idoc_status"));
            GetExchangeRate response = (GetExchangeRate) JsonUtil.jsonMapper(XML.toJSONObject(output).toString(), new TypeReference<GetExchangeRate>() {
            });
            System.out.println(response.oUTPUT);
//            String result = output.getString("EXCH_RATE");
//            ExchangeRate result = (ExchangeRate) output.getValue("EXCH_RATE");
//
//            System.out.println("output = " + result);
//            JCoTable table = function.getTableParameterList().getTable("EXCH_RATE");
//            for (int i = 0; i < table.getNumRows(); i++) {
//                table.setRow(i);
//                String field1 = table.getString("RATE");
////                String field2 = table.getString("VALID_FROM");
//                System.out.println(field1);
//                // process the results of the table row
//            }

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
