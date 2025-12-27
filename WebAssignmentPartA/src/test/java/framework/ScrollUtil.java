package framework;

import com.microsoft.playwright.Page;

public class ScrollUtil {
    public static void scrollDownNTimes(Page page, int times) {
        for (int i = 0; i < times; i++) {
            page.mouse().wheel(0, 1200);
            page.waitForTimeout(700);
        }
    }
}
