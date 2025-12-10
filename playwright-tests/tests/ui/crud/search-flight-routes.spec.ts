import { SearchFlightsPage } from "@pages/searchFlightsPage";
import test, { expect } from "@playwright/test";

test.describe('City Management UI - CRUD Operations', () => {
    let cityPage: SearchFlightsPage;

    test.beforeEach(async ({ page }) => {
        await page.goto('http://localhost:8000/');
        cityPage = new SearchFlightsPage(page);
    });

    test('Search Fight Routes - Search For Existing Flight Route', async ({ page }) => {
        await cityPage.searchFlightsTab.click();
        
        const sourceCountry = 'Macedonia';
        const sourceCity = 'Skopje';
        const sourceAirport = ' Skopje Alexander the Great Airport (SKP) ';
        await cityPage.searchSource(sourceCountry, sourceCity, sourceAirport);

        const destinationCountry = 'Turkey';
        const destinationCity = 'Istanbul';
        const destinationAirport = ' Atatürk International Airport (IST) ';
        await cityPage.searchDestination(destinationCountry, destinationCity, destinationAirport);

        await cityPage.searchButton.click();

        //Verify Available Routes
        // Verify results card is visible
        await expect(cityPage.routesCard).toBeVisible();

        // Verify route count in title
        await expect(cityPage.routesCardTitle).toContainText('Available Routes (1)');

        // Verify card subtitle with airports
        await expect(cityPage.routesCardSubtitle).toContainText('Routes from SKP to IST');

        // Verify route details - From airport
        await expect(cityPage.routesCardFromDetail)
            .toContainText('SKP (Skopje Alexander the Great Airport)');

        // Verify route details - To airport
        await expect(cityPage.routesCardToDetail)
            .toContainText('IST (Atatürk International Airport)');

        // Verify stops information
        await expect(cityPage.routesCardStopsDetail)
            .toContainText('Direct Flight');

        // Verify price is visible and contains dollar sign
        await expect(cityPage.routesCardPrice).toBeVisible();
        await expect(cityPage.routesCardPrice).toContainText('$');

        // Verify exactly one route card exists
        await expect(cityPage.routeCards).toHaveCount(1);
    });

    test('Search Fight Routes - Search For Non-existing Flight Route', async ({ page }) => {
await cityPage.searchFlightsTab.click();
        
        const sourceCountry = 'Macedonia';
        const sourceCity = 'Skopje';
        const sourceAirport = ' Skopje Alexander the Great Airport (SKP) ';
        await cityPage.searchSource(sourceCountry, sourceCity, sourceAirport);

        const destinationCountry = 'China';
        const destinationCity = 'Shanghai';
        const destinationAirport = 'Shanghai Hongqiao International Airport (SHA)';
        await cityPage.searchDestination(destinationCountry, destinationCity, destinationAirport);

        await cityPage.searchButton.click();

        // Verify error message
    await expect(cityPage.errorCard).toBeVisible();
    await expect(cityPage.errorCard).toContainText('No routes found between these airports');
    });
})