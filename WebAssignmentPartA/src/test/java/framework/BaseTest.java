package framework;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import framework.ArtifactUtils;

import org.junit.jupiter.api.*;

import java.io.File;

public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;

    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void beforeAll() {
        ArtifactUtils.cleanArtifactsDir();
        playwright = Playwright.create();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(Config.HEADLESS)
                .setChannel(Config.BROWSER_CHANNEL);

        browser = playwright.chromium().launch(launchOptions);

        new File(Config.ARTIFACT_DIR).mkdirs();
    }

    @AfterAll
    static void afterAll() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeEach
    void beforeEach() {
        Browser.NewContextOptions options = new Browser.NewContextOptions()
                .setLocale("en-US");

        if ("mobile".equalsIgnoreCase(Config.MODE)) {
            //MOBILE EMULATION
            options
                    .setViewportSize(Config.MOBILE_VIEWPORT_WIDTH, Config.MOBILE_VIEWPORT_HEIGHT)
                    .setDeviceScaleFactor(Config.DEVICE_SCALE_FACTOR)
                    .setIsMobile(true)
                    .setHasTouch(true)
                    .setUserAgent(Config.MOBILE_USER_AGENT);
        } else {
            //DESKTOP
            options.setViewportSize(Config.DESKTOP_VIEWPORT_WIDTH, Config.DESKTOP_VIEWPORT_HEIGHT);
        }

        context = browser.newContext(options);
        page = context.newPage();

        page.setDefaultTimeout(30000);
        page.setDefaultNavigationTimeout(60000);
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        if (context != null) context.close();
    }
}
