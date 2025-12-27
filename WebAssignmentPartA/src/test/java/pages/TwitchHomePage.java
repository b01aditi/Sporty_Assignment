package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import framework.Config;
import framework.PopupHandlers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class TwitchHomePage {
    private final Page page;

    public TwitchHomePage(Page page) {
        this.page = page;
    }

    public void open() {
        page.navigate(Config.BASE_URL);

        try { page.waitForLoadState(); } catch (Exception ignored) {}
        page.waitForTimeout(1500);

        PopupHandlers.dismissBlockingPopups(page);
        page.waitForSelector("body");
    }

    public void searchFor(String query) {
        PopupHandlers.dismissBlockingPopups(page);

        try {
            var searchBox = page.getByRole(AriaRole.SEARCHBOX);
            if (searchBox.count() > 0 && isVisibleSafe(searchBox.first())) {
                searchBox.first().click();
                searchBox.first().fill(query);
                page.keyboard().press("Enter");
                return;
            }
        } catch (Exception ignored) {}

        try {
            openSearchUi();

            var input = page.locator(
                    "input[type='search'], input[placeholder*='Search'], input[aria-label*='Search'], [role='searchbox']"
            );

            input.first().waitFor();
            input.first().click();
            input.first().fill(query);
            page.keyboard().press("Enter");
            return;

        } catch (Exception ignored) {
            debugScreenshot("debug-home-search-missing.png");
        }

        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        page.navigate("https://www.twitch.tv/search?term=" + encoded);

        try { page.waitForLoadState(); } catch (Exception ignored) {}
        page.waitForTimeout(1200);
        PopupHandlers.dismissBlockingPopups(page);
    }



    private void openSearchUi() {
        PopupHandlers.dismissBlockingPopups(page);

        clickIfVisible(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")));
        clickIfVisible(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Search")));

        clickIfVisible(page.locator("button[aria-label*='Search']"));
        clickIfVisible(page.locator("a[aria-label*='Search']"));
        clickIfVisible(page.locator("[data-a-target*='search']"));

        try { page.keyboard().press("/"); } catch (Exception ignored) {}

        page.waitForTimeout(800);
        PopupHandlers.dismissBlockingPopups(page);
    }

    private void clickIfVisible(com.microsoft.playwright.Locator loc) {
        try {
            if (loc != null && loc.count() > 0 && isVisibleSafe(loc.first())) {
                loc.first().click();
                page.waitForTimeout(500);
            }
        } catch (Exception ignored) {}
    }

    private boolean isVisibleSafe(com.microsoft.playwright.Locator loc) {
        try { return loc.isVisible(); } catch (Exception e) { return false; }
    }

    private void debugScreenshot(String fileName) {
        try {
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(Config.ARTIFACT_DIR, fileName))
                    .setFullPage(true));
        } catch (Exception ignored) {}
    }
}
