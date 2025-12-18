import { SearchFlightsPage } from "@pages/searchFlightsPage";
import test, { expect } from "@playwright/test";
import { CityManagementPage } from "@pages/cityManagementPage";
import { TaxInformationPage } from "@pages/taxInformationPage";

test.describe('Smoke Test', () => {
    let searchFlightsPage: SearchFlightsPage;
    let cityPage: CityManagementPage;
    let taxInformation: TaxInformationPage;

    test.beforeEach(async ({ page }) => {
        await page.goto('/');
        await page.waitForTimeout(500);
        searchFlightsPage = new SearchFlightsPage(page);
    });

    test('Smoke Test - Verify Search Flights Tab, Manage Cities Tab and Tax Information Tab are visible and click on them', async ({ page }) => {
            cityPage = new CityManagementPage(page);
            taxInformation = new TaxInformationPage(page);

            //Verify Search Flights Tab, Manage Cities Tab and Tax Information Tab are visible
            await expect(searchFlightsPage.searchFlightsTab).toBeVisible();
            await expect(cityPage.manageCitiesTab).toBeVisible();


            //Click on Search Flights Tab
            await searchFlightsPage.searchFlightsTab.click();

            //Click on Manage Cities Tab
            await cityPage.manageCitiesTab.click();

            //Click on Tax Information Tab
            await taxInformation.taxInformationTab.click();
        });

});