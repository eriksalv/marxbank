package it1901;

public class DataHandler {
    
    private static DataHandler handler = null;
    
    private DataHandler() {}

    public static DataHandler getDataHandler() {
        if(handler == null) {
            synchronized (DataHandler.class) {
                if(handler == null) {
                    handler = new DataHandler();
                }
            }
        }
        return handler;
    }

}
