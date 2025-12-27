package framework;

import com.microsoft.playwright.Page;

public class PopupHandlers {

    public static void handleCommonTwitchPopups(Page page) {
        dismissBlockingPopups(page);
    }

    public static void dismissBlockingPopups(Page page) {
        try { page.keyboard().press("Escape"); } catch (Exception ignored) {}

        clickIfVisible(page, "button:has-text(\"Accept\")");
        clickIfVisible(page, "button:has-text(\"I Accept\")");
        clickIfVisible(page, "button:has-text(\"Continue\")");
        clickIfVisible(page, "button:has-text(\"Start Watching\")");
        clickIfVisible(page, "button:has-text(\"Got it\")");
        clickIfVisible(page, "button:has-text(\"OK\")");

        clickIfVisible(page, "[data-a-target='modal-close-button']");
        clickIfVisible(page, "button[aria-label*='Close']");
        clickIfVisible(page, "button[aria-label*='close']");
    }

    private static void clickIfVisible(Page page, String selector) {
        try {
            var loc = page.locator(selector);
            if (loc.count() > 0 && loc.first().isVisible()) {
                loc.first().click();
            }
        } catch (Exception ignored) {}
    }
}
