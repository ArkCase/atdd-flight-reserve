import { Locator, Page } from "@playwright/test";
import { BasePage } from "./basePage";

export class TaxInformationPage extends BasePage {
    // Navigation
    readonly taxInformationTab: Locator;

    constructor(page: Page) {
            super(page);
    
            // Navigation
            this.taxInformationTab = page.getByText('Tax Information');
    };
};