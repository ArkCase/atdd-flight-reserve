import { SearchFlightsPage } from "@pages/searchFlightsPage";
import test, { expect } from "@playwright/test";

test.describe('City Management UI - CRUD Operations', () => {
    let searchFlightsPage: SearchFlightsPage;

    test.beforeEach(async ({ page }) => {
        await page.goto('/');
        await page.waitForTimeout(500);
        searchFlightsPage = new SearchFlightsPage(page);
    });

    test('Search Fight Routes - Search For Existing Flight Route', async ({ page }) => {
        await searchFlightsPage.searchFlightsTab.click();
        
        const sourceCountry = 'Macedonia';
        const sourceCity = 'Skopje';
        const sourceAirport = ' Skopje Alexander the Great Airport (SKP) ';
        await searchFlightsPage.searchSource(sourceCountry, sourceCity, sourceAirport);

        const destinationCountry = 'Turkey';
        const destinationCity = 'Istanbul';
        const destinationAirport = ' Atatürk International Airport (IST) ';
        await searchFlightsPage.searchDestination(destinationCountry, destinationCity, destinationAirport);

        await searchFlightsPage.searchButton.click();

        //Verify Available Routes
        // Verify results card is visible
        await expect(searchFlightsPage.routesCard).toBeVisible();

        // Verify route count in title
        await expect(searchFlightsPage.routesCardTitle).toContainText('Available Routes (1)');

        // Verify card subtitle with airports
        await expect(searchFlightsPage.routesCardSubtitle).toContainText('Routes from SKP to IST');

        // Verify route details - From airport
        await expect(searchFlightsPage.routesCardFromDetail)
            .toContainText('SKP (Skopje Alexander the Great Airport)');

        // Verify route details - To airport
        await expect(searchFlightsPage.routesCardToDetail)
            .toContainText('IST (Atatürk International Airport)');

        // Verify stops information
        await expect(searchFlightsPage.routesCardStopsDetail)
            .toContainText('Direct Flight');

        // Verify price is visible and contains dollar sign
        await expect(searchFlightsPage.routesCardPrice).toBeVisible();
        await expect(searchFlightsPage.routesCardPrice).toContainText('$');

        // Verify exactly one route card exists
        await expect(searchFlightsPage.routeCards).toHaveCount(1);
    });

    test('Search Fight Routes - Search For Non-existing Flight Route', async ({ page }) => {
await searchFlightsPage.searchFlightsTab.click();
        
        const sourceCountry = 'Macedonia';
        const sourceCity = 'Skopje';
        const sourceAirport = ' Skopje Alexander the Great Airport (SKP) ';
        await searchFlightsPage.searchSource(sourceCountry, sourceCity, sourceAirport);

        const destinationCountry = 'China';
        const destinationCity = 'Shanghai';
        const destinationAirport = 'Shanghai Hongqiao International Airport (SHA)';
        await searchFlightsPage.searchDestination(destinationCountry, destinationCity, destinationAirport);

        await searchFlightsPage.searchButton.click();

        // Verify error message
    await expect(searchFlightsPage.errorCard).toBeVisible();
    await expect(searchFlightsPage.errorCard).toContainText('No routes found between these airports');
    });
})