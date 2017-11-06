package RedHatDev;

import RedHatDev.controllers.RequestHandler;

public class App {

    public static void main(String[] args) {
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.run();
    }
}
