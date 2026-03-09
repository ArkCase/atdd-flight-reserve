import { test, expect } from '@playwright/test';
import { CityManagementPage } from '@pages/cityManagementPage';
import { SearchFlightsPage } from '@pages/searchFlightsPage';

test.describe('UI Negative Tests - Invalid Input Validation', () => {

    test.describe('City Management - Invalid Inputs', () => {
        let cityPage: CityManagementPage;

        test.beforeEach(async ({ page }) => {
            cityPage = new CityManagementPage(page);
            await cityPage.navigateToCityManagement();
        });

        test('Add City - Should disable button when city name is empty', async ({ page }) => {
            await cityPage.countryInput.click();
            await cityPage.countryInput.pressSequentially('Germany', { delay: 100 });
            await page.waitForTimeout(1000);
            
            const countryOption = cityPage.countryOption.filter({ hasText: 'Germany' }).first();
            await countryOption.click();
            await page.waitForTimeout(500);

            await cityPage.cityNameInput.clear();
            await expect(cityPage.addCityButton).toBeDisabled();
        });

        test('Add City - Should disable button when country is not selected', async ({ page }) => {
            await cityPage.cityNameInput.fill('Test City');
            await expect(cityPage.addCityButton).toBeDisabled();
        });

        test('Search Cities - Should disable search when country is not selected', async ({ page }) => {
            await cityPage.searchCityInput.fill('Berlin');
            await expect(cityPage.searchButton).toBeDisabled();
        });

    });

    test.describe('Flight Search - Invalid Inputs', () => {
        let searchFlightsPage: SearchFlightsPage;

        test.beforeEach(async ({ page }) => {
            await page.goto('/');
            await page.waitForTimeout(500);
            searchFlightsPage = new SearchFlightsPage(page);
            await searchFlightsPage.searchFlightsTab.click();
        });

        test('Should keep search button disabled when only source is selected', async ({ page }) => {
            const sourceCountry = 'Macedonia';
            const sourceCity = 'Skopje';
            const sourceAirport = ' Skopje Alexander the Great Airport (SKP) ';
            await searchFlightsPage.searchSource(sourceCountry, sourceCity, sourceAirport);
            await expect(searchFlightsPage.searchButton).toBeDisabled();
        });

        test('Should keep search button disabled when only destination is selected', async ({ page }) => {
            const destinationCountry = 'Turkey';
            const destinationCity = 'Istanbul';
            const destinationAirport = ' Atatürk International Airport (IST) ';
            await searchFlightsPage.searchDestination(destinationCountry, destinationCity, destinationAirport);
            await expect(searchFlightsPage.searchButton).toBeDisabled();
        });

        test('Should show error when searching identical source and destination', async ({ page }) => {
            await searchFlightsPage.searchSource('Macedonia', 'Skopje', ' Skopje Alexander the Great Airport (SKP) ');
            await searchFlightsPage.searchDestination('Macedonia', 'Skopje', ' Skopje Alexander the Great Airport (SKP) ');

            await searchFlightsPage.searchButton.click();
            await page.waitForTimeout(2000);

            await expect(searchFlightsPage.errorCard).toBeVisible();
            await expect(searchFlightsPage.errorCard).toContainText('No routes found');
        });

        test('Should show error for non-existent route', async ({ page }) => {
            await searchFlightsPage.searchSource('Macedonia', 'Skopje', ' Skopje Alexander the Great Airport (SKP) ');
            await searchFlightsPage.searchDestination('China', 'Shanghai', 'Shanghai Hongqiao International Airport (SHA)');

            await searchFlightsPage.searchButton.click();
            await page.waitForTimeout(3000);

            await expect(searchFlightsPage.errorCard).toBeVisible();
            await expect(searchFlightsPage.errorCard).toContainText('No routes found between these airports');
        });
    });
});
