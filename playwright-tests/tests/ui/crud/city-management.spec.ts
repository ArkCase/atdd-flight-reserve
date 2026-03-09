import { test, expect } from '@playwright/test';
import { CityManagementPage } from '../../../pages/cityManagementPage';

/**
 * UI CRUD Test - City Management
 */

test.describe('City Management UI - CRUD Operations', () => {
    let cityPage: CityManagementPage;

    test.beforeEach(async ({ page }) => {
        cityPage = new CityManagementPage(page);
        await cityPage.navigateToCityManagement();
    });

    test('should create, search, and delete a city', async ({ page }) => {
        const uniqueCityName = `UITest_${Date.now()}`;
        const country = 'Germany';
        const description = 'Test city';

        // CREATE - Add a new city
        await cityPage.addCity(uniqueCityName, country, description);

        // SEARCH - Find the created city
        await cityPage.searchCities(country, uniqueCityName);

        // VERIFY - City exists in table
        const cityExists = await cityPage.isCityInTable(uniqueCityName);
        expect(cityExists).toBe(true);

        // DELETE - Remove the city
        await cityPage.deleteCity(uniqueCityName);
        const cityExistsAfterDelete = await cityPage.isCityInTable(uniqueCityName);
        expect(cityExistsAfterDelete).toBe(false);

        // VERIFY DELETION - City no longer exists
        await cityPage.clearSearchForm();
        await cityPage.searchCities(country, uniqueCityName);

        const cityExistsAfterSearch = await cityPage.isCityInTable(uniqueCityName);
        expect(cityExistsAfterSearch).toBe(false);
    });

     test('Add City - Should handle numeric-only city name', async ({ page }) => {
            const numericCityName = `123456_${Date.now()}`;
            await cityPage.countryInput.click();
            await cityPage.countryInput.pressSequentially('Germany', { delay: 100 });
            await page.waitForTimeout(1000);
            const countryOption = cityPage.countryOption.filter({ hasText: 'Germany' }).first();
            await countryOption.click();
            await page.waitForTimeout(500);
            await cityPage.cityNameInput.fill(numericCityName);
            await cityPage.addCityButton.click();
            await expect(cityPage.notification).toBeVisible({ timeout: 8000 });
        });
});
