package framework;

public class Config {
    public static final boolean HEADLESS = Boolean.parseBoolean(System.getProperty("headless", "false"));
    public static final String BROWSER_CHANNEL = System.getProperty("channel", "chrome");
    public static final String ARTIFACT_DIR = "artifacts";

    // desktop | mobile
    public static final String MODE = System.getProperty("mode", "mobile");

    // Desktop viewport
    public static final int DESKTOP_VIEWPORT_WIDTH = 1280;
    public static final int DESKTOP_VIEWPORT_HEIGHT = 720;

    // Mobile viewport
    public static final int MOBILE_VIEWPORT_WIDTH = 393;
    public static final int MOBILE_VIEWPORT_HEIGHT = 851;

    // Mobile emulation extras
    public static final int DEVICE_SCALE_FACTOR = Integer.parseInt(System.getProperty("dpr", "3"));
    public static final String MOBILE_USER_AGENT = System.getProperty("ua",
            "Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/120.0.0.0 Mobile Safari/537.36");

    // Base URL
    public static final String BASE_URL = System.getProperty("baseUrl", "https://www.twitch.tv");
}

