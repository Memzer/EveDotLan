package com.rr.eve.dotlan;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private WebDriver driver;

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {
        driver = new ChromeDriver();
        String userprofile = System.getenv("USERPROFILE");
        File logFolder = new File(userprofile+"/Documents/EVE/logs/Chatlogs");
        List<File> files = Arrays.asList(logFolder.listFiles());
        List<File> localFiles = files.stream().filter(f -> f.getName().startsWith("Local")).collect(Collectors.toList());
        Collections.sort(localFiles);
        Collections.reverse(localFiles);
        if(localFiles.size() > 0) {
            File latestLocal = localFiles.get(0);
            System.out.println("Latest: "+latestLocal);
            LogReader reader = new LogReader(this, latestLocal);
            reader.run();
        }
    }

    public void handleSystemChange(String systemName) throws IOException {
        System.out.println(systemName);
        driver.get("https://evemaps.dotlan.net/search?q="+systemName);
    }
}