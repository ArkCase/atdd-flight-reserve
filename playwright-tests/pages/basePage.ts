import { Page } from '@playwright/test';

/**
 * Base Page Object
 * Contains common functionality shared across all page objects
 */
export class BasePage {
    readonly page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    /**
     * Navigate to a specific path
     */
    async goto(path: string = '/') {
        await this.page.goto(path);
    }

    /**
     * Wait for page to be fully loaded
     */
    async waitForPageLoad() {
        await this.page.waitForLoadState('networkidle');
    }

    /**
     * Get page title
     */
    async getTitle(): Promise<string> {
        return await this.page.title();
    }

    /**
     * Wait for element to be visible
     */
    async waitForElement(selector: string, timeout: number = 5000) {
        await this.page.waitForSelector(selector, { state: 'visible', timeout });
    }

    /**
     * Take a screenshot
     */
    async screenshot(name: string) {
        await this.page.screenshot({ path: `screenshots/${name}.png`, fullPage: true });
    }
}
