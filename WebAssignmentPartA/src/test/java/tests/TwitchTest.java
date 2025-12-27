package tests;

import framework.BaseTest;
import framework.Config;
import org.junit.jupiter.api.Test;
import pages.TwitchHomePage;
import pages.TwitchSearchResultsPage;

import java.nio.file.Paths;

public class TwitchTest extends BaseTest {

    @Test
    void twitchMobileSearchOpenStreamerAndScreenshot() {
        TwitchHomePage home = new TwitchHomePage(page);
        home.open();
        home.searchFor("StarCraft II");

        TwitchSearchResultsPage results = new TwitchSearchResultsPage(page);
        results.waitForResults();
        results.scrollDownTwice();
        results.openFirstStreamer();

        // Screenshot after opening streamer page
        page.waitForTimeout(2000);
        page.screenshot(new com.microsoft.playwright.Page.ScreenshotOptions()
                .setPath(Paths.get(Config.ARTIFACT_DIR, "streamer-page.png"))
                .setFullPage(true));
    }
}
