package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import framework.PopupHandlers;

import java.nio.file.Paths;

public class TwitchStreamerPage {
    private final Page page;

    public TwitchStreamerPage(Page page) {
        this.page = page;
    }

    public void waitUntilLoaded() {
        PopupHandlers.dismissBlockingPopups(page);

        Locator playerOrVideo = page.locator("[data-a-target='player-overlay-click-handler'], video").first();
        Locator channelHeader = page.locator("[data-a-target='channel-header'], header").first();

        try {
            page.waitForCondition(() ->
                            playerOrVideo.isVisible() || channelHeader.isVisible(),
                    new Page.WaitForConditionOptions().setTimeout(20000)
            );
        } catch (Exception ignored) {}

        PopupHandlers.dismissBlockingPopups(page);
        try { page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE); }
        catch (Exception ignored) {}
    }

    public void takeScreenshot(String fileName) {
        PopupHandlers.dismissBlockingPopups(page);
        page.screenshot(new Page.ScreenshotOptions()
                .setFullPage(true)
                .setPath(Paths.get("artifacts", fileName)));
    }
}
