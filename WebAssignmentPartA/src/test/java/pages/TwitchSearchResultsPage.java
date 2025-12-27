package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import framework.PopupHandlers;
import framework.ScrollUtil;

public class TwitchSearchResultsPage {
    private final Page page;

    public TwitchSearchResultsPage(Page page) {
        this.page = page;
    }

    public void waitForResults() {
        PopupHandlers.dismissBlockingPopups(page);

        page.waitForURL(url -> url.contains("/search"));

        try { page.waitForLoadState(); } catch (Exception ignored) {}

        page.waitForTimeout(1200);

        PopupHandlers.dismissBlockingPopups(page);
        page.waitForSelector("main");
        page.waitForSelector("main a");
    }

    public void scrollDownTwice() {
        PopupHandlers.dismissBlockingPopups(page);
        ScrollUtil.scrollDownNTimes(page, 2);
        PopupHandlers.dismissBlockingPopups(page);
    }

    public void openFirstStreamer() {
        PopupHandlers.dismissBlockingPopups(page);
        clickTabIfPresent("Channels");
        clickTabIfPresent("Live channels");
        clickTabIfPresent("Live Channels");
        clickTabIfPresent("Top");

        PopupHandlers.dismissBlockingPopups(page);
        page.waitForTimeout(1200);
        Locator candidate = channelCardLinks();
        if (existsWithin(candidate, 10000)) {
            clickFirstVisible(candidate);
            waitForLeaveSearch();
            return;
        }
        ScrollUtil.scrollDownNTimes(page, 1);
        PopupHandlers.dismissBlockingPopups(page);
        page.waitForTimeout(800);

        candidate = channelCardLinks();
        if (existsWithin(candidate, 10000)) {
            clickFirstVisible(candidate);
            waitForLeaveSearch();
            return;
        }
        Locator fallback = fallbackChannelLinks();
        if (existsWithin(fallback, 10000)) {
            clickFirstVisible(fallback);
            waitForLeaveSearch();
            return;
        }
        try {
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(java.nio.file.Paths.get("artifacts", "debug-search.png"))
                    .setFullPage(true));
        } catch (Exception ignored) {}

        throw new RuntimeException("No clickable streamer/channel link found on search results page. " +
                "Saved artifacts/debug-search.png for inspection.");
    }

    private void clickTabIfPresent(String text) {
        try {
            Locator aTab = page.locator("a:has-text(\"" + text + "\")");
            if (aTab.count() > 0 && aTab.first().isVisible()) {
                aTab.first().click();   // no ClickOptions (compat)
                page.waitForTimeout(800);
                return;
            }

            Locator btnTab = page.locator("button:has-text(\"" + text + "\")");
            if (btnTab.count() > 0 && btnTab.first().isVisible()) {
                btnTab.first().click(); // no ClickOptions (compat)
                page.waitForTimeout(800);
            }
        } catch (Exception ignored) {}
    }

    private Locator channelCardLinks() {
        return page.locator("main a[href^='/']")
                .filter(new Locator.FilterOptions().setHas(page.locator("img")))
                .filter(new Locator.FilterOptions().setHasNot(page.locator(
                        "a[href^='/directory'], " +
                                "a[href^='/search'], " +
                                "a[href^='/videos'], " +
                                "a[href^='/settings'], " +
                                "a[href^='/downloads'], " +
                                "a[href^='/subscriptions'], " +
                                "a[href^='/turbo'], " +
                                "a[href^='/wallet']"
                )));
    }

    private Locator fallbackChannelLinks() {
        return page.locator("main a[href^='/']")
                .filter(new Locator.FilterOptions().setHasNot(page.locator(
                        "a[href^='/directory'], " +
                                "a[href^='/search'], " +
                                "a[href^='/videos'], " +
                                "a[href^='/settings'], " +
                                "a[href^='/downloads'], " +
                                "a[href^='/subscriptions'], " +
                                "a[href^='/turbo'], " +
                                "a[href^='/wallet']"
                )));
    }

    private boolean existsWithin(Locator locator, int timeoutMs) {
        try {
            locator.first().waitFor(new Locator.WaitForOptions().setTimeout(timeoutMs));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void clickFirstVisible(Locator locator) {
        int n;
        try { n = locator.count(); } catch (Exception e) { n = 0; }
        if (n == 0) n = 10;

        for (int i = 0; i < n; i++) {
            try {
                Locator el = locator.nth(i);
                if (el.count() == 0) continue;
                if (!el.isVisible()) continue;

                try { el.scrollIntoViewIfNeeded(); } catch (Exception ignored) {}
                PopupHandlers.dismissBlockingPopups(page);

                el.click();
                return;
            } catch (Exception ignored) {}
        }

        locator.first().click();
    }

    private void waitForLeaveSearch() {
        PopupHandlers.dismissBlockingPopups(page);
        page.waitForURL(url -> !url.contains("/search"));
        PopupHandlers.dismissBlockingPopups(page);
    }
}
