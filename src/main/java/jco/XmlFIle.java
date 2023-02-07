//package jco;
//
//public class XmlFIle {
//    try
//    {
////                JCoRepository repository = destination.getRepository();
////                System.out.println(repository.);
////                String tid=destination.createTID();
////                IDocFactory iDocFactory=JCoIDoc.getIDocFactory();
////
////                // a) create new IDoc
////                IDocDocument doc=iDocFactory.createIDocDocument(iDocRepository, "SYIDOC01");
////                IDocSegment segment=doc.getRootSegment();
////                segment=segment.addChild("E1IDOCH");
////                // and so on. See IDoc Specification .....
////                JCoIDoc.send(doc, IDocFactory.IDOC_VERSION_DEFAULT, destination, tid);
////
////                // b) use existent xml file
////                String iDocXML=getXMLFromFile("syidoc01.xml");
////
////                IDocXMLProcessor processor=iDocFactory.getIDocXMLProcessor();
////                IDocDocumentList iDocList=processor.parse(iDocRepository, iDocXML);
////                JCoIDoc.send(iDocList, IDocFactory.IDOC_VERSION_DEFAULT, destination, tid);
////                destination.confirmTID(tid);
//    }
//            catch (Exception e)
//    {
//        e.printStackTrace();
//    }
//}
