package tests;

public class DesktopRunnerMain {
    public static void main(String[] args) {
        System.setProperty("mode", "desktop");
        System.setProperty("headless", "false");
        System.setProperty("channel", "chrome");
        System.setProperty("baseUrl", "https://www.twitch.tv");
        System.out.println("Desktop runner started. Now run TwitchTest via IntelliJ or wire JUnit launcher if added.");
    }
}
