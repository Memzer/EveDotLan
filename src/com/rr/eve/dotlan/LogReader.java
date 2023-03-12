package com.rr.eve.dotlan;

import java.io.*;

public class LogReader extends Thread {

    private Main main;
    private File logFile;

    private String systemName;
    private boolean reachedEnd = false;

    public LogReader(Main main, File logFile) {
        this.main = main;
        this.logFile = logFile;
        readFullLog();
    }

    @Override
    public void run() {
        try(FileReader reader = new FileReader(logFile)) {
            BufferedReader br = new BufferedReader(reader);
            String line;
            while(true) {
                line = br.readLine();
                if(line == null) {
                    Thread.sleep(1000);
                    reachedEnd = true;
                } else {
                    StringBuffer b = new StringBuffer();
                    for(char c : line.toCharArray()) {
                        if((byte)c != 0) {
                            b.append(c);
                        }
                    }
                    String properLine = b.toString();
                    if(properLine.length() > 0) {
                        if(properLine.contains("EVE System > Channel changed to Local : ")) {
                            properLine = properLine.replaceAll(" ","");
                            systemName = properLine.substring(properLine.lastIndexOf(":")+1);
                            if(reachedEnd) {
                                main.handleSystemChange(systemName);
                            }
                        }
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void readFullLog() {
        try(FileReader reader = new FileReader(logFile)) {
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            while(line != null) {
                StringBuffer b = new StringBuffer();
                for(char c : line.toCharArray()) {
                    if((byte)c != 0) {
                        b.append(c);
                    }
                }
                String properLine = b.toString();
                if(properLine.length() > 0) {
                    if(properLine.contains("EVE System > Channel changed to Local : ")) {
                        systemName = properLine.substring(properLine.lastIndexOf(":")+2);
                    }
                }
                line = br.readLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        if(systemName != null) {
            try {
                main.handleSystemChange(systemName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
