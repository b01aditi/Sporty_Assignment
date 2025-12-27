package tests;

public class MobileRunnerMain {
    public static void main(String[] args) {
        System.setProperty("mode", "mobile");
        System.setProperty("headless", "false");
        System.setProperty("channel", "chrome");
        System.setProperty("baseUrl", "https://www.twitch.tv");

        System.setProperty("dpr", "3");
        System.setProperty("ua",
                "Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/120.0.0.0 Mobile Safari/537.36");

        System.out.println("Mobile runner started. Now run TwitchTest via IntelliJ or wire JUnit launcher if added.");
    }
}
