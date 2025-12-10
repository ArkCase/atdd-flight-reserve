import { Page, Locator, expect } from '@playwright/test';
import { BasePage } from './basePage';

/**
 * City Management Page Object
 * Represents the City Management UI and its interactions
 */
export class CityManagementPage extends BasePage {
    // Navigation
    readonly manageCitiesTab: Locator;

    // Add New City Section
    readonly cityNameInput: Locator;
    readonly countryInput: Locator;
    readonly descriptionInput: Locator;
    readonly addCityButton: Locator;
    readonly clearButton: Locator;

    // Search Cities Section
    readonly searchCountryInput: Locator;
    readonly searchCityInput: Locator;
    readonly searchButton: Locator;
    readonly clearSearchButton: Locator;

    // City List Table
    readonly cityTable: Locator;

    constructor(page: Page) {
        super(page);

        // Navigation
        this.manageCitiesTab = page.getByText('Manage Cities');

        // Add New City Section
        // Using explicit selectors based on the HTML structure and classes
        const addSection = page.locator('.add-city-section');
        this.cityNameInput = addSection.locator('input[placeholder="Enter city name"]');
        this.countryInput = addSection.locator('input[placeholder="Type to search countries..."]');
        this.descriptionInput = addSection.locator('input[placeholder="Enter description"]');
        this.addCityButton = addSection.locator('button', { hasText: 'Add City' });
        this.clearButton = addSection.locator('button', { hasText: 'Clear' });

        // Search Cities Section
        const searchSection = page.locator('.search-cities-section');
        this.searchCountryInput = searchSection.locator('input[placeholder="Select country..."]');
        this.searchCityInput = searchSection.locator('input[placeholder="Type at least 2 characters..."]');
        this.searchButton = searchSection.locator('button', { hasText: 'Search' });
        this.clearSearchButton = searchSection.locator('button', { hasText: 'Clear' });

        // City List Table
        this.cityTable = page.locator('table.cities-table');
    }

    /**
     * Navigate to City Management page
     */
    async navigateToCityManagement() {
        await this.goto('/');
        await this.manageCitiesTab.click();
        await this.page.waitForTimeout(500);
    }

    /**
     * Add a new city
     */
    async addCity(cityName: string, country: string, description: string = '') {
        // Step 1: Select Country (Autocomplete) - Must be first
        await this.countryInput.click();
        // Use pressSequentially to simulate real typing which triggers autocomplete better than fill
        await this.countryInput.pressSequentially(country, { delay: 100 });
        await this.page.waitForTimeout(1000); // Wait for results

        const autocompleteOption = this.page.locator('mat-option').filter({ hasText: country }).first();
        await autocompleteOption.waitFor({ state: 'visible', timeout: 5000 });
        await autocompleteOption.click();
        await this.page.waitForTimeout(500); // Selection register time

        // Step 2: Fill City Name
        await this.cityNameInput.fill(cityName);

        // Step 3: Fill Description
        if (description) {
            await this.descriptionInput.fill(description);
        }

        // Step 4: Click Add
        // Verify button is enabled (meaning validation passed)
        await expect(this.addCityButton).toBeEnabled({ timeout: 5000 });
        await this.addCityButton.click();

        // Wait for success notification and verify content
        const notification = this.page.locator('simple-snack-bar, .mat-mdc-simple-snack-bar, .mat-simple-snackbar');
        await expect(notification).toBeVisible({ timeout: 8000 });
        await expect(notification).toContainText(`City "${cityName}" added successfully!`);

        // Wait for complete
        await this.page.waitForTimeout(2000);
    }

    /**
     * Search for cities
     */
    async searchCities(country: string, cityName: string = '') {
        // Step 1: Select Country
        await this.searchCountryInput.click();
        await this.searchCountryInput.pressSequentially(country, { delay: 100 });
        await this.page.waitForTimeout(1000);

        const autocompleteOption = this.page.locator('mat-option').filter({ hasText: country }).first();
        await autocompleteOption.waitFor({ state: 'visible', timeout: 5000 });
        await autocompleteOption.click();
        await this.page.waitForTimeout(500);

        // Step 2: Fill City Name
        if (cityName) {
            await this.searchCityInput.fill(cityName);
        }

        // Step 3: Search
        await expect(this.searchButton).toBeEnabled();
        await this.searchButton.click();

        // Wait for results
        await this.page.waitForSelector('.loading-container', { state: 'hidden', timeout: 10000 }).catch(() => { });
        await this.page.waitForTimeout(2000);
    }

    /**
     * Get all city names from the table
     */
    async getCityNames(): Promise<string[]> {
        // Wait for table to be visible
        const tableVisible = await this.cityTable.isVisible().catch(() => false);
        if (!tableVisible) {
            return [];
        }

        const rows = await this.page.locator('table.cities-table tbody tr').all();
        const cityNames: string[] = [];

        for (const row of rows) {
            const nameCell = row.locator('td').nth(1); // Name is in the second column
            const cityName = await nameCell.textContent();
            if (cityName) {
                cityNames.push(cityName.trim());
            }
        }

        return cityNames;
    }

    /**
     * Check if a city exists in the table
     */
    async isCityInTable(cityName: string): Promise<boolean> {
        const cityNames = await this.getCityNames();
        return cityNames.some(name => name.includes(cityName));
    }

    /**
     * Get the number of cities in the table
     */
    async getCityCount(): Promise<number> {
        const tableVisible = await this.cityTable.isVisible().catch(() => false);
        if (!tableVisible) {
            return 0;
        }
        return await this.page.locator('table.cities-table tbody tr').count();
    }

    /**
     * Delete a city by name
     */
    async deleteCity(cityName: string) {
        // Find the row with the city name and click delete button
        const row = this.page.locator(`tr:has-text("${cityName}")`).first();
        const deleteButton = row.locator('button[mattooltip="Delete city"]');

        // Setup dialog handler to accept the confirmation
        this.page.once('dialog', async dialog => {
            await dialog.accept();
        });

        await deleteButton.click();

        // Wait for success notification and verify content
        const notification = this.page.locator('simple-snack-bar, .mat-mdc-simple-snack-bar, .mat-simple-snackbar');
        await expect(notification).toBeVisible({ timeout: 5000 });
        await expect(notification).toContainText(`City "${cityName}" deleted successfully!`);

        // Wait for deletion to complete
        await this.page.waitForTimeout(2000);
    }

    /**
     * Clear the add city form
     */
    async clearAddCityForm() {
        await this.clearButton.click();
        await this.page.waitForTimeout(500);
    }

    /**
     * Clear the search form
     */
    async clearSearchForm() {
        await this.clearSearchButton.click();
        await this.page.waitForTimeout(500);
    }
}
