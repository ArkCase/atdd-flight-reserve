import { Locator, Page } from "@playwright/test";
import { BasePage } from "./basePage";

export class CountryInformationPage extends BasePage {
    // Navigation
    readonly countryInformationTab: Locator;

    constructor(page: Page) {
            super(page);
    
            // Navigation
            this.countryInformationTab = page.getByText('Country Currency and TimeZone Information');
    };
}